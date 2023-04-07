package com.iscweb.component.web.handler;

import com.google.common.base.Strings;
import com.iscweb.common.exception.BaseApplicationException;
import com.iscweb.common.exception.BaseApplicationRuntimeException;
import com.iscweb.common.exception.IApplicationException;
import com.iscweb.component.web.auth.jwt.exceptions.JwtExpiredTokenException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Error handler for application controllers.
 */
@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {

    /**
     * Global error handler for application controllers. Instead of returning the full stacktrace
     * to the client making a request, it only sends the message. If the exception does not have
     * a meaningful message, a generic one is sent.
     * Note: {@link IOException}s are handled separately
     *
     * @param ex exception that has been caught
     */
    @ExceptionHandler({BaseApplicationException.class, BaseApplicationRuntimeException.class})
    public void handleApplicationException(IApplicationException ex, HttpServletResponse response) throws IOException {
        log.warn("Handling {}: {}", ex.getClass(), ex.getMessage());
        response.sendError(ex.getErrorCode().getHttpCode(), getExceptionMessage(ex));
    }

    /**
     * Error handler for IO Exceptions. This case is handled separately because broken pipe exceptions
     * (i.e. clients abandoning ongoing HTTP(S) request) do not need to be reported extensively in logs.
     */
    @ExceptionHandler(IOException.class)
    public void handleIoException(IOException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(e), "Broken pipe")) {
            log.info("Broken pipe (client abandoning HTTPS connection) at {}", request.getPathInfo());
        } else {
            handleAnyException(e, response);
        }
    }

    /**
     * Error handler for missing required request parameters.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingServletRequestParameterException(MissingServletRequestParameterException ex,
                                                              HttpServletResponse response) throws IOException {
        log.warn("Missing request parameter: {}", ex.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    /**
     * Error handler for when the attempted HTTP method is not accepted, even though the URL is valid.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex,
                                                             HttpServletRequest request,
                                                             HttpServletResponse response) throws IOException {
        log.warn("{} request to {} not supported", request.getMethod(), request.getRequestURI());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    /**
     * Error handler for failed conversions (thrown by ConditionalGenericConverter). These exceptions
     * can have an IApplicationException nested in the stack trace; if one is found, it is handled.
     * Otherwise, this method defers to generic handler.
     */
    @ExceptionHandler({ConversionFailedException.class, MethodArgumentTypeMismatchException.class})
    public void handleConversionFailedException(Exception ex, HttpServletResponse response) throws IOException {
        IApplicationException cause = findApplicationExceptionCause(ex);
        if (cause != null) {
            handleApplicationException(cause, response);
        } else {
            handleAnyException(ex, response);
        }
    }

    /**
     * AccessDeniedExceptions are thrown when Spring Security determines that the principal user lacks authority
     * to complete an operation. This method catches these exceptions and responds 403, but with no error message.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }

    /**
     * Global error handler for application controllers. Since we are already catching ServiceException,
     * by "pattern matching" we fallback here. This method would handle exceptions that are out of the
     * Phantasm's control and would send 500 error code back to the client.
     *
     * @param ex exception that has been caught.
     */
    @ExceptionHandler(Exception.class)
    public void handleAnyException(Exception ex, HttpServletResponse response) throws IOException {
        log.error("Application error", ex);
        if (!response.isCommitted()) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An application error occurred");
        }
    }

    /**
     * Jwt token expired case handling.
     * @param response servlet response.
     * @throws IOException if application failed.
     */
    @ExceptionHandler(JwtExpiredTokenException.class)
    public void handleJwtTokenException(HttpServletResponse response) throws IOException {
        log.warn("Jwt Token Expired");
        if (!response.isCommitted()) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An application error occurred");
        }
    }

    /**
     * Iterate through stack trace, searching for an IApplicationException.
     * If found, handle it using {@link GlobalErrorHandler#handleApplicationException}
     * <p>
     * This is intended for use in cases where throwing an exception within our application
     * code causes Spring to raise one or more new exceptions as a result. For example,
     * when we throw BaseApplicationException in EnumConverter, this actually results in
     * Spring throwing a MethodArgumentTypeMismatchException.
     *
     * @param ex the exception to search through the causation chain of
     */
    public IApplicationException findApplicationExceptionCause(Exception ex, int depth) {
        Throwable cause = ex;
        int count = 0;
        do {
            cause = cause.getCause();
            count++;
        } while (count < depth && cause != null && !(cause instanceof IApplicationException));

        return (IApplicationException) cause;
    }

    public IApplicationException findApplicationExceptionCause(Exception ex) {
        return findApplicationExceptionCause(ex, 5);
    }

    private String getExceptionMessage(IApplicationException ex) {
        String exceptionMessage = ex.getMessage();
        if (Strings.isNullOrEmpty(exceptionMessage)) {
            exceptionMessage = "a(n) " + ex.getClass().getName();
        }

        return exceptionMessage;
    }
}
