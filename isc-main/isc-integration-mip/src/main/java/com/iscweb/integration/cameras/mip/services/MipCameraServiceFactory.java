package com.iscweb.integration.cameras.mip.services;

import com.iscweb.common.service.IApplicationService;
import com.iscweb.integration.cameras.mip.dto.ProxyParamsDto;
import com.iscweb.integration.cameras.mip.services.mip.IServiceRegistrationService;
import com.mip.registry.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.xml.ws.soap.SOAPBinding;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Soap client binding factory class.
 *
 * Note: it provides two helper methods <code>createJaxWsProxy</code> or <code>createJaxWsProxySoap12</code> to generate
 * instance of SOAP client for given SOAP interface.
 */
@Slf4j
@Component
public class MipCameraServiceFactory implements IApplicationService {

    private static final String SERVICE_URL = "/ManagementServer/ServiceRegistrationService.svc";
    private static final String SERVICE_WSDL = SERVICE_URL + "?wsdl";

    @Getter
    @Setter(onMethod = @__({@Value("${integration.cameras.mip.registry.secured}")}))
    private boolean useHttps;

    @Getter
    @Setter(onMethod = @__({@Value("${integration.cameras.mip.registry.address}")}))
    private String address;

    @Getter
    @Setter(onMethod = @__({@Value("${integration.cameras.mip.registry.port}")}))
    private int port;

    @Getter
    @Setter(onMethod = @__({@Value("${integration.cameras.mip.registry.username}")}))
    private String username;

    @Getter
    @Setter(onMethod = @__({@Value("${integration.cameras.mip.registry.password}")}))
    private String password;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private MipScheduledTasksService scheduledTasksService;

    private static final String AUTH_TYPE = "Basic";

    /**
     * TODO: implement all services endpoints resolution from the Registry service.
     * So, we will need only registry service to be in the configuration and other resolvable through it.
     *
     * @throws Exception
     */
    public void resolveServices() throws Exception {
        URL connectionUrl;
        try {
            connectionUrl = new URL(isUseHttps() ? "https" : "http", getAddress(), getPort(), SERVICE_WSDL);
        } catch (MalformedURLException e) {
            log.error("Unable to connect to the MIP server", e);
            throw new RuntimeException("Unable to connect to the MIP server", e);
        }

        IServiceRegistrationService registryService = createJaxWsProxy(
                new ProxyParamsDto(isUseHttps(), getAddress(), getPort(), SERVICE_URL, getUsername(), getPassword()),
                IServiceRegistrationService.class);

        log.error("Found MIP services:");
        ArrayOfServiceInfo services = registryService.getServices();
        for (ServiceInfo serviceInfo : services.getServiceInfo()) {
            log.error("  Name: {}, Description: {}",
                    serviceInfo.getName(),
                    serviceInfo.getDescription());

            for (ServiceEndpoint endpoint : serviceInfo.getEndpoints().getServiceEndpoint()) {
                log.error("      Endpoint: {}, url: {}",
                        endpoint.getName(),
                        endpoint.getUri());
            }
            ArrayOfstring uris = serviceInfo.getUri();
            if (uris != null && CollectionUtils.isEmpty(uris.getString())) {
                for (String endpoint : uris.getString()) {
                    log.error("      Uri: {}", endpoint);
                }
            }
        }
    }

    protected <T> T createJaxWsProxyInternal(ProxyParamsDto params, Class<T> clazz, boolean useSoap12) {
        URL connectionUrl;
        try {
            connectionUrl = new URL(params.secure ? "https" : "http", params.address, params.port, params.endpoint);
        } catch (MalformedURLException e) {
            log.error("Unable to connect to the MIP server", e);
            throw new RuntimeException("Unable to connect to the MIP server", e);
        }

        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(clazz);
        jaxWsProxyFactoryBean.setAddress(connectionUrl.toExternalForm());
        if (useSoap12) {
            jaxWsProxyFactoryBean.setBindingId(SOAPBinding.SOAP12HTTP_BINDING);
        }

        @SuppressWarnings("unchecked")
        T service = (T) jaxWsProxyFactoryBean.create();

        Client client = ClientProxy.getClient(service);

        HTTPConduit http = (HTTPConduit) client.getConduit();
        AuthorizationPolicy authorizationPolicy = new AuthorizationPolicy();
        authorizationPolicy.setUserName(params.username);
        authorizationPolicy.setPassword(params.password);
        authorizationPolicy.setAuthorizationType(AUTH_TYPE);
        http.setAuthorization(authorizationPolicy);

        // https://issues.apache.org/jira/browse/CXF-2688
        // https://issues.apache.org/jira/browse/CXF-2693
        if (http.getTlsClientParameters() == null) {
            http.setTlsClientParameters(new TLSClientParameters());
        }
        http.getTlsClientParameters().setUseHttpsURLConnectionDefaultSslSocketFactory(true);
        http.getTlsClientParameters().setUseHttpsURLConnectionDefaultHostnameVerifier(true);

        return service;
    }

    /**
     * Helper factory method to create Soap 1.1 client binding proxy
     *
     * @param params Connection parameters
     * @param clazz Soap interface to connect
     * @param <T> Soap service type to connect to
     * @return Instance of required Soap service implementation
     */
    public <T> T createJaxWsProxy(ProxyParamsDto params, Class<T> clazz) {
        return createJaxWsProxyInternal(params, clazz, false);
    }

    /**
     * Helper factory method to create Soap 1.2 client binding proxy
     *
     * @param params Connection parameters
     * @param clazz Soap interface to connect
     * @param <T> Soap service type to connect to
     * @return Instance of required Soap service implementation
     */
    public <T> T createJaxWsProxySoap12(ProxyParamsDto params, Class<T> clazz) {
        return createJaxWsProxyInternal(params, clazz, true);
    }
}
