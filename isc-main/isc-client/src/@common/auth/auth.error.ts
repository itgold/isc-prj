export const SecurityError = {
    AUTHENTICATION: 1,
    AUTHORIZATION: 2,
    AUTH_REFRESH: 3,
};

export class AuthenticationError extends Error {
    errorCode: number;

    constructor(errorCode: number) {
        super('Invalid username or password');
        this.name = 'AuthenticationError';
        this.errorCode = errorCode;
    }

    toString(): string {
        switch (this.errorCode) {
            case SecurityError.AUTHENTICATION:
                return 'Login failed: Invalid username or password.';
            case SecurityError.AUTHORIZATION:
                return 'User is not authorized for the action.';
            case SecurityError.AUTH_REFRESH:
                return 'User session is expired.';
            default:
                return 'Login failed: Invalid username or password.';
        }
    }
}
