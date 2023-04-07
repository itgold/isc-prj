package com.iscweb.component.web.auth;

public final class SecurityConstants {

    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_COOKIE_NAME = "X-Authorization";
    public static final String AUTHENTICATION_URL = "/api/auth/login";
    public static final String REFRESH_TOKEN_URL = "/api/auth/token";
    public static final String LOGOUT_URL = "/api/logout";

    public static final String API_PUBLIC_ROOT_URL = "/api/**";
    public static final String API_REST_URL = "/rest/";
    public static final String API_REST_ROOT_URL = API_REST_URL + "**";
    public static final String API_WS_ROOT_URL = "/ws/**";

    public static final String SECURED_BASE_URL = "/";
    public static final String PUBLIC_BASE_URL = "/login";

    public enum SecurityErrorCode {
        AUTHENTICATION, JWT_TOKEN_EXPIRED
    }
}
