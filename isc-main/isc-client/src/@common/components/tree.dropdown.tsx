import React, { RefObject } from 'react';
import _ from 'lodash';
import clsx from 'clsx';

import {
    Button,
    Chip,
    FormControlLabel,
    Popover,
    SelectProps,
    Theme,
    Typography,
    SvgIconProps,
} from '@material-ui/core';
import { createStyles, StyleRules, withStyles } from '@material-ui/core/styles';
import { ClassNameMap } from '@material-ui/styles';
import { CompositeType } from 'app/domain/composite';
import { EMPTY_LIST } from 'app/utils/domain.constants';
import TreePanel, { ItemRenderer, StyledTreeItemProps, TreePanelNode } from './layout/TreePanel';

const styles = (theme: Theme): StyleRules =>
    createStyles({
        root: {
            width: '100%',
            height: '42px',
            verticalAlign: 'bottom',
        },
        dropdownBtn: {
            height: '24px',
            width: '100%',
            borderBottom: '1px solid rgba(0, 0, 0, 0.42)',
            borderBottomLeftRadius: '0px',
            borderBottomRightRadius: '0px',
            paddingLeft: '0px',

            '& .MuiButton-label': {
                display: 'block',
            },
        },
        statusBarMenu: {
            minWidth: '260px',
            borderRadius: '0px ​0px 4px 4p',
            minHeight: '200px',
            maxWidth: '100%',
        },
        treePanel: {
            maxHeight: '200px',
            overflow: 'auto',
        },
        wrapper: {
            flex: '1 1 100%',
            display: 'flex',
            maxWidth: '100%',
            flexDirection: 'column',
        },
        treeContainer: {
            position: 'relative',
            overflow: 'auto',
        },
        popoverBtn: {
            textAlign: 'right',
            paddingRight: '10px',
        },
        cssLabel: {
            fontSize: '1rem',
            marginTop: 0,
            marginBottom: 8,
            display: 'block',
        },
        dropdownChip: {
            marginRight: 4,
            marginBottom: 2,
        },

        // Custom tree node renderer styles
        labelRoot: {
            display: 'flex',
            alignItems: 'center',
            padding: theme.spacing(0.3, 0),
        },
        labelIcon: {
            marginRight: theme.spacing(1),
            minWidth: 14,
            minHeight: 14,
        },
        treeCheckbox: {
            paddingTop: '0px',
            paddingBottom: '0px',
            paddingLeft: '8px',
            paddingRight: '8px',
            marginLeft: '8px',
            marginRight: '8px',
        },
        treeCheckboxLabel: {
            padding: '0px',
            marginLeft: 8,
        },
    });

export interface TreeItemData {
    id?: string;
    name?: string;
    type?: string;
    children?: TreeItemData[];
    compositeType?: CompositeType | string;
    nodeData?: unknown;
    disabled: boolean;
    icon?: React.ElementType<SvgIconProps>;
}

interface DefaultProps {
    classes: Partial<ClassNameMap<string>>;
}

interface TreeDropDownProps extends SelectProps {
    options: TreeItemData[];
    selected?: string[];
    expanded?: string[];
    showCheckboxes?: boolean;

    onExpand?: (nodeIds: string[]) => void;
}

type Props = TreeDropDownProps & DefaultProps;

interface TreeDropDownState {
    menu: HTMLButtonElement | null;
    menuWidth: number;
    selected: string[];
    expanded: string[];

    propOptions?: TreeItemData[];
    propSelected?: string[];
    propExpanded?: string[];
    treeData: TreePanelNode[];
    cache: TreeItemCache;
}

interface PreparedDate {
    selected: string[];
    expanded: string[];
    treeData: StyledTreeItemProps[];
    cache: TreeItemCache;
}
interface CacheItem {
    parent?: StyledTreeItemProps;
    childrenToProcess: TreeItemData[];
}
interface TreeItemCache {
    [key: string]: StyledTreeItemProps;
}

