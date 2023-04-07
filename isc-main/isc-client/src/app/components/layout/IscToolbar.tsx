import React from 'react';
import { Grid, Paper } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';

interface IscToolbarProps {
    children?: React.ReactNode;
}

const IscToolbar = (props: IscToolbarProps) => {
    const useStyles = makeStyles(theme => ({
        root: {
            flexGrow: 1,
            marginBottom: 1,
        },
        paper: {
            padding: theme.spacing(1),
            textAlign: 'center',
            color: theme.palette.text.primary,
            background: `linear-gradient(to right, ${theme.palette.primary.dark} 0%, ${theme.palette.primary.main} 100%)`,
            position: 'relative',
        },
        toolbarButtons: {
            verticalAlign: 'middle',
            display: 'inline-flex',
        },
        cameraStatus: {},
        cameraStatusWarn: {
            color: 'red',
        },
    }));

    const classes = useStyles();

    return (
        <Grid container className={classes.root}>
            <Grid item xs={12}>
                <Paper className={classes.paper}>
                    <Grid container spacing={3}>
                        {props.children}
                    </Grid>
                </Paper>
            </Grid>
        </Grid>
    );
};
export default IscToolbar;
