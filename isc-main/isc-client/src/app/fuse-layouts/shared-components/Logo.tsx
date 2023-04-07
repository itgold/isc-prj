import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import _ from '@lodash';
import * as Actions from 'app/store/actions';
import { RestService } from '@common/services/rest.service';
import { FuseSettingsState, RootState } from 'app/store/appState';
import { publicPath } from 'app/utils/ui.constants';

const useStyles = makeStyles(theme => ({
    root: {
        '& .logo-icon': {
            width: 24,
            height: 24,
            transition: theme.transitions.create(['width', 'height'], {
                duration: theme.transitions.duration.shortest,
                easing: theme.transitions.easing.easeInOut,
            }),
        },
        '& .react-badge, & .logo-text': {
            transition: theme.transitions.create('opacity', {
                duration: theme.transitions.duration.shortest,
                easing: theme.transitions.easing.easeInOut,
            }),
        },
    },
    reactBadge: {
        backgroundColor: '#121212',
        color: '#61DAFB',
    },
}));

export default function Logo(): JSX.Element {
    const classes = useStyles();
    const dispatch = useDispatch();
    const settings = useSelector<RootState, FuseSettingsState>(({ fuse }) => fuse.settings.current);

    return (
        <div className={clsx(classes.root, 'flex items-center')}>
            <IconButton
                onClick={() => {
                    dispatch(
                        Actions.setDefaultSettings(
                            _.set({}, 'layout.config.navbar.folded', !settings.layout.config.navbar.folded)
                        )
                    );
                }}
                color="inherit"
            >
                <img className="logo-icon" src={`${publicPath}/assets/images/logos/logo192.png`} alt="logo" />
            </IconButton>

            <Typography className="text-16 mx-12 font-medium logo-text" color="inherit">
                720 Security
            </Typography>
        </div>
    );
}
