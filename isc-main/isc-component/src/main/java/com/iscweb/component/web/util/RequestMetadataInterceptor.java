package com.iscweb.component.web.util;

import com.iscweb.common.model.metadata.ClientType;
import com.iscweb.component.api.BasePublicApiController;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Intercepts requests and stores metadata about their origin to be used to generate APM events.
 */
@Component
public class RequestMetadataInterceptor extends HandlerInterceptorAdapter {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private @NonNull RequestUtilityService requestUtilityService;

    /**
     * Before the request, set the default client type to API or WEB based on which controller is handling it.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ClientType clientType = isApiHandler(handler) ? ClientType.API : ClientType.WEB;
        getRequestUtilityService().setDefaultClientType(clientType);
        return true;
    }

    /**
     * Return true if the handler is a subclass of BasePublicApiController.
     */
    private boolean isApiHandler(Object handler) {
        return HandlerMethod.class.isAssignableFrom(handler.getClass()) &&
                BasePublicApiController.class.isAssignableFrom(((HandlerMethod) handler).getBean().getClass());
    }
}
