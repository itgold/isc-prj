import React, { ChangeEvent, createRef, ReactNode, RefObject } from 'react';
import { bindActionCreators, Dispatch, Action } from 'redux';
import clsx from 'clsx';
import InfoIcon from '@material-ui/icons/Info';
import CachedIcon from '@material-ui/icons/Cached';
import { BsCameraVideoOff } from 'react-icons/bs';
import {
    Paper,
    GridListTileBar,
    IconButton,
    Theme,
    createStyles,
    withStyles,
    StyleRules,
    Tooltip,
} from '@material-ui/core';
import Camera, { CameraState, cameraState, cameraStateType, CameraStateType } from 'app/domain/camera';
import { RouteComponentProps, withRouter } from 'react-router-dom';
import { publicPath, UI_RESOLUTION_BREAKPOUNT } from 'app/utils/ui.constants';
import { probateSecurity } from 'app/utils/auth.utils';
import { ClassNameMap } from '@material-ui/styles';
import { copyToClipboard } from '@common/utils/dom.utils';
import * as Actions from 'app/store/actions';
import { connect } from 'react-redux';
import { EntityType, EMPTY_LIST } from 'app/utils/domain.constants';
import { getFrameRect } from '@common/components/layout/window/IscWindow';
import { CgListTree } from 'react-icons/cg';
import { CompositeNode } from 'app/domain/composite';
import { DeviceState, RootState } from 'app/store/appState';
import _ from 'lodash';
import ISchoolElement from 'app/domain/school.element';
import FindOnTree from './map/deviceActions/FindOnTree';
import { resolveCompositeDeviceState } from './map/utils/MapUtils';

const styles = (theme: Theme): StyleRules =>
    createStyles({
        camPanel: {
            padding: theme.spacing(1),
            textAlign: 'center',
            color: theme.palette.text.secondary,
            position: 'relative',
            minWidth: '250px',
            width: '100%',
            height: '100%',
            zIndex: 1,
            overflow: 'hidden',
        },
        camView: {
            width: '100%',
            height: 'auto',
            minHeight: '100px',
            objectFit: 'contain',
        },
        cameraStatusTitle: {
            fontSize: '0.8em',
            lineHeight: '16px',
            [theme.breakpoints.up(UI_RESOLUTION_BREAKPOUNT)]: {
                fontSize: '1.2em',
                lineHeight: '24px',
            },
        },
        cameraStatusSubTitle: {
            fontSize: '0.8em',
            [theme.breakpoints.up(UI_RESOLUTION_BREAKPOUNT)]: {
                fontSize: '1.2em',
            },
        },
        cameraStatus: {
            color: 'lightgray',
            padding: '6px',
        },
        noWrap: {
            whiteSpace: 'nowrap',
        },
        cameraRefresh: {
            paddingRight: '0px',
        },
        cameraStatusWarn: {
            color: 'red',
        },
        cameraStatusOk: {
            color: '#10d610',
        },
        hiddenImage: {
            display: 'none',
        },
        transparentDiv: {
            backgroundColor: 'rgba(0, 0, 0, 0.4)',
            position: 'absolute',
            left: '0px',
            right: '0px',
            top: '0px',
            bottom: '0px',
        },
        titleBar: {
            height: '36px',
            [theme.breakpoints.up(UI_RESOLUTION_BREAKPOUNT)]: {
                height: '68px',
            },
        },
        camSelector: {
            width: '100%',
            height: 'auto',

            userSelect: 'none',
            border: '1px solid #AAA',
            borderRadius: '2px',
            boxShadow: '0px 1px 3px rgba(0, 0, 0, 0.1)',
            color: '#555',
            fontSize: 'inherit',
            margin: 0,
            overflow: 'hidden',
            textOverflow: 'ellipsis',
            whiteSpace: 'nowrap',
        },
    });

interface InheritedProps extends RouteComponentProps {
    classes: Partial<ClassNameMap<string>>;
}
interface CameraWidgetProps {
    camera?: ISchoolElement;
    streamId?: string;
    floating?: boolean;
    onCloseWindow?: () => void;
}
interface DispatchProps {
    openFloatingWindow(payload?: any): void;
}
export interface ReduxStateProps {
    devices: ISchoolElement[];
    deviceState?: DeviceState;
}

