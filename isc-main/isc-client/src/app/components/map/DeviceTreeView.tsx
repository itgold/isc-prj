import React, { useEffect, useMemo, useRef, useState } from 'react';
import { makeStyles, Theme, createStyles } from '@material-ui/core/styles';
import {
    Button,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    fade,
    IconButton,
    InputBase,
    List,
    ListItem,
    ListItemText,
    Paper,
    SvgIconProps,
    Tooltip,
} from '@material-ui/core';
import SearchIcon from '@material-ui/icons/Search';
import TreePanel, { ContextMenuAction, resolveTopNodesIds, TreePanelNode } from '@common/components/layout/TreePanel';
import * as Selectors from 'app/store/selectors';
import { useSelector, useDispatch } from 'react-redux';
import { CompositeNode, CompositeType } from 'app/domain/composite';
import Region from 'app/domain/region';
import { EntityType, hasGeoData, RegionType, StringMap, EMPTY_LIST } from 'app/utils/domain.constants';
import {
    MdSpeakerGroup,
    MdVideocam,
    MdLockOpen,
    MdSpeaker,
    MdBuild,
    MdStorage,
    MdHealing,
    MdOutlineKeyboardVoice,
} from 'react-icons/md';
import { FaBuilding, FaClone, FaFan } from 'react-icons/fa';
import { SiBuffer, SiGoogleclassroom } from 'react-icons/si';
import { GiBrickWall, Gi3DStairs, GiElevator } from 'react-icons/gi';
import { BsLayersHalf, BsQuestion } from 'react-icons/bs';
import { IconType } from 'react-icons';
import { findComposite } from 'app/store/selectors';
import { useDebounce } from '@fuse/hooks';
import * as FuseActions from 'app/store/actions/fuse';
import * as AppActions from 'app/store/actions/app';

import clsx from 'clsx';
import { RootState } from 'app/store/appState';
import ISchoolElement from 'app/domain/school.element';
import EventBus from 'app/utils/simpleEventBus';
import SelectInTreeEvent from 'app/store/events/SelectInTreeEvent';
import _ from 'lodash';
import userPreferencesService, { UserContexts } from 'app/services/userPreference.service';
import { CompositeTreeDataProvider, ICompositeTreeDataProvider } from './providers/CompositeTreeDataProvider';
import IDeviceAction, { ActionMap, MapControl } from './deviceActions/IDeviceAction';
import { resolveEntityTool } from './markers/toolsList';
import { friendlyName } from './utils/MapUtils';

export type EntityIconMap = {
    [key: string]: React.ElementType<SvgIconProps> | IconType;
};
const NODE_TYPE_ICONS: EntityIconMap = {
    [EntityType.DOOR]: MdLockOpen,
    [EntityType.CAMERA]: MdVideocam,
    [EntityType.DRONE]: FaFan,
    [EntityType.SPEAKER]: MdSpeaker,
    [EntityType.UTILITY]: MdStorage,
    [EntityType.SAFETY]: MdHealing,
    [EntityType.RADIO]: MdOutlineKeyboardVoice,
    // ------------------------------------------
    [RegionType.ZONE]: MdSpeakerGroup,
    [RegionType.UNKNOWN]: MdBuild,
    [RegionType.BUILDING]: FaBuilding,
    [RegionType.FLOOR]: SiBuffer,
    [RegionType.ROOM]: SiGoogleclassroom, // MdWallpaper,
    [RegionType.WALL]: GiBrickWall,
    [RegionType.STAIRS]: Gi3DStairs,
    [RegionType.ELEVATOR]: GiElevator,
    [RegionType.POINT_REGION]: MdVideocam,
};

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        wrapper: {
            flex: '1 1 100%',
            display: 'flex',
            maxWidth: '100%',
            flexDirection: 'column',
        },
        treeContainer: {
            position: 'relative',
            flex: '1 1 100%',
            display: 'flex',
            flexDirection: 'column',
            overflowY: 'scroll',
            overflowX: 'hidden',
        },
        treePanel: {
            maxWidth: 400,
            paddingLeft: '10px',
            position: 'absolute',
            top: '0px',
            left: '0px',
            right: '0px',
            bottom: '0px',
        },

        searchRoot: {
            padding: '0px 4px',
            display: 'flex',
            alignItems: 'center',
            margin: 6,
            marginTop: 8,
            marginBottom: 10,
        },
        searchInput: {
            marginLeft: theme.spacing(1),
            flex: 1,
        },
        iconButton: {
            padding: 10,
        },

        selectedFloor: {
            color: theme.palette.primary.contrastText,
            backgroundColor: theme.palette.primary.main,
        },
        selectedFloorFocus: {
            borderColor: theme.palette.primary.main,
            borderWidth: '1px',
            borderStyle: 'dashed',
            color: theme.palette.text.secondary,
            backgroundColor: fade(theme.palette.primary.main, 0.25),
        },
        selectedZoneFocus: {
            borderColor: theme.palette.primary.main,
            borderWidth: '1px',
            borderStyle: 'dashed',
        },
        deviceNames: {
            fontStyle: 'bold',
            overflow: 'auto',
            maxHeight: 120,
        },
        nonDefinedEntity: {
            color: fade(theme.palette.text.secondary, 0.25),

            '&$selected': {
                color: 'red',
            },
        },
        selected: {},
    })
);

