import React from 'react';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import InfoIcon from '@material-ui/icons/Info';
import { IconButton, makeStyles, Tooltip } from '@material-ui/core';
import IDeviceAction from 'app/components/map/deviceActions/IDeviceAction';
import ISchoolElement from 'app/domain/school.element';
import { CgListTree } from 'react-icons/cg';
import { IContextMenuProps } from './ContextMenu';
import { prettifyName } from '../../../../@common/utils/prettyName';
import FindOnTree from '../../../components/map/deviceActions/FindOnTree';

const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        minWidth: 200,
        backgroundColor: theme.palette.background.paper,
    },
    actionsLabel: {},
}));

/**
 * TODO: TBD
 * Display the context menu for safeties
 */
export default function ContextMenuSafety(props: IContextMenuProps): JSX.Element {
    const model = props.selectedFeature?.getProperties()?.data as ISchoolElement;
    const { selectedFeature } = props;
    const classes = useStyles();

    const onClientAction = (event: React.MouseEvent<Element>, action: IDeviceAction): void => {
        event.preventDefault();
        event.stopPropagation();

        action.execute(model);
        if (props.closeContextMenu) props.closeContextMenu();
    };

    return (
        <List dense disablePadding className={classes.root}>
            <ListItem dense>
                <ListItemIcon>
                    <InfoIcon />
                </ListItemIcon>
                <ListItemText primary={prettifyName(selectedFeature?.getProperties().data?.type)} secondary="Type" />
            </ListItem>
            <ListItem key="actions" dense className="context-menu-item">
                <ListItemText id="switch-list-label-door" primary="Actions" className={classes.actionsLabel} />
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
