import jwtDecode from 'jwt-decode';
import { JWTToken } from '.';

interface DecodedJWTToken {
    exp: number;
}

export function isAuthTokenValid(accessToken: string): boolean {
    if (!accessToken) {
        return false;
    }

    const decoded = jwtDecode<DecodedJWTToken>(accessToken);
    const currentTime = Date.now() / 1000;
    if (decoded.exp < currentTime) {
        console.warn('access token expired');
        return false;
    }

    return true;
}

export function isTokenExpired(token: JWTToken): boolean {
    if (token) {
        const now = new Date().getTime();
        const expiry = token.timestamp + token.expires_in * 1000;

        // if the token has expired return false
        if (now < expiry) {
            return false;
        }
    }

    return true;
}
