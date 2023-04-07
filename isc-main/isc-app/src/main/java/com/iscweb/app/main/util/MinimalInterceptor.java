package com.iscweb.app.main.util;

import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is example HTTP requests interceptor intended in future to have logic for some monitoring.
 */
public class MinimalInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler) throws Exception {
        System.out.println("MINIMAL: INTERCEPTOR PREHANDLE CALLED");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MINIMAL: INTERCEPTOR POSTHANDLE CALLED");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        System.out.println("MINIMAL: INTERCEPTOR AFTERCOMPLETION CALLED");
    }
}
