package com.iscweb.common.exception.util;

import lombok.Getter;

/**
 * Contains error codes that can be used in conjunction with ServiceException to declare
 * a type of error to be returned to the client side.
 */
public enum ErrorCode {

    ERROR_CODE_FORBIDDEN("error.forbidden", Constants.SC_FORBIDDEN),
    ERROR_CODE_BAD_REQUEST("error.bad.request", Constants.SC_BAD_REQUEST),
    ERROR_CODE_UNAUTHORIZED("error.unauthorized", Constants.SC_UNAUTHORIZED),
    ERROR_CODE_TOO_LARGE("error.too.large", Constants.SC_REQUEST_ENTITY_TOO_LARGE),
    ERROR_CODE_NO_CONTENT("error.no.content", Constants.SC_NO_CONTENT),
    ERROR_SERVER_ERROR("error.server.error", Constants.SC_INTERNAL_SERVER_ERROR),
    ;

    private static class Constants {
        /**
         * Status code (403) indicating that the server understood the request but
         * refused to fulfill it.
         */
        public static final int SC_FORBIDDEN = 403;
        /**
         * Status code (400) indicating that the request sent by the client was
         * syntactically incorrect.
         */
        public static final int SC_BAD_REQUEST = 400;
        /**
         * Status code (401) indicating that the request requires HTTP authentication.
         */
        public static final int SC_UNAUTHORIZED = 401;
        /**
         * Status code (413) indicating that the server is refusing to process the
         * request because the request entity is larger than the server is willing
         * or able to process.
         */
        public static final int SC_REQUEST_ENTITY_TOO_LARGE = 413;
        /**
         * Status code (404) indicating that the requested resource is not available.
         */
        public static final int SC_NO_CONTENT = 204;
        /**
         * Status code (500) indicating that there was an error inside the HTTP server
         * which prevented it from fulfilling the request.
         */
        public static final int SC_INTERNAL_SERVER_ERROR = 500;
    }

    @Getter
    private final String code;

    @Getter
    private final Integer httpCode;

    ErrorCode(String code, Integer httpCode) {
        this.code = code;
        this.httpCode = httpCode;
    }
}
