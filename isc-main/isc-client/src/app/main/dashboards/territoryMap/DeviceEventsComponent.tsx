import React, { useEffect, useState } from 'react';
import FusePageCarded from '@fuse/core/FusePageCarded';
import HOCWrapper from '@fuse/utils/HOCWrapper';
import { createEmptyContext, Filter, IscTableContext, IscTableDataAdaptor } from 'app/components/table/IscTableContext';
import School from 'app/domain/school';
import { OnContextChanged } from 'app/components/table/IscTableDataProvider';
import { IEntity } from 'app/domain/entity';
import { ICellCallback, ITableHeadColumn } from 'app/components/table/IscTableHead';
import _ from 'lodash';
import { createStyles, fade, IconButton, makeStyles, TableCell, Theme, Tooltip } from '@material-ui/core';
import { FaEye, FaOrcid } from 'react-icons/fa';
import { BiHistory, BiLink } from 'react-icons/bi';
import { BsQuestion } from 'react-icons/bs';
import DeviceEvent, { TYPE_ALERT, TYPE_ALERT_ACK, TYPE_ALERT_IGNORE } from 'app/domain/deviceEvent';
import MomentUtils from '@date-io/moment';
import { RootState, SchoolEvents } from 'app/store/appState';
import * as Selectors from 'app/store/selectors';
import * as Actions from 'app/store/actions';
import { useDispatch, useSelector } from 'react-redux';
import { ServerDataProvider } from 'app/components/table/providers/ServerDataProvider';
import { MapControl } from 'app/components/map/deviceActions/IDeviceAction';
import { EntityType, hasGeoData } from 'app/utils/domain.constants';
import { findNode, findParentSchool } from 'app/components/map/utils/MapUtils';
import { SearchResult } from 'app/domain/search';
import { CompositeNode } from 'app/domain/composite';
import { copyToClipboard } from '@common/utils/dom.utils';
import clsx from 'clsx';
import DeviceEventsElements from './DeviceEventsElements';
import DeviceEventsControls from './DeviceEventsControls';
import DeviceEventsTraceDialog from './DeviceEventsTraceDialog';

const momentUtils: MomentUtils = new MomentUtils();

interface DeviceEventsComponentProps {
    school?: School;
    mapControl: MapControl;
}

const useStyles = makeStyles((theme: Theme) => {
    return createStyles({
        missingGeoIcon: {
            color: fade(theme.palette.text.secondary, 0.25),
        },
    });
});

function MissingGeoDataIcon(): JSX.Element {
    const classes = useStyles();
    const ClickAction = (): void => {
        console.log('Click: selected floot');
    };

    return (
        <Tooltip title="Missing on Map" aria-label="show-filters">
            <IconButton className={classes.missingGeoIcon} color="inherit" size="small" onClick={ClickAction}>
                <BsQuestion />
            </IconButton>
        </Tooltip>
    );
}

interface EventActionProps {
    event: DeviceEvent;
    cellCallback?: ICellCallback;
}

function EventAction(props: EventActionProps): JSX.Element {
    const deviceId = props.event.payload?.deviceId;
    const entityType = props.event.payload?.type as EntityType;

    const device = useSelector<RootState, IEntity | undefined>(
        Selectors.createEntitySelector({ id: deviceId, entityType } as CompositeNode)
    );

    return device ? (
        <TableCell
            padding="default"
            className={clsx(
                props.event.deviceType === TYPE_ALERT && 'error-cell',
                props.event.deviceType === TYPE_ALERT_ACK && 'success-cell',
                props.event.deviceType === TYPE_ALERT_IGNORE && 'ignore-cell'
            )}
        >
            {(device && hasGeoData(device) && (
                <Tooltip title="Find On Map">
                    <IconButton
                        aria-label="highlight"
                        size="small"
                        onClick={(event: React.MouseEvent) => {
                            event.stopPropagation();
                            if (props.cellCallback?.onAction) {
                                props.cellCallback?.onAction('highlight', props.event);
                            }
                            return false;
                        }}
                    >
                        <FaEye />
                    </IconButton>
                </Tooltip>
            )) ||
                (!hasGeoData(device) && <MissingGeoDataIcon />)}

            <Tooltip title="Trace">
                <IconButton
                    aria-label="trace"
                    size="small"
                    onClick={(event: React.MouseEvent) => {
                        event.stopPropagation();
                        if (props.cellCallback?.onAction) {
                            props.cellCallback?.onAction('trace', props.event);
                        }
                        return false;
                    }}
                >
                    <BiHistory />
                </IconButton>
            </Tooltip>

            {device && (
                <Tooltip title="Other events for Device" aria-label="show-filters">
                    <IconButton
                        size="small"
                        style={{ fontSize: '1.4rem' }}
                        onClick={(event: React.MouseEvent) => {
                            event.stopPropagation();

                            if (props.cellCallback?.onAction) {
                                props.cellCallback?.onAction('filterById', props.event);
                            }
                            return false;
                        }}
                    >
                        <FaOrcid />
                    </IconButton>
                </Tooltip>
            )}
        </TableCell>
    ) : (
        <TableCell padding="default">
            <Tooltip title="Trace">
                <IconButton
                    aria-label="trace"
                    size="small"
                    onClick={(event: React.MouseEvent) => {
                        event.stopPropagation();
                        if (props.cellCallback?.onAction) {
                            props.cellCallback?.onAction('trace', props.event);
                        }
                        return false;
                    }}
                >
                    <BiHistory />
                </IconButton>
            </Tooltip>
        </TableCell>
    );
}

