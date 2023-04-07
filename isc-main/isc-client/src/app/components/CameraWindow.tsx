import React, { useEffect, useRef, useState } from 'react';
import IscDefaultTheme from '@common/components/layout/window/IscDefaultTheme';
import { IscWindow } from '@common/components/layout/window';
import { useSelector } from 'react-redux';
import * as Selectors from 'app/store/selectors';
import { RootState } from 'app/store/appState';
import { IEntity } from 'app/domain/entity';
import { Rect } from '@common/components/layout/window/IscWindow';
import CameraWidget from './CameraWidget';
import { FloatingWindowProps } from './map/FloatingWindow';

type CameraWindowProps = FloatingWindowProps;

const CAMERA_WINDOW_WIDTH = 200;
const CAMERA_WINDOW_HEIGHT = 150;

const CameraWindow = (props: CameraWindowProps): JSX.Element => {
    const [updateFlag, setUpdateFlag] = useState<number>(props.updateFlag);
    const windowRef = useRef<IscWindow>(null);
    const cameraDevice = useSelector<RootState, IEntity | undefined>(
        Selectors.createCameraEntitySelector(props.entityId)
    );
    const compositeCamera = useSelector<RootState, IEntity | undefined>(
        Selectors.createRegionEntitySelector(props.entityId)
    );
    const camera = cameraDevice || compositeCamera;

    const cameraWindowPaneStyle = {
        width: '80%',
        height: '50%',
        top: '25%',
        left: '10%',
        backgroundColor: 'rgba(0, 0, 0, 0.2)',
    };
    const cameraWindowStyle = IscDefaultTheme({
        title: '', // camera?.name || '',
        onClose: () => {
            windowRef.current?.minimize();
            if (props.onClose) {
                props.onClose();
            }
        },
        onMinimize: () => windowRef.current?.minimize(),
        onMaximize: () => windowRef.current?.maximize(),
    });

    const onMove = (currentFrame: Rect): void => {
        if (props.onMove) {
            props.onMove(currentFrame);
        }
    };
    const onResize = (currentFrame: Rect): void => {
        if (props.onResize) {
            props.onResize(currentFrame);
        }
    };

    useEffect(() => {
        if (props.updateFlag !== updateFlag) {
            setUpdateFlag(props.updateFlag);
            const windowState = windowRef.current?.frameRect;
            if (windowState) {
                setTimeout(() => onMove(windowState), 10);
            }
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [props.updateFlag, updateFlag]);

    return (
        <IscWindow
            ref={windowRef}
            {...cameraWindowStyle}
            cursorRemap={c => (c === 'move' ? 'default' : undefined)}
            style={cameraWindowPaneStyle}
            boundary={{ top: 0 }}
            initialWidth={props.initialWidth || CAMERA_WINDOW_WIDTH}
            initialHeight={props.initialHeight || CAMERA_WINDOW_HEIGHT}
            initialTop={props.initialTop}
            initialLeft={props.initialLeft}
            onMove={onMove}
            onResize={onResize}
        >
            <CameraWidget floating camera={camera} streamId={props.data as string} onCloseWindow={props.onClose} />
        </IscWindow>
    );
};

export default CameraWindow;
