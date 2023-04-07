package com.iscweb.integration.radios.service;

import com.google.common.collect.Maps;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.IApplicationComponent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

/**
 * Helper component to manage REST calls to the TRBOnet integration service.
 */
@Slf4j
@Component
public class TrboNetRestService implements IApplicationComponent {

    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";

    @Value("${integration.radios.trbonet.server.secured}") boolean secure;
    @Value("${integration.radios.trbonet.server.address}") String address;
    @Value("${integration.radios.trbonet.server.port}") int port;

    @Value("${integration.radios.trbonet.server.auth}") boolean useAuthentication;
    @Value("${integration.radios.trbonet.server.username}") String username;
    @Value("${integration.radios.trbonet.server.password}") String password;

    private String serverUrl;

    @Getter
    @Setter(onMethod = @__({@Autowired, @Qualifier("TrbonetClient")}))
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        serverUrl = (secure ? HTTPS : HTTP) + address;
        if (port != 80 && port != 443) {
            serverUrl += ":" + port;
        }
        serverUrl += "%s";
    }

    public <RESPONSE> RESPONSE getJson(String endpoint, Class<RESPONSE> clazz) throws ServiceException {
        return processJsonRequest(endpoint, HttpMethod.GET, null, clazz, Maps.newHashMap());
    }
    public <RESPONSE> RESPONSE getJson(String endpoint, Class<RESPONSE> clazz, Map<String, Object> queryParams) throws ServiceException {
        return processJsonRequest(endpoint, HttpMethod.GET, null, clazz, queryParams);
    }

    public <RESPONSE, REQUEST> RESPONSE postJson(String endpoint, REQUEST payload, Class<RESPONSE> clazz) throws ServiceException {
        return processJsonRequest(endpoint, HttpMethod.POST, payload, clazz, Maps.newHashMap());
    }
    public <RESPONSE, REQUEST> RESPONSE postJson(String endpoint, REQUEST payload, Class<RESPONSE> clazz, Map<String, Object> queryParams) throws ServiceException {
        return processJsonRequest(endpoint, HttpMethod.POST, payload, clazz, queryParams);
    }

    protected <RESPONSE, REQUEST> RESPONSE processJsonRequest(String endpoint, HttpMethod method, REQUEST payload, Class<RESPONSE> clazz, Map<String, Object> queryParams) throws ServiceException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (useAuthentication) {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                headers.set("Authorization", authHeader);
            }

            ResponseEntity<RESPONSE> response = getRestTemplate().exchange(String.format(serverUrl, endpoint),
                    method,
                    payload != null ? new HttpEntity<>(payload, headers) : new HttpEntity<>(headers),
                    clazz, queryParams);
            HttpStatus statusCode = response.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                return response.getBody();
            }

            throw new ServiceException("Unable to make '" + method + "' request to REST endpoint: '" + endpoint + "'. Response error: " + statusCode);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
