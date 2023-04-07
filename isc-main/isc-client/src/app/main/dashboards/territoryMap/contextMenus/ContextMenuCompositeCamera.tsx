import React from 'react';
import { Grid, makeStyles, Theme } from '@material-ui/core';
import { IContextMenuProps } from 'app/main/mapsEditor/contextMenus/ContextMenu';
import CameraWidget from 'app/components/CameraWidget';
import { UI_RESOLUTION_BREAKPOUNT } from 'app/utils/ui.constants';
import { useSelector } from 'react-redux';
import * as Selectors from 'app/store/selectors';
import Camera from 'app/domain/camera';
import { CompositeNode } from 'app/domain/composite';
import { EntityType } from 'app/utils/domain.constants';

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
export default function ContextMenuCompositeCamera(props: IContextMenuProps): JSX.Element {
    const compositeCamRegion = useSelector(
        Selectors.createRegionEntitySelector(props.selectedFeature?.getProperties()?.data?.id || '')
    ) as CompositeNode;

    const classes = useStyles();
    return (
        <Grid className={classes.cameraWidgetContainer}>
            <CameraWidget camera={compositeCamRegion} />
        </Grid>
    );
}
