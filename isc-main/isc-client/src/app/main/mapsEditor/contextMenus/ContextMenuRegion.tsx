import React, { useMemo, useCallback } from 'react';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import LayersIcon from '@material-ui/icons/Layers';
import RemoveLayersIcon from '@material-ui/icons/LayersClear';
import {
    Button,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Divider,
    IconButton,
    makeStyles,
    Tooltip,
} from '@material-ui/core';
import Region from 'app/domain/region';
import { useDispatch, useSelector } from 'react-redux';
import { setFloorSelection, clearFloorSelection } from 'app/store/actions';
import { EntityMap, RootState } from 'app/store/appState';
import { CompositeNodesMap } from 'app/domain/composite';
import * as Selectors from 'app/store/selectors';
import clsx from 'clsx';
import { EntityType, RegionType } from 'app/utils/domain.constants';
import { MeetingRoom } from '@material-ui/icons';
import { BsFillLockFill, BsFillUnlockFill, BsLock, BsUnlock } from 'react-icons/bs';
import * as AppActions from 'app/store/actions/app';
import ISchoolElement from 'app/domain/school.element';
import IDeviceAction from 'app/components/map/deviceActions/IDeviceAction';
import LockDown from 'app/components/map/deviceActions/LockDown';
import EmergencyOpen from 'app/components/map/deviceActions/EmergencyOpen';
import EndEmergency from 'app/components/map/deviceActions/EndEmergency';
import OpenDoor from 'app/components/map/deviceActions/OpenDoor';
import * as FuseActions from 'app/store/actions/fuse';
import { friendlyName } from 'app/components/map/utils/MapUtils';
import { authRoles } from 'app/auth';
import FuseUtils from '@fuse/utils';
import FindOnTree from 'app/components/map/deviceActions/FindOnTree';
import { CgListTree } from 'react-icons/cg';
import { IContextMenuProps, resolveEntityName } from './ContextMenu';

const useStyles = makeStyles(theme => ({
    root: {
        minWidth: '100%',
        backgroundColor: theme.palette.background.paper,
        '& .MuiListItem-button': {
            zIndex: 100,
        },
    },

    cotextMenuItem: {
        paddingTop: '0px',
        paddingBottom: '0px',
        fontSize: '1em',

        '&.Mui-selected': {
            backgroundColor: 'rgba(0, 0, 0, 0.2)',
        },
    },

    deviceNames: {
        fontStyle: 'bold',
        overflow: 'auto',
        maxHeight: 120,
    },
}));

/**
 * Display the context menu for Regions
 */
