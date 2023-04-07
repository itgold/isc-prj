import React, { RefObject, useEffect, useState } from 'react';
import { makeStyles, Theme, createStyles, fade, withStyles } from '@material-ui/core/styles';
import TreeView from '@material-ui/lab/TreeView';
import TreeItem, { TreeItemProps } from '@material-ui/lab/TreeItem';
import Typography from '@material-ui/core/Typography';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import ArrowRightIcon from '@material-ui/icons/ArrowRight';
import { AiOutlinePlusSquare, AiOutlineMinusSquare, AiOutlineCloseSquare } from 'react-icons/ai';
import { BsPlusSquare, BsDashSquare, BsXSquare } from 'react-icons/bs';
import { SvgIconProps } from '@material-ui/core/SvgIcon';
import chroma from 'chroma-js';
import clsx from 'clsx';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import { ListItemIcon, ListItemText } from '@material-ui/core';
import _ from 'lodash';
import ISchoolElement from 'app/domain/school.element';
import { CompositeNode, CompositeNodesMap } from 'app/domain/composite';
// import { FontWeightProperty } from 'csstype';

declare module 'csstype' {
    interface Properties {
        '--tree-view-color'?: string;
        '--tree-view-bg-color'?: string;
        '--tree-view-select-color'?: string;
        '--tree-view-select-bg-color'?: string;
    }
}

export interface ContextMenuAction {
    name: string;
    label: string;
    icon?: React.ElementType<SvgIconProps>;
    node?: CompositeNode;
}

interface ContextMenuPosition {
    mouseX: number;
    mouseY: number;
    ctrlKey: boolean;
}

type TreeItemType = typeof TreeItem;
export type StyledTreeItemProps = TreeItemProps & {
    className?: string;
    bgColor?: string;
    color?: string;
    selectBgColor?: string;
    selectColor?: string;

    nodeData?: unknown;
    labelInfo?: string | React.ReactNode;
    labelText?: string;
    labelIcon?: React.ElementType<SvgIconProps>;

    disabled?: boolean;
    childrenNodes?: TreePanelNode[];

    onShowContextMenu?: (nodeId: string, nodeData: unknown | undefined, pos: ContextMenuPosition) => void;
    onItemClick?: (nodeId: string, nodeData: unknown | undefined) => void;
    onItemRender?: (node: TreePanelNode, itemProps: StyledTreeItemProps, nodeData: unknown) => React.ReactNode;
    onItemRendered?: (nodeId: string, treeItem: TreeItemType | null, nodeData: unknown | undefined) => void;
};

export function resolveTopNodesIds(data: TreePanelNode[], levels: number): string[] {
    const nodeIds: string[] = [];

    let currentLevel = 0;
    let nodesToProcess = data;
    while (nodesToProcess.length && currentLevel < levels) {
        const nextNodes: TreePanelNode[] = [];
        nodesToProcess.forEach(node => {
            nodeIds.push(node.nodeId);
            if (node.childrenNodes?.length) {
                node.childrenNodes.forEach(child => nextNodes.push(child));
            }
        });

        nodesToProcess = nextNodes;
        currentLevel += 1;
    }

    return nodeIds;
}

const useTreeItemStyles = makeStyles((theme: Theme) => {
    const color = chroma(theme.palette.text.primary);

    return createStyles({
        root: {
            color: theme.palette.text.secondary,
            '&$selected > $content': {
                backgroundColor: `var(--tree-view-select-bg-color, ${theme.palette.primary.main})`,
                color: `var(--tree-view-select-color, ${theme.palette.primary.contrastText})`,
            },
            '&:focus > $content $label, &:hover > $content $label, &$selected > $content $label': {
                backgroundColor: 'transparent',
            },
            '&:focus > $content .colored-icon, &$selected > $content .colored-icon': {
                backgroundColor: 'white',
            },
        },
        content: {
            color: theme.palette.text.secondary,
            fontWeight: theme.typography.fontWeightMedium as 'normal',
            '$expanded > &': {
                fontWeight: theme.typography.fontWeightRegular,
            },
            position: 'relative',
        },
        group: {
            marginLeft: 7,
            paddingLeft: 10,
            '& $content': {
                paddingLeft: 0,
            },
            borderLeft: `1px dashed ${color.alpha(0.4).css()}`,
        },
        iconContainer: {
            '& .close': {
                opacity: 0.3,
            },
            position: 'absolute',
            width: 10,
            marginRight: 0,
            display: 'block',
            zIndex: 100,
        },
        expanded: {},
        selected: {},
        label: {
            fontWeight: 'inherit',
            color: 'inherit',
            paddingLeft: 6,
        },
        labelRoot: {
            display: 'flex',
            alignItems: 'center',
            padding: theme.spacing(0.5, 0),
            paddingLeft: 14,
        },
        labelIcon: {
            marginRight: theme.spacing(1),
            minWidth: 14,
            minHeight: 14,
        },
        labelText: {
            // fontWeight: 'inherit',
            fontWeight: 'normal',
            flexGrow: 1,
            whiteSpace: 'nowrap',
            overflow: 'hidden',
            textOverflow: 'ellipsis',
        },
        labelInfo: {
            marginRight: 14,
        },
    });
});

