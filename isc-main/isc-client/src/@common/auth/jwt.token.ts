export default class JWTToken {
    access_token: string;

    expires_in: number;

    refresh_token: string;

    roles: string[];

    status: string;

    timestamp: number;

    token_type: string;

    username: string;

    constructor(data?: unknown) {
        const rec = data as JWTToken;
        this.access_token = rec?.access_token;
        this.expires_in = rec?.expires_in;
        this.refresh_token = rec?.refresh_token;
        this.roles = rec?.roles;
        this.status = rec?.status;
        this.timestamp = rec?.timestamp;
        this.token_type = rec?.token_type;
        this.username = rec?.username;
    }
}