type Props = InheritedProps & DispatchProps & ReduxStateProps & CameraWidgetProps;

const STATE_CONNECTED = 'CONNECTED';
const STATE_CONNECTION_LOST = 'NO CONNECTION';

const LOAD_STATE_FAILED = 2;
const LOAD_STATE_RELOAD = -1;
const LOAD_STATE_LOADED = 1;
const LOAD_STATE_STOPPED = 0;

interface CameraWidgetState {
    loaded: number;
    cameras: Camera[];
    cameraState?: DeviceState;
    currentCamera?: Camera;
}

function connectionState(currentState: DeviceState, loadingState: number): string {
    const ConnectionRestored = cameraStateType(CameraStateType.ConnectionRestored);
    const CONNECTION = cameraState(CameraState.CONNECTION);
    const connected = currentState.find(it => it.type === CONNECTION);
    if ((!connected && loadingState === LOAD_STATE_LOADED) || connected?.value === ConnectionRestored) {
        return STATE_CONNECTED;
    }

    return STATE_CONNECTION_LOST;
}

class CameraWidget extends React.Component<Props, CameraWidgetState> {
    imgRef: RefObject<HTMLImageElement>;

    frameRef: RefObject<HTMLDivElement>;

    timer?: NodeJS.Timeout;

    static getDerivedStateFromProps(nextProps: Props, prevState: CameraWidgetState): CameraWidgetState | null {
        const currentState = prevState.cameraState || EMPTY_LIST;
        const stateChanged = !_.isEqual(currentState, nextProps.deviceState);
        if (!_.isEqual(prevState.cameras, nextProps.devices) || stateChanged) {
            let loadedFlag = prevState.loaded;
            if (stateChanged) {
                const oldConnState = connectionState(prevState.cameraState || EMPTY_LIST, loadedFlag);
                const connState = connectionState(nextProps.deviceState || EMPTY_LIST, loadedFlag);
                if (connState === STATE_CONNECTED && oldConnState !== connState) {
                    loadedFlag = LOAD_STATE_RELOAD;
                }
            }

            return {
                ...prevState,
                cameras: nextProps.devices as Camera[],
                cameraState: nextProps.deviceState,
                loaded: loadedFlag,
            };
        }

        return null;
    }

    constructor(props: Props) {
        super(props);

        this.imgRef = createRef<HTMLImageElement>();
        this.frameRef = createRef<HTMLDivElement>();
        this.onCloseStreams = this.onCloseStreams.bind(this);

        let camera = props.devices.length ? (props.devices[0] as Camera) : undefined;
        if (props.streamId && props.devices?.length) {
            props.devices.forEach(device => {
                if (device.id === props.streamId) {
                    camera = device as Camera;
                } else {
                    (device as Camera).streams?.forEach(stream => {
                        if (stream.streamId === props.streamId) {
                            camera = device as Camera;
                        }
                    });
                }
            });
        }

        this.state = {
            loaded: LOAD_STATE_RELOAD,
            cameras: props.devices as Camera[],
            currentCamera: camera,
        };
    }

    componentDidMount(): void {
        this.startStreaming();
    }

    componentDidUpdate(): void {
        if (this.state.loaded === LOAD_STATE_RELOAD) {
            this.timer = setTimeout(() => {
                this.stopStreaming();
                this.timer = setTimeout(() => this.startStreaming(), 10);
            }, 10);
        }
    }

    componentWillUnmount(): void {
        if (this.timer) clearTimeout(this.timer);
        this.timer = undefined;
        this.stopStreaming(false);
    }

    onImageLoad(): void {
        this.setState(oldState => {
            return {
                ...oldState,
                loaded: LOAD_STATE_LOADED,
            };
        });
    }

    onImageFailed(): void {
        this.setState(oldState => {
            return {
                ...oldState,
                loaded: LOAD_STATE_FAILED,
            };
        });
    }

    onCopyToClipboard(event: React.MouseEvent): void {
        event.stopPropagation();
        copyToClipboard(this.cameraName);
    }

