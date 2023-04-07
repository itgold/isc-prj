import React, { cloneElement, ReactPortal, useContext, useState, useEffect } from 'react';
import { IconButton, makeStyles, Theme, Tooltip, withStyles } from '@material-ui/core';
import { createStyles } from '@material-ui/core/styles';

import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import { ViewStyle } from '@common/components/layout/splitpanel';
import { isNumber } from 'lodash';
import { FaCaretSquareLeft, FaCaretSquareRight, FaCaretSquareDown, FaCaretSquareUp } from 'react-icons/fa';
import { WithStylesOptions } from '@material-ui/styles';
import { SplitViewContext } from '../splitpanel/SplitViewContext';

export const DEFAULT_TAB_HEIGHT = 36;

interface TabPanelViewProps {
    id?: string;
    children?: React.ReactNode | React.ReactNode[];
    align?: ViewStyle;
    horizontalAlign?: 'left' | 'right';
    tabWidth?: number;
    selectedIndex?: number;
    minimized?: boolean;

    onToggle?: (minimized: boolean) => void;
    onSelect?: (activaIndex: number) => void;
}

interface TabPanelProps {
    children?: React.ReactNode | React.ReactNode[];
    name: string;
    disabled?: boolean;
}

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        wrapperVertical: {
            backgroundColor: '#eeeeee',
            minWidth: `${DEFAULT_TAB_HEIGHT}px`,
        },
        wrapperHorizontal: {
            backgroundColor: '#eeeeee',
            minHeight: `${DEFAULT_TAB_HEIGHT}px`,
        },

        verticalTab: {
            position: 'relative',
            height: `${DEFAULT_TAB_HEIGHT}px`,
            transform: `rotate(90deg) translate(${DEFAULT_TAB_HEIGHT}px, 0px)`,
            transformOrigin: 'left top 0',
            left: `${DEFAULT_TAB_HEIGHT}px`,
            minHeight: `${DEFAULT_TAB_HEIGHT}px`,
            fontWeight: theme.typography.fontWeightRegular as 'normal',
            fontSize: theme.typography.pxToRem(14),
            padding: '0px',
        },
        horizontalTab: {
            minHeight: `${DEFAULT_TAB_HEIGHT}px`,
            fontWeight: theme.typography.fontWeightRegular as 'normal',
            fontSize: theme.typography.pxToRem(14),
            padding: '0px',
        },

        menuButton: {
            marginLeft: '0px',
            marginRight: '0px',
            marginTop: '4px',
            marginBottom: '0px',
        },

        verticalLeftMinIcon: {
            position: 'absolute',
            right: '10px',
            zIndex: 100,
        },
        verticalRightMinIcon: {
            position: 'absolute',
            left: '10px',
            zIndex: 100,
        },
        horizontalMinIcon: {
            position: 'absolute',
            right: '10px',
            zIndex: 100,
        },
    })
);

const VerticalTabs = withStyles(() => ({
    flexContainer: {
        flexDirection: 'column',
        position: 'relative',
        width: `${DEFAULT_TAB_HEIGHT}px`,
    },
    indicator: {
        display: 'none',
    },
}))(Tabs);
const HorizontalTabs = withStyles(() => ({
    flexContainer: {
        flexDirection: 'row',
        position: 'relative',
        height: `${DEFAULT_TAB_HEIGHT}px`,
    },
    indicator: {
        display: 'none',
    },
}))(Tabs);

interface TabContainerProps {
    children: React.ReactNode | React.ReactNode[];
}

function TabContainer(props: TabContainerProps): JSX.Element {
    return (
        <Typography
            component="div"
            style={{ padding: 0, flex: 'auto', overflow: 'hidden', display: 'flex', alignItems: 'stretch' }}
        >
            {props.children}
        </Typography>
    );
}