const TREE_STYLE_VARIANTS = {
    simple: {
        collapse: <ArrowDropDownIcon />,
        expand: <ArrowRightIcon />,
        end: <div style={{ width: 2 }} />,
    },
    bigSquare: {
        collapse: <BsDashSquare />,
        expand: <BsPlusSquare />,
        end: <BsXSquare style={{ opacity: 0.3 }} />,
    },
    smallSquare: {
        collapse: <AiOutlineMinusSquare />,
        expand: <AiOutlinePlusSquare />,
        end: <AiOutlineCloseSquare style={{ opacity: 0.3 }} />,
    },
};

const StyledMenuItem = withStyles((theme: Theme) => ({
    root: {
        minWidth: '180px',

        '&:hover': {
            backgroundColor: fade(theme.palette.secondary.dark, 0.25),
            '& .MuiListItemIcon-root, & .MuiListItemText-primary': {
                color: theme.palette.secondary.contrastText,
            },
        },
    },
}))(MenuItem);

const StyledTreeItem = React.memo(function StyledTreeItemComponent(props: StyledTreeItemProps): JSX.Element {
    const itemRef: RefObject<typeof TreeItem> = React.createRef();
    const classes = useTreeItemStyles();
    const {
        labelText,
        labelIcon: LabelIcon,
        labelInfo,
        nodeData,
        color,
        bgColor,
        selectColor,
        selectBgColor,
        childrenNodes,
        className,
        onShowContextMenu,
        onItemClick,
        onItemRender,
        onItemRendered,
        onIconClick,
        ...other
    } = props;

    const handleRightClick = (event: React.MouseEvent<HTMLLIElement>): void => {
        event.preventDefault();
        event.stopPropagation();

        if (onShowContextMenu) {
            onShowContextMenu(props.nodeId, nodeData, {
                mouseX: event.clientX - 2,
                mouseY: event.clientY - 4,
                ctrlKey: event.ctrlKey || event.metaKey || event.shiftKey,
            });
        }
    };

    const handleLabelClick = (e: React.MouseEvent<Element>): void => {
        if (props.disabled) {
            e.preventDefault();
            e.stopPropagation();
        } else if (onItemClick) {
            onItemClick(props.nodeId, nodeData);
        }
    };

    useEffect(() => {
        props.onItemRendered?.(props.nodeId, itemRef.current, nodeData);
    });

    const cleanLabelText = (labelText || '').replace(/<\/?[^>]+(>|$)/g, '');
    return (
        <>
            <TreeItem
                onIconClick={onIconClick}
                onContextMenu={handleRightClick}
                onLabelClick={handleLabelClick}
                ref={itemRef}
                label={
                    onItemRender ? (
                        onItemRender(props, props, nodeData)
                    ) : (
                        <div className={classes.labelRoot}>
                            {LabelIcon && <LabelIcon color="inherit" className={classes.labelIcon} />}
                            <Typography
                                title={cleanLabelText}
                                className={classes.labelText}
                                variant="body2"
                                dangerouslySetInnerHTML={{ __html: labelText || '' }}
                            />
                            <Typography className={classes.labelInfo} variant="caption" color="inherit">
                                {labelInfo}
                            </Typography>
                        </div>
                    )
                }
                style={
                    {
                        '--tree-view-color': color,
                        '--tree-view-bg-color': bgColor,
                        '--tree-view-select-color': selectColor,
                        '--tree-view-select-bg-color': selectBgColor,
                    } as React.CSSProperties
                }
                classes={{
                    root: classes.root,
                    content: clsx(classes.content, className),
                    expanded: classes.expanded,
                    selected: classes.selected,
                    group: classes.group,
                    label: classes.label,
                    iconContainer: classes.iconContainer,
                }}
                {...other}
            >
                {childrenNodes?.map(node => (
                    <StyledTreeItem
                        key={node.nodeId}
                        onIconClick={onIconClick}
                        onShowContextMenu={onShowContextMenu}
                        onItemClick={onItemClick}
                        childrenNodes={node.childrenNodes}
                        onItemRender={onItemRender}
                        onItemRendered={onItemRendered}
                        {...node}
                    />
                ))}
            </TreeItem>
        </>
    );
});

const useStyles = makeStyles(() =>
    createStyles({
        root: {
            flexGrow: 1,
            maxWidth: 400,
        },
    })
);

