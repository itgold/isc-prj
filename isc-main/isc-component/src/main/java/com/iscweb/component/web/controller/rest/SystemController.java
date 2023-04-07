package com.iscweb.component.web.controller.rest;

import com.iscweb.component.web.controller.BaseInternalApiController;
import com.iscweb.service.SystemService;
import com.iscweb.service.utils.GitMeta;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collections;

@Slf4j
@RestController
@Api("REST Controller for generic operational functions")
public class SystemController extends BaseInternalApiController<SystemService> {

    @Getter
    @Value("${server.session.timeout:#{1860}}")
    private Integer sessionTimeout;

    @Getter
    @Value("${isc.server.simulator.address:isc-simulator}")
    private String simulatorAddress;

    @Getter
    @Value("${isc.server.simulator.port:9091}")
    private Integer simulatorPort;

    @GetMapping(value = "/meta")
    public GitMeta getServerVersion() {
        return getService().getGitMeta();
    }

    @GetMapping(value = "/env")
    public String getEnvironment() {
        return getService().getEnvironment();
    }

    /**
     * User details handler.
     */
    @RequestMapping(value = {"/user"})
    @ResponseBody
    public Principal user(Principal principal) {
        return principal;
    }

    /**
     * The intention of this REST endpoint is to:
     * 1. Be an unsecured "heartbeat" endpoint (needs to be as lightweight as possible).
     * 2. Be used before making "login" REST call to make sure browser has correct CSRF cookie.
     *
     * @return Always returns "alive"
     */
    @GetMapping(value = "/status")
    public String status() {
        return "alive";
    }

    @RequestMapping({"/session-timeout"})
    public Integer getServerSessionTimeout() {
        return getSessionTimeout();
    }

    @RequestMapping(value = "/simulate/random", method = RequestMethod.POST)
    public String getSimulator(@RequestBody String body, HttpMethod method, HttpServletRequest request) throws URISyntaxException {
        URI uri = new URI("http", null, this.simulatorAddress, this.simulatorPort, "/simulate/salto/random", null, null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(uri, method, new HttpEntity<>(body, headers), String.class);

        return responseEntity.getBody();
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
