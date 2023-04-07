package com.iscweb.component.web.util;

import com.iscweb.common.exception.InvalidOperationException;
import com.iscweb.common.model.dto.api.v13.ZonedDateTimeRange;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.time.ZonedDateTime;


/**
 * Handles conversion of loop request parameters into ZonedDateTimeRange. Expects to find parameters 'from' and 'to'
 * in the request, both integers representing milliseconds since epoch.  If 'from' is null, but 'to' is not, 'from'
 * will default to 0 milliseconds since epoch.  If 'to' is null, but 'from' is not, then 'to' will default to the
 * current time. If both are null, then the result will be null.
 */
public class ZonedDateTimeRangeHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(ZonedDateTimeRange.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        Object result = null;

        String from = nativeWebRequest.getParameter("from");
        String to = nativeWebRequest.getParameter("to");

        Long fromMilliseconds = null;
        Long toMilliseconds = null;
        if (from != null) {
            fromMilliseconds = Long.valueOf(from);
        }
        if (to != null) {
            toMilliseconds = Long.valueOf(to);
        }

        if (fromMilliseconds != null && toMilliseconds == null) {
            toMilliseconds = ZonedDateTime.now().toInstant().toEpochMilli();
        }
        if (fromMilliseconds == null && toMilliseconds != null) {
            fromMilliseconds = (long) 0;
        }

        // If one is null, both are...
        if (fromMilliseconds != null) {
            try {
                result = new ZonedDateTimeRange(fromMilliseconds, toMilliseconds);
            } catch (IllegalArgumentException ex) {
                throw new InvalidOperationException(ex.getMessage());
            }
        }

        return result;
    }
}
