/* eslint-disable class-methods-use-this */
import axios, { AxiosInstance, AxiosResponse, AxiosRequestConfig, AxiosPromise, AxiosRequestHeaders } from 'axios';
import { handleApiErrors, AuthenticationError, SecurityError, JWTToken } from '@common/utils/api.utils';
import { context as securityContext } from '@common/auth/auth.provider';

export class RestService {
    isRefreshing: boolean;

    failedRequests: any[];

    client: AxiosInstance;

    clientSecret: any;

    constructor() {
        this.isRefreshing = false;
        this.failedRequests = [];

        this.client = axios.create({
            headers: {
                clientSecret: this.clientSecret,
            },
        });
        this.beforeRequest = this.beforeRequest.bind(this);
        this.onRequestFailure = this.onRequestFailure.bind(this);
        this.processQueue = this.processQueue.bind(this);
        this.client.interceptors.request.use(this.beforeRequest);
        this.client.interceptors.response.use(this.onRequestSuccess, this.onRequestFailure);
    }

    beforeRequest(request: AxiosRequestConfig): AxiosRequestConfig {
        const token = securityContext().currentToken();
        if (token) {
            request.headers = request.headers || {};
            request.headers.Authorization = `${token.token_type} ${token.access_token}`;
        }

        return request;
    }

    onRequestSuccess(response: AxiosResponse<any>): any {
        return response.data;
    }

    redirectToLogin(): void {
        setTimeout(() => securityContext().logout(), 10);
    }

    async onRequestFailure(err: any): Promise<AxiosPromise<unknown>> {
        const { response } = err;
        if (response.status === 401 && err && err.config && !err.config.__isRetryRequest) {
            if (this.isRefreshing) {
                try {
                    const token = await new Promise<JWTToken>((resolve, reject) => {
                        this.failedRequests.push({ resolve, reject });
                    });
                    err.config.headers.Authorization = `${token.token_type} ${token.access_token}`;
                    return await this.client(err.config);
                } catch (e) {
                    return Promise.reject(e);
                }
            }

            this.isRefreshing = true;
            err.config.__isRetryRequest = true;
            return new Promise((resolve, reject) => {
                const token = securityContext().currentToken();
                if (!token || !token.refresh_token) {
                    reject(new AuthenticationError(SecurityError.AUTH_REFRESH));
                    this.redirectToLogin();
                } else {
                    securityContext()
                        .refreshToken()
                        .then(newToken => {
                            err.config.headers.Authorization = `${newToken.token_type} ${newToken.access_token}`;
                            this.isRefreshing = false;
                            this.processQueue(undefined, newToken);
                            // this.client(err.config).then(rez => resolve(rez));
                            resolve(this.client(err.config));
                        })
                        .catch(() => {
                            this.isRefreshing = false;
                            this.processQueue(new AuthenticationError(SecurityError.AUTH_REFRESH), undefined);
                            reject(new AuthenticationError(SecurityError.AUTH_REFRESH));
                            this.redirectToLogin();
                        });
                }
            });
        }

        throw response;
    }

    processQueue(error?: AuthenticationError, token: JWTToken | undefined = undefined): void {
        this.failedRequests.forEach(prom => {
            if (error) {
                prom.reject(error);
            } else {
                prom.resolve(token);
            }
        });
        this.failedRequests = [];
    }

    /*
	Content types:
		application/x-www-form-urlencoded
		application/json
  	*/
    post(url: string, payload: any, contentType = 'application/json'): Promise<any> {
        return this.client
            .post(url, payload, {
                headers: {
                    ...this.defaultHeaders(),
                    'Content-Type': contentType,
                },
            })
            .then(handleApiErrors)
            .catch(error => {
                throw error;
            });
    }

    get(url: string): Promise<any> {
        return this.client
            .get(url, {
                headers: {
                    ...this.defaultHeaders(),
                },
            })
            .then(handleApiErrors)
            .catch(error => {
                throw error;
            });
    }

    defaultHeaders(): AxiosRequestHeaders {
        return {
            'X-Requested-With': 'XMLHttpRequest',
            // 'X-XSRF-TOKEN': readCsrfTokenFromCookies('XSRF-TOKEN') // this seeems to be automatically done by Axios?!
        };
    }
}
