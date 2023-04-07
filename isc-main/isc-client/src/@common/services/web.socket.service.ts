// https://github.com/sockjs/sockjs-client, http://sockjs.org
import SockJS from 'sockjs-client';
// https://stomp-js.github.io/api-docs/latest/classes/Client.html
import { Client, IFrame, IMessage, StompHeaders, StompSubscription } from '@stomp/stompjs';
import { AUTH_COOKIE, resolveToken, probateSecurity } from 'app/utils/auth.utils';
import cookiesService from '@common/services/cookies.service';

export const CONNECTION_TIMEOUT = 10000;

const WEBSOCKET_ENDPOINT = '/ws';
const DEBUG_MESSAGES = false;

const MAX_DISCONNECT_TIME = 1000 * 60 * 60; // 1 hour max time without been connected
const CONNECTION_FAILURE_RETRY_DELAY = 5000;
const MAXRETRY_COUNT = MAX_DISCONNECT_TIME / CONNECTION_FAILURE_RETRY_DELAY;
// server read timeout, I.e. because of the default multiplier is 2,
// the client will close the connection if will not have any message from server within 2 min
// this is more then 10 missed heartbeat messages
const SERVER_HEARTBEAT_DELAY = 60000;
// how often to send hertbeat messages to the server
const CLIENT_HEARTBEAT_DELAY = 10000;

declare type SubscriptionCallback = (message: IMessage) => void;

interface Subscribtions {
    [key: string]: SubscriptionInfo;
}
interface SubscriptionInfo {
    subscription: StompSubscription;
    callback: SubscriptionCallback;
}

const requestHeaders = (useAjax = true): StompHeaders => {
    const headers: StompHeaders = useAjax
        ? {
              'X-Requested-With': 'XMLHttpRequest',
          }
        : {};

    headers.ack = 'client';
    const token = resolveToken();
    if (token) {
        headers.Authorization = `${token.token_type} ${token.access_token}`;
    }

    return headers;
};
const trace = (text: string): void => {
    if (DEBUG_MESSAGES) {
        console.log(text, new Date());
    }
};

export class WebSocketService {
    client?: Client;

    subscriptions: Subscribtions = {};

    retryCount = 0;

    OnMaxConnectRetryReached?: () => void;

    OnUnableToConnect?: (err: any) => void;

    OnConnectionRestored?: () => void;

    get connected(): boolean {
        return !!this.client && this.client?.connected === true;
    }

    connect(timeout: number): Promise<boolean> {
        let resolved = false;
        this.retryCount = 0;

        if (this.client) {
            this.disconnect();
        }

        const self = this;
        return new Promise<boolean>((resolve, reject) => {
            const rejectConnection = (data: any): void => {
                if (!resolved) {
                    resolved = true;
                    reject(data);
                    self.disconnect();
                }
            };

            console.log('START WS connection ...');

            const wsUrl = `${window.location.protocol}//${window.location.host}${WEBSOCKET_ENDPOINT}`;
            try {
                self.client = new Client({
                    // connectionTimeout: CONNECTION_TIMEOUT,
                    connectHeaders: requestHeaders(false),
                    reconnectDelay: CONNECTION_FAILURE_RETRY_DELAY,
                    heartbeatIncoming: SERVER_HEARTBEAT_DELAY,
                    heartbeatOutgoing: CLIENT_HEARTBEAT_DELAY,

                    webSocketFactory: () => {
                        return new SockJS(wsUrl, null, {
                            timeout,
                            sessionId() {
                                const id = cookiesService.getCookie(AUTH_COOKIE) || `${new Date().getTime()}`;
                                return id;
                            },
                        });
                    },
                    beforeConnect: () => {
                        // make sure that JWT token is valid
                        return new Promise<void>(resolve2 => {
                            self.retryCount += 1;
                            if (self.retryCount > MAXRETRY_COUNT) {
                                self.disconnect();
                                console.log('WS MAX RETRY');
                                if (self.OnMaxConnectRetryReached) {
                                    self.OnMaxConnectRetryReached();
                                }
                                // reject2(new Error('Max WS re-connect try reached.'));
                                resolve2();
                            } else {
                                probateSecurity()
                                    .then(() => {
                                        console.log('WS headers - refreshed');
                                        if (self.client) {
                                            self.client.connectHeaders = requestHeaders(false);
                                        }
                                        resolve2();
                                    })
                                    .catch(err => {
                                        console.log("WS headers - CAN'T refresh", err);
                                        resolve2(err);
                                    });
                            }
                        });
                    },

                    // debugging
                    logRawCommunication: DEBUG_MESSAGES,
                    debug(str) {
                        trace(str);
                    },

                    // callbacks
                    onWebSocketClose: (evt: CloseEvent) => {
                        // https://developer.mozilla.org/en-US/docs/Web/API/CloseEvent
                        console.log('WS closed', evt.code, evt.reason);
                        // show it only if it is not the first connection time
                        if (resolved && self.OnUnableToConnect) {
                            self.OnUnableToConnect(evt);
                        }
                        rejectConnection(evt);
                    },
                    onWebSocketError: (evt: Event) => {
                        console.log('onWebSocketError', evt);
                        rejectConnection(evt);
                    },
                    onConnect: (receipt: IFrame) => {
                        // Reconnect all subscriptions existed before connection re-established
                        const existingSubscriptions: { destination: string; callback: SubscriptionCallback }[] = [];
                        Object.keys(self.subscriptions).forEach(destination => {
                            const s = self.subscriptions[destination];
                            if (s) {
                                try {
                                    existingSubscriptions.push({
                                        destination,
                                        callback: s.callback,
                                    });

                                    s.subscription.unsubscribe();
                                    delete self.subscriptions[destination];
                                } catch (e) {
                                    // do nothing, just ignore
                                }
                            }
                        });

                        existingSubscriptions.forEach(s => self.subscribe(s.destination, s.callback));

                        if (DEBUG_MESSAGES) console.log('WS connected', receipt);
                        if (!resolved) {
                            resolved = true;
                            resolve(true);
                        } else if (self.OnConnectionRestored) {
                            // show it only if it is not the first connection time
                            self.OnConnectionRestored();
                        }
                    },
                    onDisconnect: (receipt: IFrame) => {
                        rejectConnection(receipt);
                    },
                    onStompError: (receipt: IFrame) => {
                        console.log('onStompError', receipt);
                    },
                });

                self.client.activate();
            } catch (e) {
                console.log('Unable to connect', e);
                rejectConnection(e);
            }
        });
    }

    subscribe(destination: string, callback: SubscriptionCallback): StompSubscription {
        const { client } = this;
        if (client) {
            let subscription = this.subscriptions[destination]?.subscription;
            if (!subscription) {
                subscription = client.subscribe(destination, callback, requestHeaders(false));
                this.subscriptions[destination] = {
                    subscription,
                    callback,
                };

                console.log(`Subscribed to: ${destination}`);
            }

            return subscription;
        }

        throw new Error('Message Service is not initialized!');
    }

    disconnect(): void {
        if (this.client) {
            console.log('WS CLEANUP after disconnect');
            this.client?.deactivate();
            this.client = undefined;
        }
    }

    onUnableToConnect(callback: (err: any) => void): void {
        this.OnUnableToConnect = callback;
    }

    onMaxConnectRetryReached(callback: () => void): void {
        this.OnMaxConnectRetryReached = callback;
    }

    onConnectionRestored(callback: () => void): void {
        this.OnConnectionRestored = callback;
    }
}
