import React, { useState } from 'react';
import clsx from 'clsx';
import { makeStyles, Paper, IconButton, Menu, MenuItem, Typography } from '@material-ui/core';
import { MoreVert as MoreIcon } from '@material-ui/icons';

import { AppTheme } from '../theme';
import { IMenuItem, OnItemSelected } from '../menu';

const useStyles = makeStyles((theme: AppTheme) => ({
    widgetWrapper: {
        display: 'flex',
        minHeight: '100%',
    },
    widgetHeader: {
        padding: theme.spacing(2),
        paddingBottom: theme.spacing(1),
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    widgetRoot: {
        boxShadow: theme.customShadows?.widget,
    },
    widgetBody: {
        paddingBottom: theme.spacing(2),
        paddingRight: theme.spacing(2),
        paddingLeft: theme.spacing(2),
    },
    noPadding: {
        padding: 0,
    },
    paper: {
        display: 'flex',
        flexDirection: 'column',
        flexGrow: 1,
        overflow: 'hidden',
    },
    moreButton: {
        margin: -theme.spacing(1),
        padding: 0,
        width: 40,
        height: 40,
        color: theme.palette.text.hint,
        '&:hover': {
            backgroundColor: theme.palette.primary.main,
            color: 'rgba(255, 255, 255, 0.35)',
        },
    },
}));

interface IWidgetProps {
    children: React.ReactNode;
    menu?: IMenuItem[];
    header?: React.ReactNode;
    title?: string | React.ReactNode;

    noBodyPadding?: boolean;
    bodyClass?: string;
    headerClass?: string;
    bodyId?: string;
}

export default function Widget(props: IWidgetProps): JSX.Element {
    const classes = useStyles();

    // local
    const [moreButtonRef, setMoreButtonRef] = useState(null);
    const [isMoreMenuOpen, setMoreMenuOpen] = useState(false);

    const handleItemSelect = (event: React.MouseEvent<EventTarget>, callback: OnItemSelected): void => {
        setMoreMenuOpen(false);
        callback();
    };

    return (
        <div className={classes.widgetWrapper}>
            <Paper className={classes.paper} classes={{ root: classes.widgetRoot }}>
                <div className={clsx(classes.widgetHeader, props.headerClass)}>
                    <>
                        <Typography variant="h5" color="textSecondary">
                            {props.header ? props.header : props.title}
                        </Typography>
                        {props.menu && (
                            <IconButton
                                color="primary"
                                classes={{ root: classes.moreButton }}
                                aria-owns="widget-menu"
                                aria-haspopup="true"
                                onClick={() => setMoreMenuOpen(true)}
                                buttonRef={setMoreButtonRef}
                            >
                                <MoreIcon />
                            </IconButton>
                        )}
                    </>
                </div>
                <div
                    className={clsx(classes.widgetBody, {
                        [classes.noPadding]: props.noBodyPadding,
                        [props.bodyClass || 'defaultStyle']: props.bodyClass,
                    })}
                    id={props.bodyId}
                >
                    {props.children}
                </div>
            </Paper>

            {props.menu && (
                <Menu
                    open={isMoreMenuOpen}
                    anchorEl={moreButtonRef}
                    onClose={() => setMoreMenuOpen(false)}
                    disableAutoFocusItem
                >
                    {props.menu.map(menuItem => (
                        <MenuItem key={menuItem.label} onClick={event => handleItemSelect(event, menuItem.onSelected)}>
                            <Typography>{menuItem.label}</Typography>
                        </MenuItem>
                    ))}
                </Menu>
            )}
        </div>
    );
}