interface DeviceLinkProps {
    event: DeviceEvent;
}
function DeviceLink(props: DeviceLinkProps): JSX.Element | null {
    const deviceId = props.event.payload?.deviceId;
    const entityType = props.event.payload?.type as EntityType;

    const device = useSelector<RootState, IEntity | undefined>(
        Selectors.createEntitySelector({ id: deviceId, entityType } as CompositeNode)
    );

    return device ? (
        <Tooltip title="Copy Device Id to clipboard">
            <IconButton
                aria-label="highlight"
                size="small"
                onClick={(event: React.MouseEvent) => {
                    event.stopPropagation();
                    if (props.event.deviceId) {
                        copyToClipboard(props.event.deviceId);
                    }
                    return false;
                }}
            >
                <BiLink />
            </IconButton>
        </Tooltip>
    ) : null;
}

const columns: Array<ITableHeadColumn> = [
    {
        id: 'deviceId',
        align: 'center',
        disablePadding: false,
        width: 40,
        label: 'ID',
        filter: 'string',
        formatter: (row: DeviceEvent, column: ITableHeadColumn, index: number): React.ReactNode => (
            <td
                className={clsx(
                    'MuiTableCell-root MuiTableCell-sizeSmall',
                    row.deviceType === TYPE_ALERT && 'error-cell',
                    row.deviceType === TYPE_ALERT_ACK && 'success-cell',
                    row.deviceType === TYPE_ALERT_IGNORE && 'ignore-cell'
                )}
                key={index}
            >
                <DeviceLink event={row} />
            </td>
        ),
    },
    {
        id: 'eventTime',
        align: 'center',
        disablePadding: false,
        width: 180,
        label: 'Time',
        type: 'dateTimeRange',
        filter: {
            filterType: 'date',
            format: 'YYYY-MM-DD LT',
        },
        formatter: (row: DeviceEvent, column: ITableHeadColumn, index: number): React.ReactNode => (
            <td
                className={clsx(
                    'MuiTableCell-root MuiTableCell-sizeSmall',
                    row.deviceType === TYPE_ALERT && 'error-cell',
                    row.deviceType === TYPE_ALERT_ACK && 'success-cell',
                    row.deviceType === TYPE_ALERT_IGNORE && 'ignore-cell'
                )}
                key={index}
            >
                {momentUtils.format(momentUtils.date(row.eventTime), 'YYYY-MM-DD LT')}
            </td>
        ),
    },
    {
        id: 'payload.code',
        align: 'left',
        disablePadding: false,
        label: 'Code',
        filter: 'string',
        width: 230,
        formatter: (row: DeviceEvent, column: ITableHeadColumn, index: number): React.ReactNode => (
            <TableCell
                padding="default"
                key={index}
                style={{ wordBreak: 'break-word' }}
                className={clsx(
                    row.deviceType === TYPE_ALERT && 'error-cell',
                    row.deviceType === TYPE_ALERT_ACK && 'success-cell',
                    row.deviceType === TYPE_ALERT_IGNORE && 'ignore-cell'
                )}
            >
                {row.payload?.code}
            </TableCell>
        ),
    },
    {
        id: 'payload.type',
        align: 'left',
        width: 100,
        disablePadding: false,
        label: 'Type',
        filter: {
            filterType: 'string',
            filter: (value: IEntity, filterValue: string): boolean => `${value}`.indexOf(filterValue) >= 0,
        },
        formatter: (row: DeviceEvent, column: ITableHeadColumn, index: number): React.ReactNode => (
            <TableCell
                title={row.payload?.user ? `${row.payload?.user}: ${row.payload?.notes || 'No notes'}` : ''}
                padding="default"
                key={index}
                className={clsx(
                    row.deviceType === TYPE_ALERT && 'error-cell',
                    row.deviceType === TYPE_ALERT_ACK && 'success-cell',
                    row.deviceType === TYPE_ALERT_IGNORE && 'ignore-cell'
                )}
            >
                {row.deviceType || row.payload?.type}
            </TableCell>
        ),
    },
    {
        id: 'payload.description',
        align: 'left',
        disablePadding: false,
        label: 'Description',
        filter: 'string',
        formatter: (row: DeviceEvent, column: ITableHeadColumn, index: number): React.ReactNode => (
            <TableCell
                padding="default"
                key={index}
                style={{ wordBreak: 'break-word' }}
                className={clsx(
                    row.deviceType === TYPE_ALERT && 'error-cell',
                    row.deviceType === TYPE_ALERT_ACK && 'success-cell',
                    row.deviceType === TYPE_ALERT_IGNORE && 'ignore-cell'
                )}
            >
                {(row.deviceType === TYPE_ALERT_IGNORE || row.deviceType === TYPE_ALERT_ACK) && (
                    <Tooltip title={`${row.payload?.user}: ${row.payload?.notes || 'No notes'}`}>
                        <span>{`${row.payload?.notes || ''}`}</span>
                    </Tooltip>
                )}
                {row.deviceType !== TYPE_ALERT_IGNORE && row.deviceType !== TYPE_ALERT_ACK && (
                    <span>{row.payload?.description}</span>
                )}
            </TableCell>
        ),
    },
    {
        id: 'actions',
        align: 'left',
        disablePadding: false,
        label: 'Actions',
        width: 100,
        formatter: (
            row: DeviceEvent,
            column: ITableHeadColumn,
            index: number,
            cellCallback?: ICellCallback
        ): React.ReactNode => {
            return <EventAction key={index} event={row} cellCallback={cellCallback} />;
        },
    },
];

