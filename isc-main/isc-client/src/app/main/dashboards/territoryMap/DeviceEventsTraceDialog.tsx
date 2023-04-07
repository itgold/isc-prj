import React, { useState, useEffect } from 'react';
import { Button, createStyles, Dialog, DialogActions, DialogContent, DialogTitle, makeStyles } from '@material-ui/core';
import DeviceEvent, { ApplicationEvent, EventPayload } from 'app/domain/deviceEvent';
// D3 React tree component: https://bkrem.github.io/react-d3-tree/
import Tree from 'react-d3-tree';
import clsx from 'clsx';
import eventsService from 'app/services/events.service';
import { RawNodeDatum } from 'react-d3-tree/lib/types/common';
import { ObjectMap } from 'app/utils/domain.constants';
import { DeviceState } from 'app/store/appState';

interface DeviceEventsTraceDialogProps {
    event?: DeviceEvent;
    onClose?: (event: unknown, reason: 'backdropClick' | 'escapeKeyDown') => void;
}

const VIEW_HEIGHT = 400;

const useStyles = makeStyles(() =>
    createStyles({
        root: {
            width: '100%',
            minWidth: '400px',
            minHeight: VIEW_HEIGHT,
            marginTop: '0px',
            paddingTop: '0px',
            overflowY: 'visible',
            backgroundColor: 'transparent',
            position: 'relative',
        },
        dialog: {
            '& .MuiDialog-paper': {
                overflowY: 'visible',
            },
        },
        dialogTitle: {
            marginBottom: '0px',
            paddingBottom: '0px',
        },
        treeContainer: {
            position: 'absolute',
            left: 10,
            right: 10,
            top: 10,
            bottom: 10,
        },
    })
);

const fromCamelCase = (str: string, separator = '_'): string =>
    str.replace(/([a-z\d])([A-Z])/g, `$1${separator}$2`).replace(/([A-Z]+)([A-Z][a-z\d]+)/g, `$1${separator}$2`);

interface RawNodeDatumExt extends RawNodeDatum {
    id: string;
}

function resolveEventType(event: ApplicationEvent<EventPayload>): string {
    const type = event.type || event.eventId;
    let friendlyName = type;
    if (type.startsWith('event.raw.native')) {
        friendlyName = 'Bulk update (Raw)';
    } else if (type.match(/event\.raw\.device\..*\.sync/gi)) {
        friendlyName = 'Sync device/user';
    } else if (type.startsWith('event.raw.device')) {
        friendlyName = 'Device Update (Raw)';
    } else if (type.indexOf('unknown') >= 0) {
        friendlyName = 'Unknown event';
    } else if (type.indexOf('syncStart') >= 0) {
        friendlyName = 'Start bulk Sync';
    } else if (type.startsWith('alert.')) {
        friendlyName = 'Alert';
        if (type.indexOf('.ack') > 0) {
            friendlyName = 'Acknowledged';
        } else if (type.indexOf('.ignore') > 0) {
            friendlyName = 'Ignored';
        }
    } else if (type.indexOf('incrementalUpdate') >= 0) {
        friendlyName = 'Device update';
    } else if (type.match(/event\.app\..*\.state/gi)) {
        friendlyName = 'Device State update';
    } else if (type.match(/event\.app\..*\.update/gi)) {
        friendlyName = 'Device update';
    } else if (type.indexOf('onDemandSync') >= 0) {
        friendlyName = 'Manual Sync';
    } else if (type.indexOf('serverEvent') >= 0) {
        friendlyName = 'Server error';
    } else if (type.indexOf('cameraStatus') >= 0) {
        friendlyName = 'Camera status update';
    }

    return friendlyName;
}

function truncateValue(value: string, maxLength: number): string {
    if (!value || value.length < maxLength + 2) {
        return value;
    }

    return `${value.substring(0, maxLength)} ...`;
}

// TODO: implement pluggable event detail degenration here
function extractEventProperties(event: ApplicationEvent<EventPayload>): Record<string, string | number | boolean> {
    const attributes: Record<string, string | number | boolean> = {};

    const { payload } = event;
    if (payload?.state) {
        if (payload.type) attributes.Type = payload.type;
        (payload?.state as DeviceState)?.forEach(s => {
            attributes[s.type] = truncateValue(s.value, 40);
        });
    } else {
        Object.getOwnPropertyNames(payload).forEach(propName => {
            const value = (payload as any)[propName];
            if (typeof value === 'string') {
                let name = fromCamelCase(propName, ' ');
                name = name.charAt(0).toUpperCase() + name.substr(1);
                attributes[name] = truncateValue(value, 40);
            }
        });
    }

    return attributes;
}

