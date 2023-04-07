import React, { useEffect } from 'react';
import clsx from 'clsx';
import { makeStyles, Theme, createStyles } from '@material-ui/core/styles';
import { ClickAwayListener } from '@material-ui/core';
import { ClassNameMap } from '@material-ui/styles';
import { useSlidePanelDispatch, Actions, useSlidePanelState, TabDetails } from './context.provider';
import { AppTheme } from '../../theme';

const useStyles = makeStyles((theme: Theme) => {
    const appTheme = theme as AppTheme;
    return createStyles({
        fakeToolbar: {
            height: appTheme.appBar.height,
        },
        slidePanel: {
            position: 'fixed',
            top: '0px',
            right: '-300px',
            width: '300px',
            bottom: '0px',
            backgroundColor: '#f1f1f1',
            borderLeft: '1px solid #d2d2d2',

            display: 'flex',
            flexDirection: 'column',
            alignItems: 'stretch',
            paddingTop: appTheme.appBar.height,
        },
        slidePanelOpen: {
            // transitionProperty: 'all',
            transitionProperty: 'right',
            transitionDuration: '.5s',
            transitionTimingFunction: 'cubic-bezier(0, 1, 0.5, 1)',
            right: '0px',
        },

        slidePanelTabs: {
            position: 'absolute',
            top: '0px',
            height: '100%',
            left: '-46px',
        },
        slidePanelTab: {
            boxShadow: '-4px 4px 15px rgba(0, 0, 0, .5)',
            borderRadius: '15px 0px 0px 15px',
            background: appTheme.palette.primary.dark,
            color: appTheme.palette.primary.contrastText,
            cursor: 'pointer',
            width: '32px',
            height: '100%',
            position: 'relative',
            left: '14px',

            '&.active, &.active:hover, &.active:focus, &:focus': {
                outline: 'none',
            },
        },
        slidePanelTabFocused: {
            outline: 'none',
        },
        slidePanelTabSelected: {
            background: appTheme.palette.primary.light,
            width: '38px',
            left: '8px',
        },
        slidePanelTabSpace: {
            height: '10px',
        },
        slidePanelTabContent: {
            textAlign: 'center',
            whiteSpace: 'nowrap',
            transformOrigin: '150% 200%',
            transform: 'rotate(270deg)',
            userSelect: 'none',

            '&::before': {
                content: '',
                paddingTop: '90%',
                display: 'inlineBlock',
                verticalAlign: 'middle',
            },
        },
        slidePanelTabContentSelected: {
            transformOrigin: '130% 200%',
        },
        tabContent: {
            display: 'none',
            overflowX: 'hidden',
            overflowY: 'scroll',
            height: '100vh',
        },
        tabContentVisible: {
            display: 'block',
        },
    });
});

declare module 'react' {
    interface HTMLAttributes<T> extends AriaAttributes, DOMAttributes<T> {
        classes?: Partial<ClassNameMap<string>>;
    }
}

function SlideSidePanelHeaderTab(props: TabDetails): JSX.Element {
    const classes = useStyles();
    const state = useSlidePanelState();
    const dispatch = useSlidePanelDispatch();

    const OnClick = (): void => {
        if (state.opened) {
            if (props.index === state.activeTab) {
                dispatch(Actions.openPanel({ index: -1 }));
            } else {
                dispatch(Actions.selectTab({ index: props.index }));
            }
        } else {
            dispatch(Actions.openPanel({ index: props.index }));
        }
    };

    const handleKeyDown = (ev: React.KeyboardEvent<HTMLElement>): void => {
        if (ev.keyCode === 13) {
            OnClick();
        }
    };

    return (
        <>
            <tr key={`slide_tab_${props.index}`}>
                <td>
                    <div
                        className={clsx(
                            classes.slidePanelTab,
                            props.index === state.activeTab && classes.slidePanelTabSelected
                        )}
                        classes={{ focused: classes.slidePanelTabFocused }}
                        onClick={OnClick}
                        onKeyDown={handleKeyDown}
                        role="tab"
                        tabIndex={-1}
                    >
                        <div
                            className={clsx(
                                classes.slidePanelTabContent,
                                props.index === state.activeTab && classes.slidePanelTabContentSelected
                            )}
                        >
                            {props.title}
                        </div>
                    </div>
                </td>
            </tr>
            <tr key={`slide_tab_sep${props.index}`}>
                <td className={classes.slidePanelTabSpace} />
            </tr>
        </>
    );
}

function SlideSidePanelHeader(): JSX.Element {
    const classes = useStyles();
    const state = useSlidePanelState();

    return (
        <table cellPadding={0} cellSpacing={0} className={classes.slidePanelTabs}>
            <tbody key="slide_container">
                <tr key="slide_tab_-1">
                    <td className={classes.fakeToolbar} />
                </tr>
                <tr key="slide_tab_sep_-1">
                    <td className={classes.slidePanelTabSpace} />
                </tr>
                {state.tabs.map((tab, index) => (
                    <SlideSidePanelHeaderTab key={`slide_tab_${index}`} {...tab} />
                ))}
                <tr key="slide_footer">
                    <td>&nbsp;</td>
                </tr>
            </tbody>
        </table>
    );
}

// ==================================================================

interface ISlideSidePanelProps {
    children: React.ReactNode;
}
export function SlideSidePanel(props: ISlideSidePanelProps): JSX.Element {
    const classes = useStyles();
    const state = useSlidePanelState();
    const dispatch = useSlidePanelDispatch();

    const handleClickAway = (): void => {
        dispatch(Actions.openPanel({ index: -1 }));
    };

    return (
        <ClickAwayListener onClickAway={handleClickAway}>
            <div className={clsx(classes.slidePanel, state.opened && classes.slidePanelOpen)}>
                <SlideSidePanelHeader />
                {props.children}
            </div>
        </ClickAwayListener>
    );
}

interface ISlideSidePanelTabProps {
    index: number;
    title: string;
    tabStyles?: any[];
    children: React.ReactNode;
}

export function SlideSidePanelTab(props: ISlideSidePanelTabProps): JSX.Element {
    const classes = useStyles();
    const dispatch = useSlidePanelDispatch();
    const state = useSlidePanelState();

    useEffect(() => {
        dispatch(Actions.addTab({ tabInfo: { index: props.index, title: props.title } }));
    });

    return (
        <div
            className={clsx(
                classes.tabContent,
                state.activeTab === props.index && classes.tabContentVisible,
                props.tabStyles && props.tabStyles.length && props.tabStyles[0]
            )}
        >
            {props.children}
        </div>
    );
}
