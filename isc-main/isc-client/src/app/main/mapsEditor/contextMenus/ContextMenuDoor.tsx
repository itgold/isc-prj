import React, { useMemo } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import {
    IconButton,
    Tooltip,
    ListItemSecondaryAction,
    DialogTitle,
    DialogContent,
    DialogContentText,
    DialogActions,
    Button,
} from '@material-ui/core';
import { BsFillLockFill, BsLock, BsFillUnlockFill, BsUnlock } from 'react-icons/bs';
import { CgListTree } from 'react-icons/cg';
import { useSelector, useDispatch } from 'react-redux';
import { EntityType } from 'app/utils/domain.constants';
import * as Selectors from 'app/store/selectors';
import * as AppActions from 'app/store/actions/app';
import * as FuseActions from 'app/store/actions/fuse';
import IDeviceAction from 'app/components/map/deviceActions/IDeviceAction';
import LockDown from 'app/components/map/deviceActions/LockDown';
import EmergencyOpen from 'app/components/map/deviceActions/EmergencyOpen';
import EndEmergency from 'app/components/map/deviceActions/EndEmergency';
import OpenDoor from 'app/components/map/deviceActions/OpenDoor';
import { friendlyName } from 'app/components/map/utils/MapUtils';
import CodeDictionary from 'app/domain/codeDictionary';
import FuseUtils from '@fuse/utils';
import { authRoles } from 'app/auth';
import FindOnTree from 'app/components/map/deviceActions/FindOnTree';
import { IContextMenuProps } from './ContextMenu';
import ISchoolElement from '../../../domain/school.element';
import { EntityMap, RootState } from '../../../store/appState';
import { IEntity } from '../../../domain/entity';

const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        backgroundColor: theme.palette.background.paper,
    },
    actionIcon: {
        minWidth: 30,
    },
    actionsLabel: {},
    custom: {
        zIndex: 1,
    },
    deviceNames: {
        fontStyle: 'bold',
        overflow: 'auto',
        maxHeight: 120,
    },
}));

const prettyStateName = (fullLabel: string): string => {
    switch (fullLabel) {
        case 'COMMUNICATION':
            return 'COMMS';
        default:
            return fullLabel;
    }
};

/**
 * Renders the menu context for Doors
 */

export default function ContextMenuDoor(props: IContextMenuProps): JSX.Element {
    const model = props.selectedFeature?.getProperties()?.data as ISchoolElement;
    const deviceCodes = useSelector<RootState, EntityMap<CodeDictionary>>(Selectors.deviceCodeSelector);
    const device = useSelector<RootState, IEntity | undefined>(Selectors.createEntitySelector(model)) as ISchoolElement;
    const userRole = useSelector<RootState, string[]>(({ auth }) => auth?.user?.role || []);
    const hasPermission = useMemo(() => FuseUtils.hasPermission(authRoles.Analyst, userRole), [userRole]);

    const classes = useStyles();
    const dispatch = useDispatch();

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

    const onClientAction = (event: React.MouseEvent<Element>, action: IDeviceAction): void => {
        event.preventDefault();
        event.stopPropagation();

        action.execute(model);
        if (props.closeContextMenu) props.closeContextMenu();
    };

    const deviceStateFriendlyName = (deviceState: string): string => {
        return deviceCodes[deviceState]?.shortName || deviceState;
    };
    const deviceStateDescription = (deviceState: string): string | undefined => {
        return deviceCodes[deviceState]?.description;
    };

    return (
        <List dense disablePadding className={classes.root}>
            <List dense disablePadding className="context-menu-state-dsp">
                {device?.state?.map(deviceStateItem => {
                    return (
                        <ListItem key={deviceStateItem.type}>
                            <span className="context-menu-state">{prettyStateName(deviceStateItem.type)}</span>
                            <ListItemSecondaryAction>
                                <span
                                    title={deviceStateDescription(deviceStateItem.value)}
                                    className="context-menu-state"
                                >
                                    {deviceStateFriendlyName(deviceStateItem.value)}
                                </span>
                            </ListItemSecondaryAction>
                        </ListItem>
                    );
                })}
            </List>

            <ListItem key="actions" dense className="context-menu-item">
                <ListItemText id="switch-list-label-door" primary="Actions" className={classes.actionsLabel} />
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
        </List>
    );
}