interface DeviceTreeViewProps {
    schoolId?: string;
    map: MapControl;
    extraActions?: ActionMap;
}

export function resolveNodeIcon(node: CompositeNode): React.ElementType<SvgIconProps> | IconType | undefined {
    let icon: React.ElementType<SvgIconProps> | undefined;

    if (node?.compositeType === CompositeType.CONTAINER) {
        const region = node as Region;
        if (region?.type) {
            icon = NODE_TYPE_ICONS[region.type];
        }
    } else {
        const entityType = CompositeNode.entityType(node);
        if (entityType) {
            icon = NODE_TYPE_ICONS[entityType];
        }
    }

    return icon;
}

function extractNodeId(nodeId: string): string {
    return nodeId.indexOf('_') >= 0 ? nodeId.split('_')[0] : nodeId;
}

interface TreeNodeStyle {
    selectedFloor?: string;
    selectedFloorFocus?: string;
    selectedZoneFocus?: string;
    nonDefined?: string;
}

function SelectedFloorIcon(): JSX.Element {
    const ClickAction = (): void => {
        console.log('Click: selected floor');
    };

    return (
        <Tooltip title="Floor is selected" aria-label="show-filters">
            <IconButton color="inherit" size="small" onClick={ClickAction}>
                <BsLayersHalf />
            </IconButton>
        </Tooltip>
    );
}

function GeoDataIcon(): JSX.Element {
    const ClickAction = (): void => {
        console.log('Click: selected floor');
    };

    return (
        <Tooltip title="Missing on Map" aria-label="show-filters">
            <IconButton color="inherit" size="small" onClick={ClickAction}>
                <BsQuestion />
            </IconButton>
        </Tooltip>
    );
}

function ZoneIcon(props: { id?: string; color?: string; selected?: string }): JSX.Element {
    const dispatch = useDispatch();
    const ClickAction = (): void => {
        dispatch(AppActions.toggleZone(props.id));
    };

    const style: React.CSSProperties = { fontSize: '1.3rem' };
    if (props.color) {
        style.color = props.color;
    }
    return (
        <Tooltip title={props.selected === 'true' ? 'Hide Zone' : 'Show Zone'} aria-label="show-filters">
            <IconButton color="inherit" size="small" onClick={ClickAction} style={style} className="colored-icon">
                <FaClone />
            </IconButton>
        </Tooltip>
    );
}

type TreeNodeIcon = { ItemIcon: IconType | (() => JSX.Element); params?: StringMap };
function calculateTreeNodeStyles(
    compositeNode: CompositeNode,
    selectedFloors: string[],
    selectedZones: string[],
    styles: TreeNodeStyle
): { className: string | undefined; icons: TreeNodeIcon[] } {
    const nodeId = compositeNode.id || '';
    const icons = [] as TreeNodeIcon[];

    let className: string | undefined;

    const hasGeo = hasGeoData(compositeNode);
    if (selectedFloors.includes(nodeId)) {
        className = clsx(styles.selectedFloorFocus, !hasGeo && styles.nonDefined);
        if (!hasGeo) {
            icons.push({ ItemIcon: GeoDataIcon });
        }
        icons.push({ ItemIcon: SelectedFloorIcon });
    } else if (!hasGeo) {
        className = styles.nonDefined;
        icons.push({ ItemIcon: GeoDataIcon });
    }

    const entityType = CompositeNode.entityType(compositeNode);
    if (compositeNode.id && entityType === EntityType.REGION && (compositeNode as Region).type === RegionType.ZONE) {
        const isSelected = selectedZones.includes(compositeNode.id);
        const params: StringMap = {
            id: compositeNode.id,
            selected: `${isSelected}`,
        };
        const color = (compositeNode as Region).props?.find(p => p.key === 'color');
        if (color?.value) {
            params.color = color.value;
        }
        params.color = isSelected ? color?.value || '#92a8d1' : '#b2b2b2';
        if (isSelected) {
            className = clsx(styles.selectedZoneFocus, !hasGeo && styles.nonDefined);
        }

        icons.push({ ItemIcon: ZoneIcon, params });
    }

    return {
        className,
        icons,
    };
}

