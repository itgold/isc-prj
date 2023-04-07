import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import FullSizeLayout from '@common/components/layout/fullsize.layout';
import { Grid } from '@material-ui/core';
import themesConfig from 'app/fuse-configs/themesConfig';
import clsx from 'clsx';
import IscToolbar from './IscToolbar';
import IscPageBreadcrumbs from './IscPageBreadcrumbs';
import IscPageSimpleHeader from './IscPageSimpleHeader';

const useStyles = makeStyles(theme => ({
    rootControls: {
        padding: theme.spacing(2),
        position: 'sticky',
        top: 0,
        zIndex: 100,
        backgroundColor: themesConfig.default.palette.background.default,
    },
    root: {
        flexGrow: 1,
        marginLeft: theme.spacing(2),
        marginRight: theme.spacing(2),
    },
    paper: {
        padding: theme.spacing(1),
        color: theme.palette.text.secondary,
        height: '100%',
    },
    childrenContent: {
        height: '100% !important',
    },
    container: {
        height: '100%',
    },
}));

interface IscPageSimpleProps {
    children?: React.ReactNode;
    header?: React.ReactNode;
    toolbar?: React.ReactNode;
    currentPageLabel?: string;
    currentPageIcon?: React.ReactNode;
    contentClass?: string;
}

function IscPageSimple(props: IscPageSimpleProps): JSX.Element {
    const classes = useStyles(props);
    return (
        <FullSizeLayout>
            <div className={classes.rootControls}>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <IscPageBreadcrumbs
                            currentPageLabel={props.currentPageLabel}
                            currentPageIcon={props.currentPageIcon}
                        />
                    </Grid>
                    {props.header && (
                        <Grid item xs={6}>
                            <IscPageSimpleHeader header={props.header} classes={classes} />)
                        </Grid>
                    )}
                    {props.toolbar && (
                        <Grid item xs={12}>
                            <IscToolbar>{props.toolbar}</IscToolbar>
                        </Grid>
                    )}
                </Grid>
            </div>
            <div className={clsx(classes.root, props.contentClass)}>
                {props.children}
                {/*
                <Grid container spacing={3} className={classes.container}>
                    <Grid item xs={12} className={classes.childrenContent}>
                        {props.children}
                    </Grid>
                </Grid>
                */}
            </div>
        </FullSizeLayout>
    );
}

export default React.memo(IscPageSimple);