function isEmpty(filter: Filter): boolean {
    if (filter) {
        const filters = Object.keys(filter).filter(key => filter[key] && filter[key].trim().length > 0);
        return !filters?.length;
    }

    return true;
}

class EventsServerDataProvider extends ServerDataProvider {
    constructor(
        public newEventsCount: number,
        serverData: SearchResult,
        headers: ITableHeadColumn[],
        initialContext: IscTableContext,
        contextChangeListener: OnContextChanged
    ) {
        super(serverData, headers, initialContext, contextChangeListener);
    }
}

function filterSchoolEvents(schoolId: string | undefined, events: DeviceEvent[]): DeviceEvent[] {
    const schoolEvents: DeviceEvent[] = [];

    events?.forEach(event => {
        if (event.schoolId) {
            if (event.schoolId === schoolId) {
                schoolEvents.push(event);
            }
        } else if (event.payload?.code === 'SYNC' || event.payload?.code === 'SERVER') {
            schoolEvents.push(event);
        } else if (event.payload?.deviceId) {
            const entityType = event.payload?.type as EntityType;
            const node = findNode(entityType, event.payload.deviceId);
            const schoolInfo = findParentSchool(node?.parentIds);
            if (schoolInfo.id) {
                event.schoolId = schoolInfo.id;
                if (event.schoolId === schoolId) {
                    schoolEvents.push(event);
                }
            }
        }
    });

    return schoolEvents;
}

function createProvider(
    schoolId: string | undefined,
    events: SchoolEvents,
    dataContext: IscTableContext,
    contextChangeListener: OnContextChanged
): EventsServerDataProvider {
    const currentPage = events.currentPage
        ? { ...events.currentPage }
        : {
              data: [],
              numberOfItems: 0,
              numberOfPages: 0,
          };

    let newEventsCount = 0;
    const newSchoolEvents = filterSchoolEvents(schoolId, events.newEvents);
    if (newSchoolEvents.length) {
        if (dataContext.pagination.currentPage === 0 && isEmpty(dataContext.filter)) {
            currentPage.data = [...newSchoolEvents, ...currentPage.data];
            while (currentPage.data.length > dataContext.pagination.rowsPerPage) currentPage.data.pop();
        } else {
            newEventsCount = newSchoolEvents.length;
        }

        if (isEmpty(dataContext.filter)) {
            currentPage.numberOfItems += newSchoolEvents.length;
            currentPage.numberOfPages = Math.floor(currentPage.numberOfItems / dataContext.pagination.rowsPerPage) + 1;
        }
    }

    return new EventsServerDataProvider(newEventsCount, currentPage, columns, dataContext, contextChangeListener);
}