export function TabPanel(props: TabPanelProps): JSX.Element | null {
    const propsTrace = JSON.stringify(props);
    throw new Error(
        `<TabPanel> component is required to be used incide of <TabPanelView> component only! Params: ${propsTrace}`
    );
}

function removeNullChildren(children: React.ReactNode | React.ReactNode[]): React.ReactNode[] {
    return React.Children.toArray(children).filter(c => c && (c as ReactPortal).type === TabPanel);
}

const VerticalTabPanelImpl = withStyles<'wrapper'>((theme: Theme) => ({
    selected: {
        color: theme.palette.secondary.contrastText,
        borderBottomWidth: '2px',
        borderBottomStyle: 'solid',
        borderBottomColor: theme.palette.secondary.main,
        backgroundColor: 'darkgray',
    },
    wrapper: {
        borderLeft: '1px solid darkgray',
    },
}))(Tab);
const HorizontalTabPanelImpl = withStyles<'wrapper'>((theme: Theme) => ({
    selected: {
        color: theme.palette.secondary.contrastText,
        borderBottomWidth: '2px',
        borderBottomStyle: 'solid',
        borderBottomColor: theme.palette.secondary.main,
        backgroundColor: 'darkgray',
    },
    wrapper: {
        borderRight: '1px solid darkgray',
    },
}))(Tab);