export function createTreeData(
    nodes: CompositeNode[],
    selectedFloors: string[],
    selectedZones: string[],
    styles: TreeNodeStyle,
    parentId: string,
    rootState: RootState
): TreePanelNode[] {
    const treeNodes: TreePanelNode[] = [];

    nodes?.forEach(compositeNode => {
        // ignore some properties from the filtered tree which has some stale data
        const { geoLocation, geoBoundaries, state, ...filteredProperties } = compositeNode;
        // ignore some properties from the Redux which can be updated in filtered tree
        const { children, name, ...reduxStoreProps } = Selectors.createEntitySelector(compositeNode)(
            rootState
        ) as CompositeNode;

        const node = { ...filteredProperties, ...reduxStoreProps };
        const nodeId = node.id || '';
        const treeNode: TreePanelNode = {
            nodeId: `${nodeId}_${parentId}`,
            labelText: node.name,
            labelIcon: resolveNodeIcon(node),
            nodeData: node,
        };
        treeNode.childrenNodes = node.children
            ? createTreeData(
                  Object.values(node.children),
                  selectedFloors,
                  selectedZones,
                  styles,
                  treeNode.nodeId,
                  rootState
              )
            : [];

        const itemStyles = calculateTreeNodeStyles(node as CompositeNode, selectedFloors, selectedZones, styles);
        treeNode.className = itemStyles.className;
        treeNode.labelInfo = (
            <span>
                {itemStyles.icons.map((icon, idx) => (
                    <icon.ItemIcon key={idx} {...icon.params} />
                ))}
            </span>
        );

        treeNodes.push(treeNode);
    });

    return treeNodes;
}

function findExpendedNodesTree(data: TreePanelNode[], selectedNode: string): string[] {
    const path: string[] = [];
    data.forEach(node => {
        if ((node.nodeData as ISchoolElement).id === selectedNode) {
            path.push(node.nodeId);
        } else if (node.childrenNodes?.length) {
            const subPath = findExpendedNodesTree(node.childrenNodes, selectedNode);
            if (subPath?.length) {
                path.push(node.nodeId);
                subPath.forEach(s => path.push(s));
            }
        }
    });

    return path;
}

function findSingleExpendedNodesPath(data: TreePanelNode[], selectedNode: string): string[] {
    const path: string[] = [];
    data.forEach(node => {
        if (!path.length) {
            if ((node.nodeData as ISchoolElement).id === selectedNode) {
                path.push(node.nodeId);
            } else if (node.childrenNodes?.length) {
                const subPath = findSingleExpendedNodesPath(node.childrenNodes, selectedNode);
                if (subPath?.length) {
                    path.push(node.nodeId);
                    subPath.forEach(s => path.push(s));
                }
            }
        }
    });

    return path;
}

