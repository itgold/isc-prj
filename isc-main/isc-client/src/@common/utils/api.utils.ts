import { AxiosResponse } from 'axios';
import { AuthenticationError, SecurityError } from '@common/auth';

export * from '@common/auth';

export function handleApiErrors<T>(response: AxiosResponse<any>): T {
    if (response.status === 401) throw new AuthenticationError(SecurityError.AUTHENTICATION);
    else if (response.status === 403) throw new AuthenticationError(SecurityError.AUTHORIZATION);
    if (!response.data) throw Error(response.statusText);
    return response.data;
}
