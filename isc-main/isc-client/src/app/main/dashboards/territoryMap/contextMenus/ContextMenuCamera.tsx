import React from 'react';
import { Grid, makeStyles, Theme } from '@material-ui/core';
import { IContextMenuProps } from 'app/main/mapsEditor/contextMenus/ContextMenu';
import CameraWidget from 'app/components/CameraWidget';
import { UI_RESOLUTION_BREAKPOUNT } from 'app/utils/ui.constants';
import { useSelector } from 'react-redux';
import * as Selectors from 'app/store/selectors';
import { RootState } from 'app/store/appState';
import { IEntity } from 'app/domain/entity';
import Camera from 'app/domain/camera';

const useStyles = makeStyles((theme: Theme) => ({
    cameraWidgetContainer: {
        width: '100%',
        height: '150px',

        [theme.breakpoints.up(UI_RESOLUTION_BREAKPOUNT)]: {
            width: '400px',
            height: '260px',
        },
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
        lineHeight: '20px',

        display: 'flex',
        alignItems: 'end',
        justifyContent: 'end',
        marginBottom: '-8px',
    },
}));

/**
 * Display the context menu for cameras
 */
export default function ContextMenuCamera(props: IContextMenuProps): JSX.Element {
    const { selectedFeature } = props;
    const classes = useStyles();
    const cameraId = selectedFeature?.getProperties()?.data?.id || '';
    const camera = useSelector<RootState, IEntity | undefined>(
        Selectors.createCameraEntitySelector(cameraId)
    ) as Camera;

    return (
        <Grid className={classes.cameraWidgetContainer}>
            <CameraWidget camera={camera} />
        </Grid>
    );
}
