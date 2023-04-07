import { fromPromise, ServerError } from '@apollo/client';
// eslint-disable-next-line import/no-extraneous-dependencies
import { onError } from '@apollo/client/link/error';
import { context as securityContext } from '@common/auth/auth.provider';
import { JWTToken } from '@common/auth';

type CallBack = () => void;
let isRefreshing = false;
let pendingRequests: CallBack[] = [];

const setIsRefreshing = (value: boolean): void => {
    isRefreshing = value;
};

const addPendingRequest = (pendingRequest: () => void): void => {
    pendingRequests.push(pendingRequest);
};

const resolvePendingRequests = (): void => {
    pendingRequests.map(callback => callback());
    pendingRequests = [];
};

const cancelPendingRequests = (): void => {
    pendingRequests = [];
};

const getNewToken = async (): Promise<JWTToken> => {
    return securityContext().refreshToken();
};

const errorLink = onError(({ graphQLErrors, networkError, operation, forward }) => {
    let forwardResult;

    if (graphQLErrors && graphQLErrors.length) {
        console.log('GraphQL errors', graphQLErrors);
    }

    if (networkError) {
        const serverError = networkError as ServerError;
        if (serverError.statusCode === 401) {
            console.log('JWT token is expired. Trying to refresh ...');
            if (!isRefreshing) {
                setIsRefreshing(true);
                forwardResult = fromPromise(
                    getNewToken()
                        .then(({ token_type, access_token }) => {
                            console.log('JWT token successfully refreshed.');
                            operation.setContext(({ headers = {} }: any) => ({
                                headers: {
                                    ...headers,
                                    Authorization: `${token_type} ${access_token}`,
                                },
                            }));
                            resolvePendingRequests();
                            return access_token;
                        })
                        .catch(() => {
                            cancelPendingRequests();
                            console.log('Unable to refresh JWT token. Redirect to login.');
                            // Handle token refresh errors e.g clear stored tokens, redirect to login, ...
                            securityContext().logout();
                        })
                        .finally(() => {
                            setIsRefreshing(false);
                        })
                ).filter(value => Boolean(value));
            } else {
                // Will only emit once the Promise is resolved
                console.log('... cache request for replay ...');
                forwardResult = fromPromise(
                    new Promise(resolve => {
                        addPendingRequest(() => resolve(true));
                    })
                );
            }
        }
    }

    return forwardResult ? forwardResult.flatMap(() => forward(operation)) : undefined;
});

export default errorLink;