function generateTreeNode(event: ApplicationEvent<EventPayload>): RawNodeDatumExt {
    return {
        id: event.eventId,
        name: resolveEventType(event),
        attributes: extractEventProperties(event),
        children: [],
    };
}

function createEventTree(events: ApplicationEvent<EventPayload>[]): RawNodeDatum {
    let nodeTree: RawNodeDatumExt | null = null;

    if (events?.length) {
        const rootEvent: ApplicationEvent<EventPayload>[] = [];
        const eventsMap = events.reduce((result, event) => {
            return {
                ...result,
                [event.eventId]: event,
            };
        }, {} as ObjectMap<ApplicationEvent<EventPayload>>);

        events.forEach(event => {
            if (!event.referenceId || !eventsMap[event.referenceId]) {
                rootEvent.push(event);
            }
        });

        if (rootEvent.length) {
            const treeNodeMap: Record<string, RawNodeDatumExt> = {};

            if (rootEvent.length > 1) {
                nodeTree = { id: '', name: 'BATCH', children: [] };
                rootEvent.forEach(e => {
                    const node = generateTreeNode(e);
                    treeNodeMap[node.id] = node;
                    nodeTree?.children?.push(node);
                });
            } else {
                nodeTree = generateTreeNode(rootEvent[0]);
                treeNodeMap[nodeTree.id] = nodeTree;
            }

            const processingNodes: ApplicationEvent<EventPayload>[] = [];
            rootEvent.forEach(re => {
                events.filter(e => e.referenceId === re.eventId).forEach(e => processingNodes.push(e));
            });

            while (processingNodes.length) {
                const event = processingNodes.shift();
                if (event) {
                    const node = generateTreeNode(event);
                    treeNodeMap[node.id] = node;
                    const parentNode = event.referenceId ? treeNodeMap[event.referenceId] : null;
                    if (parentNode) {
                        parentNode.children?.push(node);
                    }
                    events.filter(e => e.referenceId === event.eventId).forEach(e => processingNodes.push(e));
                }
            }
        }
    }

    return nodeTree || { name: 'Not Found' };
    // return orgChart;
}

export default function DeviceEventsTraceDialog(props: DeviceEventsTraceDialogProps): JSX.Element {
    const [deviceEventHistory, setDeviceEventHistory] = useState<RawNodeDatum | undefined>(undefined);

    const onClose = (event: unknown, reason: 'backdropClick' | 'escapeKeyDown'): void => {
        if (props.onClose) {
            props.onClose(event, reason);
        }
    };

    const onCancel = (event: React.MouseEvent): void => {
        if (props.onClose) {
            props.onClose(event, 'escapeKeyDown');
        }
    };

    useEffect(() => {
        if (!deviceEventHistory && props.event?.correlationId) {
            eventsService.relatedEvents(props.event?.correlationId).then(events => {
                const tree = createEventTree(events);
                console.log('Resolve tree', events, tree);
                setDeviceEventHistory(tree);
            });
        }
    });

    const classes = useStyles();
    const showTree = !!deviceEventHistory;
    return (
        <Dialog
            fullWidth
            maxWidth="md"
            open
            onClose={onClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
            className={classes.dialog}
        >
            <DialogTitle className={classes.dialogTitle} id="alert-dialog-title">
                Event's trace
            </DialogTitle>
            <DialogContent className={classes.root}>
                <div id="treeWrapper" className={clsx(classes.treeContainer)}>
                    {showTree && (
                        <Tree
                            data={deviceEventHistory}
                            orientation="horizontal"
                            translate={{ x: 40, y: VIEW_HEIGHT / 2 }}
                            zoom={0.8}
                            scaleExtent={{ min: 0.1, max: 4 }}
                            nodeSize={{ x: 340, y: 140 }}
                        />
                    )}
                </div>
            </DialogContent>
            <DialogActions>
                <Button color="primary" onClick={onCancel}>
                    Close
                </Button>
            </DialogActions>
        </Dialog>
    );
}
