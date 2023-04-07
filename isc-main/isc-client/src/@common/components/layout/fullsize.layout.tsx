import React from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core';
import { AppTheme } from '../theme';

const useStyles = makeStyles((theme: AppTheme) => ({
    root: {
        display: 'flex',
        flexDirection: 'column',
        minHeight: '100%',
        position: 'relative',
        flex: '1 0 auto',
        height: 'auto',
        width: '100%',
        backgroundColor: theme.palette.background.default,
    },
}));

interface IFullSizeLayoutProps {
    children: React.ReactNode;
}

export default function FullSizeLayout(props: IFullSizeLayoutProps): JSX.Element {
    const classes = useStyles();
    return <div className={clsx(classes.root)}>{props.children}</div>;
}