export default function ContextMenuRegion(props: IContextMenuProps): JSX.Element {
    const { selectedFeature } = props;
    const classes = useStyles();
    const model = selectedFeature?.getProperties()?.data as ISchoolElement;
    const dispatch = useDispatch();
    const userRole = useSelector<RootState, string[]>(({ auth }) => auth?.user?.role || []);
    const hasPermission = useMemo(() => FuseUtils.hasPermission(authRoles.Analyst, userRole), [userRole]);

    const allRegions = useSelector<RootState>(
        (state: RootState): EntityMap<Region> => {
            return state.domain.entities.regions;
        }
    );

    const clearSelectedFloor = (buildingId: string): void => {
        dispatch(clearFloorSelection(buildingId));
    };

    /**
     * Clear the current elements from the map and set the new ones based on the selected floor
     * @param floor
     */
    const setFloorElementsOnMap = (floor: Region): void => {
        dispatch(setFloorSelection(floor));
    };

    const onServerAction = (event: React.MouseEvent<Element>, action: IDeviceAction): void => {
        event.preventDefault();
        event.stopPropagation();

        const executeAction = (): void => {
            const deviceAction = {
                entityType: EntityType.DOOR,
                deviceId: model.id,
                action: action.name,
            };
            dispatch(AppActions.deviceAction(deviceAction));
            dispatch(FuseActions.closeDialog());
        };

        const requireConfirmation = action.requireConfirmation?.() || false;
        if (requireConfirmation) {
            const devices = [model];
            dispatch(
                FuseActions.openDialog({
                    children: (
                        <>
                            <DialogTitle id="alert-dialog-title">Execute Device Action</DialogTitle>
                            <DialogContent>
                                <DialogContentText id="alert-dialog-description">
                                    Please confirm you want to {action.label} for the following region(s)/device(s):
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
                                <Button onClick={() => dispatch(FuseActions.closeDialog())} color="primary" autoFocus>
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
            executeAction();
        }

        if (props.closeContextMenu) props.closeContextMenu();
    };

    /**
     * Find the Building or the root of the tree based on the region informed as parameter
     */
    const findRegionRoot = useCallback(
        (region: Region | null): Region | null => {
            if (
                !region ||
                region.type === RegionType.ROOT ||
                region.type === RegionType.BUILDING ||
                region.type === RegionType.SCHOOL ||
                region.type === RegionType.SCHOOL_DISTRICT
            ) {
                return region?.type !== RegionType.ROOT ? region : null;
            }

            let currentParent = null;
            for (let i = 0; i < (!region ? 0 : ((region as Region).parentIds as string[]).length); i += 1) {
                currentParent = findRegionRoot((allRegions as EntityMap<Region>)[(region?.parentIds as string[])[i]]);
                if (currentParent) return currentParent;
            }
            return currentParent;
        },
        [allRegions]
    );

    /**
     * Try to find the building region based on the current regionpened
     * @param region
     */
    const getBuildingFloors = useCallback((building: Region): Region[] => {
        return !building
            ? []
            : Object.values((building as CompositeNodesMap).children)
                  .filter(child => child.type === RegionType.FLOOR)
                  .map(floor => ({
                      // building id: floor. This structure will be used to set the selected floor by building
                      [building.id as string]: floor,
                  }));
    }, []);

    // find top region
    const building = findRegionRoot(selectedFeature?.getProperties()?.data);
    if (building?.type === RegionType.SCHOOL) {
        console.log('School context menu is not implemented');
    } else if (building?.type === RegionType.SCHOOL_DISTRICT) {
        console.log('School district context menu is not implemented');
    }

    // Return all the floors for the current building
    const getParentFloors = useMemo(() => {
        return building && building.type === RegionType.BUILDING ? getBuildingFloors(building) : [];
    }, [building, getBuildingFloors]);

    const onClientAction = (event: React.MouseEvent<Element>, action: IDeviceAction): void => {
        event.preventDefault();
        event.stopPropagation();

        action.execute(model);
        if (props.closeContextMenu) props.closeContextMenu();
    };

    const buildingId = getParentFloors.length > 0 ? Object.keys(getParentFloors[0])[0] : null;
    const selectedFloor = useSelector<RootState, Region | null>(Selectors.getSelectedFloorByBuilding(buildingId || ''));
    return (
        <>
            <List dense disablePadding className={classes.root}>
                {getParentFloors.map((floor: Region) => {
                    const currentFloorData = Object.values(floor)[0];
                    const selected = currentFloorData.id === selectedFloor?.id;
                    return (
                        <ListItem
                            key={currentFloorData.id}
                            dense
                            button
                            onClick={() => setFloorElementsOnMap(floor)}
                            className={clsx(classes.cotextMenuItem, 'context-menu-item')}
                            selected={selected}
                        >
                            <ListItemIcon>
                                <LayersIcon />
                            </ListItemIcon>
                            <ListItemText primary={resolveEntityName(currentFloorData)} />
                        </ListItem>
                    );
                })}

                {buildingId && (
                    <ListItem
                        key="building"
                        dense
                        button
                        onClick={() => clearSelectedFloor(buildingId)}
                        className="context-menu-item"
                    >
                        <ListItemIcon>
                            <RemoveLayersIcon />
                        </ListItemIcon>
                        <ListItemText primary="Clear Floor Selection" />
                    </ListItem>
                )}
            </List>
            {selectedFeature?.getProperties()?.data?.floors?.length ? <Divider /> : null}

            <ListItem key="actions" dense className="context-menu-item">
                <ListItemIcon>
                    <MeetingRoom />
                </ListItemIcon>

                <ListItemText id="switch-list-label-door" primary="Actions" />
                {hasPermission && (
                    <>
                        <Tooltip title="Lock Down" aria-label="show-filters">
                            <IconButton size="small" onClick={event => onServerAction(event, new LockDown())}>
                                <BsFillLockFill />
                            </IconButton>
                        </Tooltip>
                        <Tooltip title="Emergency Open" aria-label="show-filters">
                            <IconButton size="small" onClick={event => onServerAction(event, new EmergencyOpen())}>
                                <BsFillUnlockFill />
                            </IconButton>
                        </Tooltip>
                        <Tooltip title="End Emergency Mode" aria-label="show-filters">
                            <IconButton size="small" onClick={event => onServerAction(event, new EndEmergency())}>
                                <BsLock />
                            </IconButton>
                        </Tooltip>
                        <Tooltip title="Open Door" aria-label="show-filters">
                            <IconButton size="small" onClick={event => onServerAction(event, new OpenDoor())}>
                                <BsUnlock />
                            </IconButton>
                        </Tooltip>
                    </>
                )}
                <Tooltip title="Find on Tree" aria-label="show-filters">
                    <IconButton
                        size="small"
                        style={{ fontSize: '1.4rem' }}
                        onClick={event => onClientAction(event, new FindOnTree())}
                    >
                        <CgListTree />
                    </IconButton>
                </Tooltip>
            </ListItem>
        </>
    );
}