export type ItemRenderer = (node: TreePanelNode, itemProps: StyledTreeItemProps, nodeData: unknown) => React.ReactNode;

interface TreePanelProps {
    className?: string;
    variant?: 'simple' | 'bigSquare' | 'smallSquare';
    style?: React.CSSProperties | undefined;

    data: TreePanelNode[];
    selected?: string[];
    expanded?: string[];
    multiple?: boolean;

    onAction?: (nodeIds: string[], action: string) => void;
    onSelectionChanged?: (nodeIds: string[]) => void;
    onExpantionChanged?: (nodeIds: string[]) => void;
    onItemClick?: (nodeId: string, nodeData: unknown | undefined) => void;
    actionListResolver?: (nodeIds: string[]) => ContextMenuAction[];

    onItemRender?: ItemRenderer;
    onItemRendered?: (nodeId: string, treeItem: HTMLElement | null, nodeData: unknown | undefined) => void;
}

export type TreePanelNode = StyledTreeItemProps;

/*
<TreeView>
    <StyledTreeItem nodeId="3" labelText="Categories" labelIcon={Label}>
        <StyledTreeItem
            nodeId="5"
            labelText="Social"
            labelIcon={SupervisorAccountIcon}
            labelInfo="90"
            color="#1a73e8"
            bgColor="#e8f0fe"
        />
        <StyledTreeItem
            nodeId="6"
            labelText="Updates"
            labelIcon={InfoIcon}
            labelInfo="2,294"
            color="#e3742f"
            bgColor="#fcefe3"
        />
    </StyledTreeItem>
</TreeView>
*/
function TreePanel(props: TreePanelProps): JSX.Element {
    const classes = useStyles();

    const treeStyle = TREE_STYLE_VARIANTS[props.variant || 'simple'];
    const defaultCollapseIcon = treeStyle.collapse;
    const defaultExpandIcon = treeStyle.expand;
    const defaultEndIcon = treeStyle.end;
    const data = props.data || [];

    const [context, setContext] = useState<{
        contextItemPos: ContextMenuPosition | null;
        actions: ContextMenuAction[] | null;
    }>({
        contextItemPos: null,
        actions: null,
    });

    // This is workaround to do not select the node when you just want to expand it
    let flag = true;

    const handleToggle = (event: React.ChangeEvent<unknown>, nodeIds: string[] | string | undefined): void => {
        if (!flag && props.onExpantionChanged) {
            let selectedNodes: string[] = [];
            if (nodeIds) {
                selectedNodes = Array.isArray(nodeIds) ? nodeIds : [nodeIds];
            }

            props.onExpantionChanged(selectedNodes);
        }
    };

    const onItemClick = (nodeId: string, nodeData: unknown | undefined): void => {
        flag = true;
        if (props.onItemClick) {
            props.onItemClick(nodeId, nodeData);
        }
    };

    const onIconClick = (): void => {
        flag = false;
    };

    const handleSelect = (event: React.ChangeEvent<unknown>, nodeIds: string[] | string | undefined): void => {
        if (event.preventDefault) {
            event.preventDefault();
            event.stopPropagation();
        }

        if (flag && props.onSelectionChanged) {
            let selectedNodes: string[] = [];
            if (nodeIds) {
                selectedNodes = Array.isArray(nodeIds) ? nodeIds : [nodeIds];
            }

            props.onSelectionChanged(selectedNodes);
        }
    };

    const onContextMenuClose = (): void => {
        setContext({
            ...context,
            contextItemPos: null,
            actions: null,
        });
    };
    const onContextMenuClick = (action: string): void => {
        if (props.selected?.length && props.onAction) {
            props.onAction(props.selected, action);
        }

        onContextMenuClose();
    };

    const onShowContextMenu = (nodeId: string, nodeData: unknown | undefined, pos: ContextMenuPosition): void => {
        let selectedNodes = props.selected || [];
        let notify = false;

        if (!selectedNodes.includes(nodeId)) {
            if (!pos.ctrlKey) {
                notify = !selectedNodes.includes(nodeId);
                selectedNodes = [nodeId];
            } else {
                selectedNodes.push(nodeId);
                notify = true;
            }
        }

        if (notify) {
            handleSelect({ target: {} } as React.ChangeEvent<unknown>, selectedNodes);
        }

        if (props.actionListResolver) {
            const actions = props.actionListResolver(selectedNodes);
            if (actions) {
                setContext({
                    ...context,
                    contextItemPos: pos,
                    actions,
                });
            }
        }
    };

    const onItemRendered = (nodeId: string, treeItem: TreeItemType | null, nodeData: unknown | undefined): void => {
        const entity = nodeData as ISchoolElement;
        if (entity?.id && props.onItemRendered) {
            props.onItemRendered(nodeId, (treeItem as unknown) as HTMLElement, nodeData);
        }
    };

    const childrenHasContainer = (node: CompositeNode): boolean => {
        if (!node.children || (node.children && Object.keys(node.children).length === 0)) {
            return false;
        }

        return (
            Object.keys(node.children as CompositeNodesMap)
                .reduce((prev, curr) => [...prev, (node.children as CompositeNodesMap)[curr]], Array<CompositeNode>())
                .filter(child => child.compositeType === 'CONTAINER').length > 0
        );
    };

    /**
     * Check whether the action menu should be disabled or not
     * @param action
     * @returns
     */
    const disableMenu = (action: ContextMenuAction): boolean => {
        if (
            // if the action is not Find on Map...
            action.name !== 'highlight' ||
            // does the node has not boundaries or geo coordinates?
            action.node?.geoBoundaries !== undefined ||
            action.node?.geoLocation !== undefined ||
            // does the node has children as container?
            (action.node && childrenHasContainer(action.node))
        )
            return false; // false meas that the menu should NOT be disabled
        return true;
    };
    return (
        <>
            {(props.multiple && (
                <TreeView
                    multiSelect
                    expanded={props.expanded}
                    selected={props.selected}
                    onNodeToggle={(e, nodeIds) => handleToggle(e, nodeIds)}
                    onNodeSelect={(e: React.ChangeEvent<unknown>, nodeIds: string[] | string | undefined) =>
                        handleSelect(e, nodeIds)
                    }
                    className={clsx(classes.root, props.className)}
                    defaultCollapseIcon={defaultCollapseIcon}
                    defaultExpandIcon={defaultExpandIcon}
                    defaultEndIcon={defaultEndIcon}
                    onContextMenu={(event: React.MouseEvent) => event.preventDefault()}
                    style={props.style}
                >
                    {data.map(node => (
                        <StyledTreeItem
                            key={node.nodeId}
                            onShowContextMenu={onShowContextMenu}
                            onItemClick={onItemClick}
                            onIconClick={onIconClick}
                            childrenNodes={node.childrenNodes}
                            onItemRender={props.onItemRender}
                            onItemRendered={onItemRendered}
                            {...node}
                        />
                    ))}
                </TreeView>
            )) ||
                (!props.multiple && (
                    <TreeView
                        expanded={props.expanded}
                        selected={props.selected}
                        onNodeToggle={(e, nodeIds) => handleToggle(e, nodeIds)}
                        onNodeSelect={(e: React.ChangeEvent<unknown>, nodeIds: string[] | string | undefined) =>
                            handleSelect(e, nodeIds)
                        }
                        className={clsx(classes.root, props.className)}
                        defaultCollapseIcon={defaultCollapseIcon}
                        defaultExpandIcon={defaultExpandIcon}
                        defaultEndIcon={defaultEndIcon}
                        onContextMenu={(event: React.MouseEvent) => event.preventDefault()}
                        style={props.style}
                    >
                        {data.map(node => (
                            <StyledTreeItem
                                key={node.nodeId}
                                onShowContextMenu={onShowContextMenu}
                                onItemClick={onItemClick}
                                onIconClick={onIconClick}
                                childrenNodes={node.childrenNodes}
                                onItemRender={props.onItemRender}
                                {...node}
                            />
                        ))}
                    </TreeView>
                ))}

            {context.actions?.length && (
                <Menu
                    autoFocus={false}
                    keepMounted
                    disableAutoFocusItem={false}
                    open={context.contextItemPos !== null}
                    onClose={onContextMenuClose}
                    anchorReference="anchorPosition"
                    anchorPosition={
                        context.contextItemPos?.mouseY !== null && context.contextItemPos?.mouseX !== null
                            ? { top: context.contextItemPos?.mouseY || 0, left: context.contextItemPos?.mouseX || 0 }
                            : undefined
                    }
                >
                    {context.actions.map(action => {
                        return (
                            <StyledMenuItem
                                key={action.name}
                                disabled={disableMenu(action)}
                                onClick={() => onContextMenuClick(action.name)}
                            >
                                <>
                                    {action.icon && (
                                        <ListItemIcon style={{ minWidth: '30px' }}>
                                            <action.icon style={{ display: 'block', margin: 'auto' }} />
                                        </ListItemIcon>
                                    )}
                                    <ListItemText primary={action.label} />
                                </>
                            </StyledMenuItem>
                        );
                    })}
                </Menu>
            )}
        </>
    );
}

export default React.memo(TreePanel, (prevProps: TreePanelProps, nextProps: TreePanelProps) => {
    const dataChanged = !_.isEqual(prevProps.data, nextProps.data);
    const selectedChanged = !_.isEqual(prevProps.selected, nextProps.selected);
    const expandedChanged = !_.isEqual(prevProps.expanded, nextProps.expanded);
    return !(dataChanged || selectedChanged || expandedChanged);
});