function calculateState(
    data: TreeItemData[],
    selected: unknown,
    expanded?: string[],
    componentInit = false
): PreparedDate {
    const newStateData: PreparedDate = {
        selected: [],
        expanded: [],
        treeData: [],
        cache: {},
    };

    const selectedIds: string[] = selected ? (Array.isArray(selected) ? selected : [selected]) : [];
    const expendedIds: string[] = expanded || [];
    const processCache: CacheItem[] = [
        {
            childrenToProcess: data,
        },
    ];

    while (processCache.length) {
        const processItem = processCache.pop();
        if (processItem) {
            const childrenNodes: StyledTreeItemProps[] = [];
            processItem.childrenToProcess.forEach(child => {
                const nodeId = child.id || '';
                const treeItem: StyledTreeItemProps = {
                    nodeId,
                    labelText: child.name,
                    labelIcon: child.icon,
                    disabled: child.disabled,
                    nodeData: child.nodeData,
                };
                childrenNodes.push(treeItem);
                newStateData.cache[nodeId] = treeItem;

                if (child.children?.length) {
                    processCache.push({
                        parent: treeItem,
                        childrenToProcess: child.children,
                    });
                }

                if (selectedIds.includes(child?.id || '')) {
                    newStateData.selected.push(nodeId);
                }
                if (expendedIds.length) {
                    if (expendedIds.includes(child?.id || '')) {
                        newStateData.expanded.push(nodeId);
                    }
                } else if (selectedIds.filter((s: string) => child.id && s.indexOf(child.id) >= 0).length > 0) {
                    newStateData.expanded.push(nodeId);
                }
            });

            if (processItem.parent) {
                processItem.parent.childrenNodes = childrenNodes;
            } else {
                childrenNodes.forEach(child => newStateData.treeData.push(child));
            }
        }
    }

    // fix default expand
    if (componentInit && !expanded && !newStateData.expanded.length) {
        newStateData.treeData.forEach(root => {
            newStateData.expanded.push(root.nodeId);
            root.childrenNodes?.forEach(child => {
                newStateData.expanded.push(child.nodeId);
            });
        });
    }

    return newStateData;
}

class TreeDropDownComponent extends React.Component<Props, TreeDropDownState> {
    popoverRef: RefObject<HTMLElement> = React.createRef();

    static getDerivedStateFromProps(nextProps: Props, prevState: TreeDropDownState): TreeDropDownState | null {
        let dataChange = false;
        let selectedChange = false;
        let expandedChange = false;
        let { selected, treeData, cache, propOptions } = prevState;
        let expandedIds: string[] | undefined = prevState.expanded;

        if (!_.isEqual(prevState.propOptions, nextProps.options)) {
            dataChange = true;
            propOptions = nextProps.options;
            selected = [];
            expandedIds = undefined;
        }
        if (!_.isEqual(prevState.propSelected, nextProps.selected)) {
            selectedChange = true;
            selected = nextProps.selected || EMPTY_LIST;
        }
        if (!_.isEqual(prevState.propExpanded, nextProps.expanded)) {
            expandedChange = true;
            expandedIds = nextProps.expanded;
        }

        if (dataChange || selectedChange || expandedChange) {
            const newState = calculateState(propOptions || [], selected, expandedIds, !prevState.propOptions?.length);
            selected = newState.selected;
            expandedIds = newState.expanded;
            treeData = newState.treeData;
            cache = newState.cache;

            console.log('!!! TreeDropDownComponent STATE UPDATE !!!', prevState.selected, selected);
            return {
                ...prevState,
                propSelected: nextProps.selected,
                selected,
                propExpanded: nextProps.expanded,
                expanded: expandedIds,
                propOptions: nextProps.options,
                treeData,
                cache,
            };
        }

        return null;
    }

    constructor(props: Props) {
        super(props);

        const newState = calculateState(props.options || [], props.selected, props.expanded, true);
        const { selected, expanded, treeData, cache } = newState;

        this.state = {
            menu: null,
            menuWidth: 0,
            treeData,
            propOptions: props.options,
            selected,
            propSelected: props.selected,
            expanded,
            propExpanded: props.expanded,
            cache,
        };
    }

    private handleToggle(nodeIds: string[]): void {
        this.setState(
            state => {
                return { ...state, expanded: nodeIds };
            },
            () => {
                if (this.props.onExpand) {
                    this.props.onExpand(nodeIds);
                }
            }
        );
    }

    private handleSelect(nodeIds: string[]): void {
        this.setState(
            state => {
                return { ...state, selected: nodeIds };
            },
            () => {
                if (this.props.onChange) {
                    this.props.onChange(
                        { target: { name: this.props.name, value: nodeIds } } as React.ChangeEvent<{
                            name?: string | undefined;
                            value: unknown;
                        }>,
                        this
                    );
                }
            }
        );
    }

    private menuClose(): void {
        this.setState(state => {
            return { ...state, menu: null };
        });
    }

    private menuClick(event: React.MouseEvent<HTMLButtonElement>): void {
        const button: HTMLButtonElement = event.currentTarget;
        this.setState(state => {
            return { ...state, menu: button, menuWidth: button.clientWidth };
        });
    }

    private unselectNode(nodeId?: string): void {
        const selected = this.state.selected.filter(val => val !== nodeId);
        this.handleSelect(selected);
    }

