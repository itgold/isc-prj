import axios, { AxiosRequestHeaders } from 'axios';
import cookiesService from '@common/services/cookies.service';
import { handleApiErrors, SecurityError, AuthenticationError, JWTToken } from '@common/utils/api.utils';
import { RestService } from '@common/services/rest.service';
import storageService from 'app/services/storage.service';

export * from '@common/auth/auth.utils';

export const LOGIN_URL = '/login';
export const INDEX_URL = '/';

export const AUTH_STORAGE = 'token';
export const AUTH_COOKIE = 'JSESSIONID';

class HelperService extends RestService {
    queryUser(): Promise<any> {
        return this.client.get('/rest/user', {
            headers: {
                ...this.defaultHeaders(),
            },
        });
    }
}
const helperService = new HelperService();

export function probateSecurity(): Promise<any> {
    return new Promise((resolve, reject) => {
        helperService
            .queryUser()
            .then(rez => resolve(rez))
            .catch(rez => reject(rez));
    });
}

export function resolveToken(): JWTToken | null {
    const storedToken = storageService.readProperty(AUTH_STORAGE);
    if (storedToken) {
        // parse it down into an object
        try {
            return JSON.parse(storedToken) as JWTToken;
        } catch (e) {
            console.log('Invalid token', e);
        }
    }

    return null;
}

export function updateToken(token: JWTToken): void {
    storageService.updateProperty(AUTH_STORAGE, JSON.stringify(token));
}

export function crearToken(): void {
    storageService.updateProperty(AUTH_STORAGE, null);
    cookiesService.deleteCookie(AUTH_COOKIE);
}

function serverStatus(): Promise<any> {
    return axios
        .get('/rest/status', {
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
            },
        })
        .then(handleApiErrors)
        .catch(error => {
            if (error.response && error.response.status && error.response.status === 401) {
                throw new AuthenticationError(SecurityError.AUTHENTICATION);
            } else {
                throw error;
            }
        });
}

export function login(username: string, password: string): Promise<any> {
    const payload = { username, password };

    // Note: anonymous call to /rest/status will bring correct XSRF token from the server
    return serverStatus().then(() => {
        return axios
            .post('/api/auth/login', payload, {
                headers: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'X-XSRF-TOKEN': cookiesService.getCookie('XSRF-TOKEN'),
                },
            })
            .then(handleApiErrors)
            .catch(error => {
                if (error.response && error.response.status && error.response.status === 401) {
                    throw new AuthenticationError(SecurityError.AUTHENTICATION);
                } else {
                    throw error;
                }
            });
    });
}

export function refreshAccessToken(refreshToken: string): Promise<JWTToken> {
    return axios
        .get('/api/auth/token', {
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                Authorization: `Bearer ${refreshToken}`,
            },
        })
        .then(handleApiErrors)
        .then(token => token as JWTToken)
        .then(token => {
            console.log('!!! JWT token was refreshed', token);
            token.refresh_token = refreshToken;
            return token;
        })
        .catch(error => {
            if (error.response && error.response.status && error.response.status === 401) {
                throw new AuthenticationError(SecurityError.AUTHENTICATION);
            } else {
                throw error;
            }
        });
}

export function logout(token: JWTToken): Promise<any> {
    const headers: AxiosRequestHeaders = {
        'X-Requested-With': 'XMLHttpRequest',
    };
    if (token) {
        headers.Authorization = `${token.token_type} ${token.access_token}`;
    }

    return axios.post('/api/logout', null, { headers }).catch(() => {
        // Do nothing
    });
}