export default function TabPanelView(props: TabPanelViewProps): JSX.Element {
    const notNullChildren = removeNullChildren(props.children);
    const { minimized, updateMinimized } = useContext(SplitViewContext);
    const [state, setState] = useState<{ activeIndex: number | boolean; oldIndex: number }>({
        activeIndex: props.selectedIndex || 0,
        oldIndex: props.selectedIndex || 0,
    });
    const [indexProp, setIndexProp] = useState<number | false>(props.selectedIndex || 0);
    const [minimizedProp, setMinimizedProp] = useState<boolean>(minimized || false);

    const handleChange = (e: unknown, activeIndex: number): void => {
        if (minimized) {
            updateMinimized(false);
        }

        if (state.activeIndex !== activeIndex) {
            if (props.onSelect) {
                props.onSelect(activeIndex);
            }
        }

        setState({ ...state, activeIndex, oldIndex: activeIndex });

        if (minimized && props.onToggle) {
            setTimeout(() => props?.onToggle && props.onToggle(false), 10);
        }
    };

    const classes = useStyles();
    const { activeIndex } = state;
    const align = props.align || ViewStyle.vertical;
    const horizontalAlign = props.horizontalAlign
        ? props.horizontalAlign
        : align === ViewStyle.vertical
        ? 'left'
        : 'right';
    const tabWidth = props.tabWidth || 100;
    const topOffset = tabWidth - DEFAULT_TAB_HEIGHT;

    useEffect(() => {
        if (minimized && state.activeIndex !== false) {
            setState({
                oldIndex: isNumber(state.activeIndex) ? state.activeIndex : 0,
                activeIndex: false,
            });
        } else if (!minimized && state.activeIndex === false) {
            setState({
                oldIndex: state.oldIndex || 0,
                activeIndex: state.oldIndex || 0,
            });
        }
    }, [minimized, state.activeIndex, state.oldIndex]);
    useEffect(() => {
        if (props.selectedIndex !== indexProp) {
            const newIndex = props.selectedIndex || 0;
            setIndexProp(newIndex);
            setState({
                oldIndex: newIndex,
                activeIndex: newIndex,
            });
        }
    }, [props.selectedIndex, indexProp]);

    let activeTabContainer = <TabContainer>&nbsp;</TabContainer>;
    const elements = notNullChildren.reduce((acc: React.ReactNode[], child: React.ReactNode, idx: number) => {
        let pane;
        const element = child as ReactPortal;
        if (element) {
            const isPane = element.type === VerticalTabPanelImpl || element.type === HorizontalTabPanelImpl;
            const paneProps = {
                index: idx,
                className: align === ViewStyle.vertical ? classes.verticalTab : classes.horizontalTab,
                label: element.props.name,
                style: align === ViewStyle.vertical ? { top: `${idx * topOffset}px`, minWidth: `${tabWidth}px` } : {},
                'data-type': 'TabPanel',
                key: `TabPanel-${idx}`,
                disabled: element.props.disabled,
            };

            if (isPane) {
                pane = cloneElement(element, paneProps);
            } else {
                pane =
                    align === ViewStyle.vertical ? (
                        <VerticalTabPanelImpl {...paneProps} />
                    ) : (
                        <HorizontalTabPanelImpl {...paneProps} />
                    );
            }

            if (activeIndex === idx) {
                activeTabContainer = <TabContainer>{element.props.children}</TabContainer>;
            }

            return [...acc, pane];
        }

        return acc;
    }, []);

    const handleDrawerToggle = (): void => {
        const newState = !minimized;
        updateMinimized(newState);
        setState({
            activeIndex: state.oldIndex,
            oldIndex: state.oldIndex,
        });

        if (props.onToggle) {
            props.onToggle(newState);
        }
    };

    const minIconStyle = horizontalAlign === 'left' ? classes.verticalLeftMinIcon : classes.verticalRightMinIcon;
    const minifyIcon = horizontalAlign === 'left' ? <FaCaretSquareRight /> : <FaCaretSquareLeft />;
    const maximizeIcon = horizontalAlign === 'left' ? <FaCaretSquareLeft /> : <FaCaretSquareRight />;
    return align === ViewStyle.vertical ? (
        <div
            style={{
                display: 'flex',
                width: '100%',
            }}
        >
            {horizontalAlign === 'left' && activeTabContainer}

            <div className={minIconStyle}>
                {minimized && (
                    <Tooltip title="Maximize" aria-label="maximize">
                        <IconButton
                            size="small"
                            color="inherit"
                            aria-label="open drawer"
                            onClick={handleDrawerToggle}
                            edge="end"
                            className={classes.menuButton}
                        >
                            {minifyIcon}
                        </IconButton>
                    </Tooltip>
                )}
                {!minimized && (
                    <Tooltip title="Minimize" aria-label="minimize">
                        <IconButton
                            size="small"
                            color="inherit"
                            aria-label="open drawer"
                            onClick={handleDrawerToggle}
                            edge="end"
                            className={classes.menuButton}
                        >
                            {maximizeIcon}
                        </IconButton>
                    </Tooltip>
                )}
            </div>

            <VerticalTabs className={classes.wrapperVertical} value={activeIndex} onChange={handleChange}>
                {elements}
            </VerticalTabs>

            {horizontalAlign === 'right' && activeTabContainer}
        </div>
    ) : (
        <div
            style={{
                display: 'flex',
                flexDirection: 'column',
                width: '100%',
            }}
        >
            <div style={{ width: '100%', display: 'block' }}>
                <div className={classes.horizontalMinIcon}>
                    {minimized && (
                        <Tooltip title="Maximize" aria-label="maximize">
                            <IconButton
                                size="small"
                                color="inherit"
                                aria-label="open drawer"
                                onClick={handleDrawerToggle}
                                edge="end"
                                className={classes.menuButton}
                            >
                                <FaCaretSquareUp />
                            </IconButton>
                        </Tooltip>
                    )}
                    {!minimized && (
                        <Tooltip title="Minimize" aria-label="minimize">
                            <IconButton
                                size="small"
                                color="inherit"
                                aria-label="open drawer"
                                onClick={handleDrawerToggle}
                                edge="end"
                                className={classes.menuButton}
                            >
                                <FaCaretSquareDown />
                            </IconButton>
                        </Tooltip>
                    )}
                </div>

                <HorizontalTabs className={classes.wrapperHorizontal} value={activeIndex} onChange={handleChange}>
                    {elements}
                </HorizontalTabs>
            </div>
            {activeTabContainer}
        </div>
    );
}
