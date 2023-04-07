/* eslint-disable no-unused-vars */
/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable class-methods-use-this */
import JWTToken from './jwt.token';

export interface AuthContextProvider {
    currentToken(): JWTToken;
    refreshToken(): Promise<JWTToken>;
    logout(): void;
    isAdmin(roles: string[]): boolean;
}

class EmptyContextProvider implements AuthContextProvider {
    refreshToken(): Promise<JWTToken> {
        throw new Error('Method not implemented.');
    }

    logout(): void {
        throw new Error('Method not implemented.');
    }

    currentToken(): JWTToken {
        throw new Error('Not implemented');
    }

    isAdmin(roles: string[]): boolean {
        return false;
    }
}

let globalAuthContextProvider = new EmptyContextProvider();

export function context(): AuthContextProvider {
    return globalAuthContextProvider;
}

export function updateAuthContextProvider(provider: AuthContextProvider): void {
    globalAuthContextProvider = provider;
}