    customItemRenderer(classes: Partial<ClassNameMap<string>>): ItemRenderer {
        const { selected } = this.state;
        const { showCheckboxes } = this.props;

        const handleCheckboxClick = (event: React.MouseEvent<HTMLInputElement>, node: StyledTreeItemProps): void => {
            event.stopPropagation();

            if (!node.disabled) {
                const checked = !selected.includes(node.nodeId);
                const selectedNodes: string[] = checked
                    ? this.props.multiple
                        ? [...selected, node.nodeId]
                        : [node.nodeId]
                    : this.props.multiple
                    ? selected.filter(s => s !== node.nodeId)
                    : [];
                this.handleSelect(selectedNodes);
            }
        };

        return (node: TreePanelNode, itemProps: StyledTreeItemProps): React.ReactNode => {
            const { labelText, labelIcon: LabelIcon, labelInfo } = itemProps;
            const checked = selected.indexOf(node.nodeId) >= 0;

            return (
                <div role="listitem" className={classes.labelRoot}>
                    {LabelIcon && <LabelIcon color="inherit" className={classes.labelIcon} />}
                    {showCheckboxes && (
                        <FormControlLabel
                            control={
                                <input
                                    type="checkbox"
                                    name={node.nodeId}
                                    className={classes.treeCheckbox}
                                    checked={checked}
                                    onChange={() => {}}
                                    onClick={(event: React.MouseEvent<HTMLInputElement>) =>
                                        handleCheckboxClick(event, node)
                                    }
                                    disabled={node.disabled}
                                />
                            }
                            label={labelText}
                            classes={{
                                root: classes.treeCheckboxLabel,
                            }}
                        />
                    )}
                    {!showCheckboxes && (
                        <Typography variant="body2" dangerouslySetInnerHTML={{ __html: labelText || '' }} />
                    )}

                    <Typography variant="caption" color="inherit">
                        {labelInfo}
                    </Typography>
                </div>
            );
        };
    }

    render(): React.ReactNode {
        const { classes, label } = this.props;
        const names = this.state.selected.map(id => this.state.cache[id]);

        return (
            <div className={classes.root}>
                <span className={clsx('MuiFormLabel-root', classes.cssLabel)}>{label}</span>
                <Button className={classes.dropdownBtn} onClick={event => this.menuClick(event)}>
                    <div className="hidden md:flex flex-col items-start">
                        <Typography component="span" className="normal-case font-600 flex">
                            {names.map((node, idx) => (
                                <Chip
                                    className={classes.dropdownChip}
                                    key={this.state.selected[idx]}
                                    size="small"
                                    label={node.labelText}
                                    onDelete={() => this.unselectNode(this.state.selected[idx])}
                                />
                            ))}
                        </Typography>
                    </div>
                    <div className="hidden md:flex flex-col mx-1 items-end">
                        <svg
                            className="MuiSvgIcon-root MuiSelect-icon"
                            focusable="false"
                            viewBox="0 0 24 24"
                            aria-hidden="true"
                        >
                            <path d="M7 10l5 5 5-5z" />
                        </svg>
                    </div>
                </Button>

                <Popover
                    ref={this.popoverRef}
                    open={!!this.state.menu}
                    anchorEl={this.state.menu}
                    onClose={() => this.menuClose()}
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'left',
                    }}
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'left',
                    }}
                    classes={{
                        paper: clsx(classes.statusBarMenu, classes.wrapper, 'py-8'),
                    }}
                >
                    <TreePanel
                        multiple={this.props.multiple}
                        variant="simple"
                        onSelectionChanged={nodeIds => this.handleSelect(nodeIds)}
                        onExpantionChanged={nodeIds => this.handleToggle(nodeIds)}
                        className={clsx(classes.treePanel, classes.treeContainer)}
                        data={this.state.treeData}
                        selected={this.state.selected}
                        expanded={this.state.expanded}
                        onItemRender={this.props.showCheckboxes ? this.customItemRenderer(classes) : undefined}
                        style={{ width: `${this.state.menuWidth}px`, maxWidth: '100%' }}
                    />
                    <div className={classes.popoverBtn}>
                        <Button size="small" variant="contained" onClick={() => this.menuClose()}>
                            Close
                        </Button>
                    </div>
                </Popover>
            </div>
        );
    }
}

const TreeDropDown = React.memo(withStyles(styles)(TreeDropDownComponent), (prevProps, nextProps) => {
    return (
        _.isEqual(prevProps.options, nextProps.options) &&
        _.isEqual(prevProps.selected, nextProps.selected) &&
        _.isEqual(prevProps.expanded, nextProps.expanded)
    );
});
export default TreeDropDown;