    onRefreshCamera(event: React.MouseEvent): boolean {
        event.stopPropagation();

        this.stopStreaming();
        this.timer = setTimeout(() => this.startStreaming(), 10);

        return false;
    }

    onFindInTree(event: React.MouseEvent): void {
        event.stopPropagation();
        new FindOnTree().execute(this.props.camera as CompositeNode);
    }

    onCloseStreams(event: React.MouseEvent): void {
        if (this.props.onCloseWindow) {
            event.stopPropagation();
            this.props.onCloseWindow();
            const allButtons = document.getElementsByClassName('btn-close-all-streams');
            // do we have started the process to close all streams? If not..
            if (!sessionStorage.getItem('closeAllStreams') || sessionStorage.getItem('closeAllStreams') === 'false') {
                // ..start it and don't allow to other clicks trigger the same process twice to avoid race condition
                sessionStorage.setItem('closeAllStreams', 'true');
                for (let index = 0; index < allButtons.length; index += 1) {
                    (allButtons[index] as HTMLElement).click();
                }
                // once it's closed, remove the flag
                sessionStorage.setItem('closeAllStreams', 'false');
            }
        }
    }

    get cameraName(): string {
        return this.props.camera?.name || '';
    }

    startStreaming(): void {
        probateSecurity().then(() => {
            if (this.imgRef?.current) {
                const streamId = this.state.currentCamera?.streams?.[0]?.streamId || '';
                this.imgRef.current.src = `/rest/camera/${this.state.currentCamera?.id || ''}/stream/${streamId}`;
            }
        });
    }

    stopStreaming(updateState = true): void {
        if (this.imgRef?.current) {
            this.imgRef.current.src = `${publicPath}/assets/images/camera_no_signal.jpg`;
        }

        if (updateState) {
            this.setState(oldState => {
                return {
                    ...oldState,
                    loaded: LOAD_STATE_STOPPED,
                };
            });
        }
    }

    resolveCameraState(): string {
        const currentState = this.props.deviceState || this.state.currentCamera?.state || [];
        return connectionState(currentState, this.state.loaded);
    }

    openFloatWindow(event: React.MouseEvent<HTMLDivElement, MouseEvent>): void {
        event?.stopPropagation();
        event?.preventDefault();

        const cameraId = this.props.camera?.id;
        if (cameraId && !this.props.floating) {
            const windowRect = getFrameRect(this.frameRef.current);
            this.props.openFloatingWindow({
                entityType: EntityType.CAMERA,
                entityId: cameraId,
                data: this.state.currentCamera?.id,

                initialWidth: windowRect.width,
                initialHeight: windowRect.height,
                initialTop: windowRect.top,
                initialLeft: windowRect.left,
            });
        }
    }

    cameraSelectorOptions(): { optionLabel: string; optionValue: string }[] {
        const defaultName = this.props.camera?.name || '';
        return this.props.devices.map(camera => {
            let name = camera.name || '';
            if (name.startsWith(defaultName)) {
                name = name.substring(defaultName.length);
            }
            return {
                optionLabel: name,
                optionValue: camera.id || '',
            };
        });
    }

    changeVideoStream(event: ChangeEvent<HTMLSelectElement>): void {
        const cameraId = event.target.value;
        const camera = this.props.devices.find(c => c.id === cameraId) as Camera;
        if (camera) {
            this.setState(oldState => {
                return {
                    ...oldState,
                    currentCamera: camera,
                    loaded: LOAD_STATE_RELOAD,
                };
            });
        }
    }

