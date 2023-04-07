import { ApolloClient, HttpLink, NormalizedCacheObject, from, InMemoryCache } from '@apollo/client';
import errorLink from './graphql/jwt.refresh.link';
import authLink from './graphql/jwt.auth.link';

const httpLink = new HttpLink({
    uri: '/rest/graphql',
});

const client: ApolloClient<NormalizedCacheObject> = new ApolloClient({
    link: from([errorLink, authLink, httpLink]),
    cache: new InMemoryCache(),

    defaultOptions: {
        mutate: {
            fetchPolicy: 'no-cache',
            errorPolicy: 'all',
        },
        query: {
            fetchPolicy: 'no-cache',
            errorPolicy: 'all',
        },
        watchQuery: {
            fetchPolicy: 'no-cache',
            errorPolicy: 'ignore',
        },
    },
});

export default client;
