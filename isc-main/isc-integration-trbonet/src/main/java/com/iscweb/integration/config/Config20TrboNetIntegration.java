package com.iscweb.integration.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.integration.IUserSyncService;
import com.iscweb.common.service.integration.radio.IRadioSyncService;
import com.iscweb.integration.doors.IEventStreamListener;
import com.iscweb.integration.radios.service.TrboNetEventStreamListener;
import com.iscweb.integration.radios.service.TrboNetService;
import com.iscweb.integration.radios.service.TrboNetRadioSyncService;
import com.iscweb.integration.radios.service.TrboNetUserSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Order(20)
@Configuration
@Profile("trbonet")
@ComponentScan(basePackages = {"com.iscweb.integration.radios.service"})
public class Config20TrboNetIntegration {

    @Bean("TrboNetEventListener")
    public IEventStreamListener getTrboNetEventStreamListener(@Value("${integration.radios.trbonet.events.listener.port:#{10001}}") final int port,
                                                            IEventHub eventHub,
                                                            ObjectMapper objectMapper) {
        return TrboNetEventStreamListener.Builder()
                .port(port)
                .eventHub(eventHub)
                .objectMapper(objectMapper)
                .build();
    }

    @Bean("TrbonetRadioSyncService")
    public IRadioSyncService getTrbonetRadioSyncService(TrboNetService trboNetService,
                                                        IEventHub eventHub) {
        return new TrboNetRadioSyncService(trboNetService, eventHub);
    }

    @Bean("TrbonetUserSyncService")
    public IUserSyncService getTrbonetUserSyncService(TrboNetService trboNetService, IEventHub eventHub) {
        return new TrboNetUserSyncService(trboNetService, eventHub);
    }

    @Bean("TrbonetClient")
    public RestTemplate restClientTemplate(@Value("${integration.radios.trbonet.server.connectionTimeout}") int connectionTimeout,
                                           @Value("${integration.radios.trbonet.server.readTimeout}") int readTimeout) {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(connectionTimeout);
        httpRequestFactory.setConnectTimeout(connectionTimeout);
        httpRequestFactory.setReadTimeout(readTimeout);
        return new RestTemplate(httpRequestFactory);
    }
}