export default function DeviceEventsComponent(props: DeviceEventsComponentProps): JSX.Element {
    const [dataContext, setDataContext] = useState<IscTableContext>(createEmptyContext());
    const [showFilters, setShowFilters] = useState<boolean>(false);
    const [school, setSchool] = useState<School | undefined>(undefined);
    const [eventTrace, setEventTrace] = useState<DeviceEvent | undefined>(undefined);
    const events = useSelector<RootState, SchoolEvents>(Selectors.eventsSelector);
    const dispatch = useDispatch();

    const search = (context: IscTableContext, currentSchool?: School): void => {
        if (currentSchool?.id) {
            const columnFilter = [{ key: 'schoolId', value: currentSchool.id }];
            if (context.filter) {
                Object.keys(context.filter).forEach(filter => {
                    const filterValue = context.filter[filter];
                    if (filterValue?.length) {
                        if (filter === 'eventTime') {
                            // TODO: parse and provide in format with client timezone
                            columnFilter.push({ key: filter, value: filterValue });
                        } else {
                            columnFilter.push({ key: filter, value: filterValue });
                        }
                    }
                });
            }

            dispatch(
                Actions.queryEvents({
                    filter: { columns: columnFilter },
                    pagination: {
                        page: context.pagination?.currentPage || 0,
                        size: context.pagination?.rowsPerPage || 25,
                    },
                    sort: [],
                })
            );
        }
    };

    const dataProvider: EventsServerDataProvider = createProvider(
        school?.id,
        events,
        dataContext,
        (newContext: IscTableContext) => {
            if (!_.isEqual(dataContext, newContext)) {
                setDataContext(newContext);
                search(newContext, school);
            }
        }
    );

    const customizeToolbar = (): React.ReactNode => {
        return <></>;
    };

    const onSelectElement = (event: DeviceEvent | null): void => {
        console.log('Event selected', event, school);
    };

    const filterEventsByDeviceId = (deviceId?: string): void => {
        if (deviceId) {
            const newContext: IscTableContext = {
                ...dataContext,
                pagination: { ...dataContext.pagination, currentPage: 0 },
                filter: { deviceId },
            };
            setDataContext(newContext);
            search(newContext, school);
        }
    };

    const cellCallback = {
        onAction: (action: string, row: DeviceEvent) => {
            if (action === 'highlight') {
                if (row.payload?.deviceId) {
                    const entityType = row.payload.type as EntityType;
                    const node = findNode(entityType, row.payload.deviceId);
                    if (node) {
                        props.mapControl?.highlight(node);
                    }
                }
            } else if (action === 'trace') {
                setEventTrace(row);
            } else if (action === 'filterById') {
                filterEventsByDeviceId(row.deviceId);
            } else {
                console.log('Table row action not implemented!', action, row);
            }
        },
        context: {},
    };

    const onRefreshGrid = (reset: boolean): void => {
        let newContext = dataContext;
        if (reset) {
            newContext = createEmptyContext();
            setSchool(props.school);
            setDataContext(newContext);
            setShowFilters(false);
        }

        search(newContext, props.school);
    };

    useEffect(() => {
        if ((!events.currentPage && props.school?.id) || props.school?.id !== school?.id) {
            onRefreshGrid(true);
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [props.school?.id]);

    const onEventTraceDialog = (): void => {
        setEventTrace(undefined);
    };

    return (
        <IscTableDataAdaptor context={dataContext} dataProvider={dataProvider}>
            <HOCWrapper
                componentRef={FusePageCarded}
                classes={{
                    content: 'flex',
                    header: 'pl-12 pr-12',
                }}
                header={
                    <DeviceEventsControls
                        showFilters={showFilters}
                        onToggleFilters={(show: boolean) => setShowFilters(show)}
                        toolBarItems={customizeToolbar()}
                        newEvents={dataProvider.newEventsCount}
                        onRefreshGrid={onRefreshGrid}
                    />
                }
                content={
                    <DeviceEventsElements
                        columns={columns}
                        onSelectElement={(row: DeviceEvent | null) => onSelectElement(row)}
                        cellCallback={cellCallback}
                        showFilters={showFilters}
                    />
                }
            />

            {eventTrace && <DeviceEventsTraceDialog event={eventTrace} onClose={onEventTraceDialog} />}
        </IscTableDataAdaptor>
    );
}
