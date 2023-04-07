package com.iscweb.component.web.util;

import com.iscweb.common.model.metadata.ClientType;
import com.iscweb.common.service.IApplicationService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;


/**
 * Used to get the details of a given, or currently active, request.
 */
@Service
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestUtilityService implements IApplicationService {

    protected static final String CLIENT_TYPE_HEADER_NAME = "Client-Type";
    protected static final String CLIENT_VERSION_HEADER_NAME = "Client-Version";
    protected static final String CLIENT_METATAG_HEADER_NAME = "Client-Metatag";

    /**
     * The currently active HTTP request.
     */
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private @NonNull HttpServletRequest request;

    /**
     * Holds the fallback value for client type. To be set by ControllerAspect (in loop services).
     * NOTE: Since this service is request-scoped, this value will be null at the beginning of each request.
     */
    @Getter
    @Setter
    private ClientType defaultClientType;

    /**
     * If 'Client-Type' header exists, return it.
     * Otherwise, if defaultClientType has been set, return it.
     * Otherwise, return ClientType.WEB.
     *
     * @return the resolved client type
     */
    public ClientType getResolvedClientTypeHeader() {
        HttpServletRequest request = getRequest();
        ClientType result = null;
        if (request != null) {
            result = getClientTypeHeader(request);
        }
        if (result == null) {
            result = getDefaultClientType();
        }
        if (result == null) {
            result = ClientType.WEB;
        }

        return result;
    }

    /**
     * @return the value of the 'Client-Type' header in the given request
     */
    public ClientType getClientTypeHeader(HttpServletRequest request) {
        ClientType result = null;

        String clientType = request.getHeader(CLIENT_TYPE_HEADER_NAME);
        if (clientType != null) {
            clientType = clientType.toUpperCase()
                                   .replaceAll("-|\\s", "_");
            if (EnumUtils.isValidEnum(ClientType.class, clientType)) {
                result = ClientType.valueOf(clientType);
            }
        }

        return result;
    }

    public ClientType getClientTypeHeader() {
        ClientType result = null;
        HttpServletRequest request = getRequest();
        if (request != null) {
            result = getClientTypeHeader(request);
        }

        return result;
    }

    /**
     * @return the value of the 'Client-Version' header in the given request
     */
    public String getClientVersionHeader(HttpServletRequest request) {
        return request.getHeader(CLIENT_VERSION_HEADER_NAME);
    }

    /**
     * @return the value of the 'Client-Version' header in the currently active request
     */
    public String getClientVersionHeader() {
        String result = null;
        HttpServletRequest request = getRequest();
        if (request != null) {
            result = getClientVersionHeader(request);
        }
        return result;
    }

    /**
     * @return the value of the 'Client-Metatag' header in the given request
     */
    public String getClientMetatagHeader(HttpServletRequest request) {
        return request.getHeader(CLIENT_METATAG_HEADER_NAME);
    }

    /**
     * @return the value of the 'Client-Metatag' header in the currently active request
     */
    public String getClientMetatagHeader() {
        String result = null;
        HttpServletRequest request = getRequest();
        if (request != null) {
            result = getClientMetatagHeader(request);
        }
        return result;
    }
}
