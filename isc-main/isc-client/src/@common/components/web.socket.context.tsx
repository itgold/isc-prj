import { CONNECTION_TIMEOUT, WebSocketService } from '@common/services/web.socket.service';
import { RootState } from 'app/store/appState';
import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import * as Actions from 'app/store/actions/auth';
import * as MessageActions from 'app/store/actions/fuse/message.actions';
import * as EventActions from 'app/store/actions/app/events.actions';
import { Dispatch, Action } from 'redux';

const CONNECTION_FAILURE_RETRY_DELAY = 10000;

interface WebSocketContextProps {
    children: React.ReactNode;
}

const WebSocketContextContext = React.createContext<WebSocketService | undefined>(undefined);

const unableToConnect = (dispatch: Dispatch<Action>, isAuthenticated: boolean): void => {
    if (isAuthenticated) {
        dispatch(
            MessageActions.showMessage({
                message: 'Trying to re-connect server ...', // text or html
                autoHideDuration: CONNECTION_TIMEOUT, // ms
                anchorOrigin: {
                    vertical: 'bottom', // top bottom
                    horizontal: 'right', // left center right
                },
                variant: 'error', // success error info warning null
            })
        );
    }
};

const connectionRestored = (dispatch: Dispatch<Action>, isAuthenticated: boolean): void => {
    if (isAuthenticated) {
        dispatch(
            MessageActions.showMessage({
                message: 'Connection to the server is restored.', // text or html
                autoHideDuration: 3000, // ms
                anchorOrigin: {
                    vertical: 'bottom', // top bottom
                    horizontal: 'right', // left center right
                },
                variant: 'success', // success error info warning null
            })
        );
    }
};

function WebSocketContext(props: WebSocketContextProps): JSX.Element {
    const { children } = props;
    const dispatch = useDispatch();
    const isAuthenticated = useSelector<RootState, boolean>(({ auth }) => !!auth?.login?.token?.access_token);
    const [service, setService] = useState<WebSocketService>(new WebSocketService());

    service.onConnectionRestored(() => connectionRestored(dispatch, isAuthenticated));
    service.onUnableToConnect(() => unableToConnect(dispatch, isAuthenticated));
    service.onMaxConnectRetryReached(() => {
        dispatch(MessageActions.hideMessage());
        dispatch(Actions.logout());
    });

    useEffect(() => {
        const connectWS = (): void => {
            if (isAuthenticated) {
                service
                    .connect(CONNECTION_TIMEOUT)
                    .then(() => {
                        service.subscribe('/topic/deviceStates', (message: any) => {
                            const data = JSON.parse(message.body);
                            if (data) {
                                dispatch(EventActions.updateDeviceState(data));
                                message.ack();
                            } else {
                                console.log('!!! ERROR: not parsable message for /topic/common', message.body);
                            }
                        });
                        service.subscribe('/topic/deviceEvents', (message: any) => {
                            const data = JSON.parse(message.body);
                            if (data) {
                                dispatch(EventActions.newDeviceEvent(data));
                                message.ack();
                            } else {
                                console.log('!!! ERROR: not parsable message for /topic/common', message.body);
                            }
                        });
                        service.subscribe('/topic/serverEvents', (message: any) => {
                            const data = JSON.parse(message.body);
                            if (data) {
                                dispatch(EventActions.newDeviceEvent(data));
                                message.ack();
                            } else {
                                console.log('!!! ERROR: not parsable message for /topic/common', message.body);
                            }
                        });
                        service.subscribe('/topic/alerts', (message: any) => {
                            const data = JSON.parse(message.body);
                            if (data) {
                                dispatch(EventActions.newDeviceEvent(data));
                                message.ack();
                            } else {
                                console.log('!!! ERROR: not parsable message for /topic/alerts', message.body);
                            }
                        });
                        service.subscribe('/queue/errors', (message: any) => {
                            console.log('New message for /queue/errors', message);
                            message.ack();
                        });
                    })
                    .catch((err: unknown) => {
                        console.log('Unable to connect', err);
                        unableToConnect(dispatch, isAuthenticated);
                        setTimeout(() => {
                            service.disconnect();
                            setService(new WebSocketService());
                        }, CONNECTION_FAILURE_RETRY_DELAY);
                    });
            }
        };

        if (isAuthenticated && isAuthenticated !== service.connected) {
            connectWS();
        } else if (!isAuthenticated) {
            service.disconnect();
            dispatch(MessageActions.hideMessage());
        }
    }, [dispatch, isAuthenticated, service]);

    return <WebSocketContextContext.Provider value={service}>{children}</WebSocketContextContext.Provider>;
}

function useWebSocket(): WebSocketService | undefined {
    const service = React.useContext(WebSocketContextContext);
    if (!service) {
        throw new Error('useWebSocketContext must be used within a WebSocketContext');
    }
    return service;
}

export { WebSocketContext, useWebSocket, WebSocketContextContext };