export default function DeviceTreeView(props: DeviceTreeViewProps): JSX.Element {
    const userPreferences = userPreferencesService.loadPreferences(UserContexts.deviceTree);
    const classes = useStyles();
    const schoolRegion = useSelector(Selectors.createSchoolRegionsTreeSelector(props.schoolId));
    const inputRef = useRef<HTMLInputElement>(null);
    const [searchText, setSearchText] = useState<string>(userPreferences.searchText || '');

    const [inputText, setInputText] = useState<string>(userPreferences.searchText || '');
    const [itemToFocus, setItemToFocus] = useState<string | null>(null);

    let itemToFocusEl: HTMLElement | null = null;
    const dispatch = useDispatch();
    const userRole = useSelector<RootState, string[]>(({ auth }) => auth?.user?.role || []);

    const rootState = useSelector<RootState, RootState>(state => state);
    const floors = useSelector(Selectors.selectSelectedFloors);
    const selectedZones = useSelector<RootState, string[]>(Selectors.selectedZonesSelector);

    const treeData = React.useMemo(() => {
        const treeStyles: TreeNodeStyle = {
            selectedFloor: classes.selectedFloor,
            selectedFloorFocus: classes.selectedFloorFocus,
            selectedZoneFocus: classes.selectedZoneFocus,
            nonDefined: classes.nonDefinedEntity,
        };
        const mapDataProvider: ICompositeTreeDataProvider = new CompositeTreeDataProvider(
            schoolRegion as CompositeNode,
            {
                filter: { name: searchText },
            }
        );
        const selectedFloors = floors.filter(floor => floor && !!floor.id).map(floor => floor.id) as string[];
        return createTreeData([mapDataProvider.root], selectedFloors, selectedZones, treeStyles, '', rootState);
    }, [classes, rootState, schoolRegion, searchText, floors, selectedZones]);

    const expendedNodes = resolveTopNodesIds(treeData, 2);
    const [schoolId, setSchoolId] = useState<string | undefined>(undefined);
    const [expanded, setExpanded] = useState<string[]>(userPreferences.expanded || []);
    const [selected, setSelected] = useState<string[]>(userPreferences.selected || []);

    /**
     * persists the user preferences
     */
    useEffect(() => {
        userPreferencesService.updatePreference(UserContexts.deviceTree, {
            selected,
            searchText,
            expanded,
        });
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [selected, searchText, userPreferencesService, expanded]);

    useEffect(() => {
        if (props.schoolId !== schoolId) {
            setSchoolId(props.schoolId);
            setSelected(userPreferences.selected || []);
            setExpanded(userPreferences.expanded || expendedNodes);
            setSearchText((userPreferences.searchText as string) || '');
            setInputText(userPreferences.searchText || '');
        }

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [props.schoolId]);

    const createNodeActions = (node: CompositeNode, extraActions?: ActionMap): IDeviceAction[] => {
        const actions: IDeviceAction[] = [];
        const elementTool = resolveEntityTool(CompositeNode.entityType(node));
        if (elementTool) {
            const entityActions = elementTool.getDeviceActions?.(node, userRole) || [];
            entityActions?.forEach(action => actions.push(action));
            if (extraActions) {
                if (!userRole.includes('ROLE_SYSTEM_ADMINISTRATOR')) {
                    // we are clearing map edit from the list of default actions
                    // for all non-admin roles.
                    extraActions[RegionType.SCHOOL] = [];
                }
                const actionType =
                    node?.compositeType === CompositeType.CONTAINER
                        ? (node as Region).type
                        : CompositeNode.entityType(node);
                const entityActions2 = extraActions[actionType || ''];
                entityActions2?.forEach(action => actions.push(action));
            }
        }

        return actions;
    };

    const onAction = (nodeIds: string[], action: string): void => {
        let actionName = action;
        const deviceActions: { action: IDeviceAction; model: CompositeNode; requireConfirmation: boolean }[] = [];
        nodeIds?.forEach(nodeId => {
            const node = findComposite(schoolRegion as CompositeNode, extractNodeId(nodeId));
            if (node) {
                const actions: IDeviceAction[] = createNodeActions(node, props.extraActions);
                const deviceAction = actions.find(a => a.name === action);
                if (deviceAction) {
                    deviceActions.push({
                        action: deviceAction,
                        model: node,
                        requireConfirmation: deviceAction.requireConfirmation?.() || false,
                    });
                    actionName = deviceAction.label;
                }
            }
        });

        if (deviceActions.length) {
            const showConfirmation = deviceActions.find(a => a.requireConfirmation);
            if (showConfirmation) {
                const devices = deviceActions.map(a => a.model);
                const executeAction = (): void => {
                    deviceActions.forEach(a => a.action.execute(a.model, props.map));
                    dispatch(FuseActions.closeDialog());
                };

                dispatch(
                    FuseActions.openDialog({
                        children: (
                            <>
                                <DialogTitle id="alert-dialog-title">Execute Device Action</DialogTitle>
                                <DialogContent>
                                    <DialogContentText id="alert-dialog-description">
                                        Please confirm you want to {actionName} for the following region(s)/device(s):
                                    </DialogContentText>
                                    <List component="nav" dense className={classes.deviceNames}>
                                        {devices.map(node => (
                                            <ListItem key={node.id}>
                                                <ListItemText primary={friendlyName(node)} />
                                            </ListItem>
                                        ))}
                                    </List>
                                </DialogContent>
                                <DialogActions>
                                    <Button
                                        onClick={() => dispatch(FuseActions.closeDialog())}
                                        color="primary"
                                        autoFocus
                                    >
                                        No
                                    </Button>
                                    <Button onClick={() => executeAction()} color="primary" autoFocus>
                                        Yes
                                    </Button>
                                </DialogActions>
                            </>
                        ),
                    })
                );
            } else {
                deviceActions.forEach(a => a.action.execute(a.model, props.map));
            }
        }
    };

    const doSearch = (text: string): void => {
        setSearchText(text);
        setInputText(text);
    };
    const search = useDebounce((query: string) => doSearch(query), 1000);

    const onSearchKeyPress = (e: React.KeyboardEvent<HTMLInputElement>): void => {
        if (e.keyCode === 13) {
            e.preventDefault();
            const text = (e.target as HTMLInputElement).value;
            doSearch(text); // do immediate search on enter
        }
    };
    const onSearch = (e: React.MouseEvent): void => {
        e.preventDefault();
        const text = inputRef.current?.value || '';
        doSearch(text); // do immediate search on search icon click
    };

    const onSearchChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
        const text = e.target.value;
        setInputText(text);
        search(text);
    };

    const handleToggle = (nodeIds: string[]): void => {
        setExpanded(nodeIds);
    };

    const handleSelect = (nodeIds: string[]): void => {
        setSelected(nodeIds);
    };

    const actionListResolver = (nodeIds: string[]): ContextMenuAction[] => {
        const actions: ContextMenuAction[] = [];
        nodeIds?.forEach(nodeId => {
            // find the node itself
            const updatedNode = findComposite(schoolRegion as CompositeNode, extractNodeId(nodeId)) as CompositeNode;
            // get the node latest state (including state and geo coordinates)
            const node = Selectors.createEntitySelector(updatedNode)(rootState) as CompositeNode;
            const nodeActions = node ? createNodeActions(node, props.extraActions) : [];
            nodeActions.forEach(a => {
                if (!actions.find(action => action.name === a.name)) {
                    a.node = node;
                    actions.push(a);
                }
            });
        });

        return actions;
    };

    const onItemClick = (nodeId: string /* , nodeData: unknown | undefined */): void => {
        /*
        const node = nodeData as CompositeNode;
        if ((node as Region).type !== RegionType.SCHOOL) {
            props.map.getMapComponent()?.animateFeature(extractNodeId(nodeId));
        }
        */
        props.map.getMapComponent()?.animateFeature(extractNodeId(nodeId));
    };

    const onItemRendered = (nodeId: string, treeItem: HTMLElement | null, nodeData: unknown | undefined): void => {
        const entity = nodeData as ISchoolElement;
        if (treeItem && selected.includes(nodeId) && !itemToFocusEl && itemToFocus === nodeId) {
            itemToFocusEl = (treeItem as unknown) as HTMLElement;
        }
    };

    useEffect(() => {
        // code to run after render goes here
        if (itemToFocusEl) {
            const el = itemToFocusEl;
            setItemToFocus(null);

            setTimeout(() => {
                el.focus();
                el.scrollIntoView({
                    block: 'center', // "center" | "end" | "nearest" | "start";
                    inline: 'center',
                    behavior: 'auto', // "auto" | "smooth";
                });
            }, 500);
        }

        const subcription = EventBus.subscribe(new SelectInTreeEvent(), (event: SelectInTreeEvent): void => {
            const { nodeId } = event;
            if (nodeId) {
                const nodesPath = findSingleExpendedNodesPath(treeData, nodeId);
                setExpanded(nodesPath);
                const selectedNodeId = nodesPath[nodesPath.length - 1];
                setSelected([selectedNodeId]);
                setItemToFocus(selectedNodeId);
            }
        });

        // cleanup this component
        return () => {
            subcription.unsubscribe();
        };
    }, [itemToFocusEl, treeData]);

    return (
        <div className={classes.wrapper}>
            <Paper component="form" className={classes.searchRoot}>
                <InputBase
                    inputRef={inputRef}
                    className={classes.searchInput}
                    placeholder="Filter ..."
                    inputProps={{ 'aria-label': 'search ...' }}
                    onKeyDown={onSearchKeyPress}
                    onChange={onSearchChange}
                    value={inputText}
                />
                <IconButton onClick={onSearch} type="submit" className={classes.iconButton} aria-label="search">
                    <SearchIcon />
                </IconButton>
            </Paper>
            <div className={classes.treeContainer}>
                <TreePanel
                    multiple
                    variant="simple"
                    onAction={onAction}
                    onSelectionChanged={handleSelect}
                    onExpantionChanged={handleToggle}
                    onItemClick={onItemClick}
                    actionListResolver={actionListResolver}
                    className={classes.treePanel}
                    data={treeData}
                    selected={selected}
                    expanded={expanded}
                    onItemRendered={onItemRendered}
                />
            </div>
        </div>
    );
}
