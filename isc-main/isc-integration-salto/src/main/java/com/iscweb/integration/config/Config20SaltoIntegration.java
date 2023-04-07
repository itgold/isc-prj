package com.iscweb.integration.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.integration.IUserSyncService;
import com.iscweb.common.service.integration.door.IDoorActionsService;
import com.iscweb.common.service.integration.door.IDoorSyncService;
import com.iscweb.common.sis.ITransportFactory;
import com.iscweb.integration.doors.IEventStreamListener;
import com.iscweb.integration.doors.ISaltoSisService;
import com.iscweb.integration.doors.SaltoApiFactory;
import com.iscweb.integration.doors.SaltoDoorActionsService;
import com.iscweb.integration.doors.SaltoDoorSyncService;
import com.iscweb.integration.doors.SaltoUserSyncService;
import com.iscweb.integration.doors.service.SaltoEventStreamListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Slf4j
@Order(20)
@Configuration
@Profile("salto")
@ComponentScan(basePackages = {"com.iscweb.integration.doors.service"})
public class Config20SaltoIntegration {

    @Bean("SaltoTransport")
    public ITransportFactory getSaltoTransportFactory(@Value("${integration.doors.salto.server.host:#{null}}") final String saltoServerHost,
                                                      @Value("${integration.doors.salto.server.port:#{9100}}") final int saltoServerPort,
                                                      @Value("${integration.doors.salto.server.poolSize:#{10}}") int connectionPoolSize,
                                                      @Value("${integration.doors.salto.server.readTimeout:#{1000}}") int readTimeout,
                                                      @Value("${integration.doors.salto.server.connectionTimeout:#{3000}}") long connectionTimeout) {
        return new SaltoApiFactory.TransportBuilder()
                .poolSize(connectionPoolSize)
                .connectionTimeout(connectionTimeout)
                .readTimeout(readTimeout)
                .host(saltoServerHost)
                .port(saltoServerPort)
                .build();
    }

    @Bean("SaltoEventListener")
    public IEventStreamListener getSaltoEventStreamListener(@Value("${integration.doors.salto.events.listener.port:#{10000}}") final int port,
                                                            IEventHub eventHub,
                                                            ObjectMapper objectMapper) {
        return SaltoEventStreamListener.Builder()
                .port(port)
                .eventHub(eventHub)
                .objectMapper(objectMapper)
                .build();
    }

    @Bean
    public ISaltoSisService getSaltoService(@Qualifier("SaltoTransport") ITransportFactory transportFactory,
                                            @Qualifier("SaltoEventListener") IEventStreamListener eventStreamListener) {
        return new SaltoApiFactory.ServiceBuilder()
                .transportFactory(transportFactory)
                .eventStreamListener(eventStreamListener)
                .build();
    }

    @Bean("SaltoDoorSyncService")
    public IDoorSyncService getSaltoDoorSyncService(ISaltoSisService saltoService,
                                                    IEventHub eventHub) {
        return new SaltoDoorSyncService(saltoService, eventHub);
    }

    @Bean("SaltoUserSyncService")
    public IUserSyncService getSaltoUserSyncService(ISaltoSisService saltoService, IEventHub eventHub) {
        return new SaltoUserSyncService(saltoService, eventHub);
    }

    @Bean
    public IDoorActionsService getDoorService(ISaltoSisService saltoService) {
        return new SaltoDoorActionsService(saltoService);
    }
}
