import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import InfoIcon from '@material-ui/icons/Info';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import { ListSubheader } from '@material-ui/core';
import { Feature, Map } from 'ol';
import Geometry from 'ol/geom/Geometry';
import { UI_RESOLUTION_BREAKPOUNT } from 'app/utils/ui.constants';
import clsx from 'clsx';
import { IscTableContext } from 'app/components/table/IscTableContext';
import Region from 'app/domain/region';
import { CompositeNode } from 'app/domain/composite';
import { EntityType } from 'app/utils/domain.constants';
import { resolveEntityTool } from 'app/components/map/markers/toolsList';
import ISchoolElement from '../../../domain/school.element';

const camelize = (text: string): string => {
    return text.substring(0, 1) + text.toLowerCase().substring(1);
};

export const resolveEntityName = (region: ISchoolElement): string => {
    let name: string = region?.name || '';

    const entityType = CompositeNode.entityType(region);
    const tool = resolveEntityTool(entityType);
    const label = tool?.getLabel?.(region);

    if (label) {
        name = `${label} ${name}`;
    } else if (entityType === EntityType.REGION) {
        const { type } = region as Region;
        name = `${camelize(type || '')} ${name}`;
    } else {
        name = `${camelize(entityType || '')} ${name}`;
    }

    return name;
};

const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        backgroundColor: theme.palette.background.paper,
        border: '1px solid lightgray',
        zIndex: 1,
        '&::after': {
            left: '50%',
            width: 0,
            border: '1em solid white',
            bottom: '-1.8em',
            height: 0,
            content: 'close-quote',
            position: 'absolute',
            transform: 'rotate(-45deg)',
            boxShadow: '-2px 3px 2px 0px rgba(81, 76, 76, 0.2)',
            boxSizing: 'border-box',
            marginLeft: '-1.5em',
            transformOrigin: '0 0',
            zIndex: -1,
        },
    },
    list: {
        zIndex: 1,
    },
    titleContainer: {
        maxWidth: 300,
        [theme.breakpoints.up(UI_RESOLUTION_BREAKPOUNT)]: {
            maxWidth: 460,
        },
    },
    title: {
        fontSize: '1em',
        width: '100%',
        overflow: 'hidden',
        whiteSpace: 'nowrap',
        textOverflow: 'ellipsis',
        lineHeight: '36px',
    },
    custom: {
        zIndex: 1,
    },
}));

export interface IContextMenuView {
    entityType: string;
    /**
     * Defines the context menu that should appear for the current tool when selecting a mark
     */
    contextMenu?: (props: IContextMenuProps) => React.ReactNode;
    hideTitle?: boolean;
}

/**
 * Describe what default actions should be
 */
export interface IDefaultAction {
    label: string;
    icon: any;
    callback: (feature: Feature<Geometry> | null) => void;
}

export interface IContextMenuProps {
    map?: Map;
    selectedFeature: Feature<Geometry> | null;
    contextMenuViews?: Array<IContextMenuView>;
    /**
     * Allow the user to add default actions which should be used for all marks
     */
    defaultContextMenuActions?: Array<IDefaultAction>;
    /**
     * Method that closes the context menu
     */
    closeContextMenu?: () => void;
    /**
     * Context that could be available
     */
    dataContext?: IscTableContext;
}

/**
 * Displays a context menu above an overlay
 * @param props
 */
const ContextMenu = (props: IContextMenuProps): JSX.Element => {
    const classes = useStyles();
    const { selectedFeature } = props;
    // find the tool according the entityType from data/feature
    const toolFeature = props.contextMenuViews?.find(
        tool => tool.entityType === selectedFeature?.getProperties().entityType
    );

    // get the contextMenu view to be rendered according the tool
    const ContextView = toolFeature ? (toolFeature as any).contextMenu : null;
    const hideTitle = toolFeature ? (toolFeature as any).hideTitle : null;

    return (
        <List
            subheader={
                !hideTitle ? (
                    <ListSubheader component="div" id="nested-list-subheader" className={classes.titleContainer}>
                        <div title={resolveEntityName(selectedFeature?.getProperties().data)} className={classes.title}>
                            {resolveEntityName(selectedFeature?.getProperties().data)}
                        </div>
                    </ListSubheader>
                ) : undefined
            }
            className={classes.root}
            dense
            disablePadding
        >
            {ContextView && (
                <>
                    {selectedFeature?.getProperties().data?.description && (
                        <ListItem dense>
                            <ListItemIcon>
                                <InfoIcon />
                            </ListItemIcon>
                            <ListItemText
                                primary={selectedFeature?.getProperties()?.data?.description}
                                secondary="Description"
                            />
                        </ListItem>
                    )}
                    <ContextView {...props} />
                </>
            )}
            {props?.defaultContextMenuActions?.map((action: IDefaultAction, key: number) => (
                <ListItem
                    dense
                    key={key}
                    button
                    onClick={() => {
                        if (selectedFeature) action.callback(selectedFeature);
                        if (props.closeContextMenu) props.closeContextMenu();
                    }}
                    className={clsx(classes.custom, 'context-menu-item')}
                >
                    <ListItemIcon>{action.icon && action.icon}</ListItemIcon>
                    <ListItemText primary={action.label} />
                </ListItem>
            ))}
        </List>
    );
};
export default ContextMenu;