    render(): ReactNode {
        const { classes } = this.props;
        const cameraConnectionstate = this.resolveCameraState();

        const cameraName =
            this.props.camera?.name || this.state.currentCamera?.name || this.state.currentCamera?.externalId;
        const streamId = this.state.currentCamera?.streams?.[0]?.streamId || '';
        return (
            <Paper ref={this.frameRef} className={classes.camPanel} onClick={event => this.openFloatWindow(event)}>
                <div
                    style={{ position: 'absolute', left: 0, top: 0, bottom: 0, right: 30 }}
                    title={this.props.floating ? '' : 'Click to Keep window'}
                >
                    <div
                        className={clsx(
                            classes.camView,
                            classes.transparentDiv,
                            cameraConnectionstate === STATE_CONNECTED && classes.hiddenImage
                        )}
                    />
                    <img
                        className={clsx(
                            classes.camView,
                            this.state.loaded === LOAD_STATE_LOADED && classes.hiddenImage
                        )}
                        src={`${publicPath}/assets/images/camera_no_signal.jpg`}
                        alt={cameraName}
                    />
                    <img
                        onLoad={() => this.onImageLoad()}
                        onError={() => this.onImageFailed()}
                        ref={this.imgRef}
                        className={clsx(
                            classes.camView,
                            this.state.loaded !== LOAD_STATE_LOADED && classes.hiddenImage
                        )}
                        src={`/rest/camera/${this.state.currentCamera?.id}/stream/${streamId}`}
                        alt={cameraName}
                    />
                    <GridListTileBar
                        title={cameraConnectionstate}
                        subtitle={
                            <button
                                type="button"
                                onClick={e => {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }}
                            >
                                {this.props.devices.length <= 1 && <span title={cameraName}>{`${cameraName}`}</span>}
                                {this.props.devices.length > 1 && (
                                    <select
                                        value={this.state.currentCamera?.id}
                                        onChange={e => this.changeVideoStream(e)}
                                        className={classes.camSelector}
                                    >
                                        {this.cameraSelectorOptions().map(camera => (
                                            <option key={camera.optionValue} value={camera.optionValue}>
                                                {camera.optionLabel}
                                            </option>
                                        ))}
                                    </select>
                                )}
                            </button>
                        }
                        actionIcon={
                            <div className={classes.noWrap}>
                                <IconButton
                                    className={clsx(classes.cameraStatus, classes.cameraRefresh)}
                                    aria-label="Refresh camera"
                                    onClick={e => this.onRefreshCamera(e)}
                                >
                                    <CachedIcon />
                                </IconButton>
                                <Tooltip title="Copy name to clipboard">
                                    <IconButton
                                        className={clsx(
                                            classes.cameraStatus,
                                            cameraConnectionstate === STATE_CONNECTION_LOST && classes.cameraStatusWarn,
                                            cameraConnectionstate === STATE_CONNECTED && classes.cameraStatusOk
                                        )}
                                        aria-label={`info about ${cameraName}`}
                                        onClick={event => this.onCopyToClipboard(event)}
                                    >
                                        <InfoIcon />
                                    </IconButton>
                                </Tooltip>
                            </div>
                        }
                        classes={{
                            subtitle: classes.cameraStatusSubTitle,
                            title: classes.cameraStatusTitle,
                            rootSubtitle: classes.titleBar,
                        }}
                    />
                </div>
                <div style={{ position: 'absolute', width: 30, top: 0, bottom: 0, right: 0, padding: 3 }}>
                    <Tooltip title="Find on Tree" aria-label="show-filters">
                        <IconButton
                            size="small"
                            style={{ fontSize: '1.4rem' }}
                            onClick={event => this.onFindInTree(event)}
                        >
                            <CgListTree />
                        </IconButton>
                    </Tooltip>

                    {this.props.floating && (
                        <Tooltip title="Close All Streams" aria-label="show-filters">
                            <IconButton
                                className="btn-close-all-streams"
                                size="small"
                                style={{ fontSize: '1.4rem' }}
                                onClick={this.onCloseStreams}
                            >
                                <BsCameraVideoOff />
                            </IconButton>
                        </Tooltip>
                    )}
                </div>
            </Paper>
        );
    }
}

function mapDispatchToProps(dispatch: Dispatch<Action>): DispatchProps {
    return bindActionCreators(
        {
            openFloatingWindow: Actions.openFloatingWindow,
        },
        dispatch
    );
}

const mapStateToProps = (state: RootState, ownProps: CameraWidgetProps): ReduxStateProps => {
    return resolveCompositeDeviceState(state, ownProps.camera);
};

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(withStyles(styles)(CameraWidget)));
