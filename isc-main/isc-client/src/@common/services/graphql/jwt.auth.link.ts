import { ApolloLink } from '@apollo/client';
import { context as securityContext } from '@common/auth/auth.provider';

type Headers = {
    Authorization?: string;
};

const authLink = new ApolloLink((operation, forward) => {
    const jwtToken = securityContext().currentToken();
    const accessToken = jwtToken ? `${jwtToken.token_type} ${jwtToken.access_token}` : null;

    operation.setContext(({ headers }: { headers: Headers }) => ({
        headers: {
            ...headers,
            Authorization: accessToken,
        },
    }));

    return forward(operation);
});

export default authLink;
