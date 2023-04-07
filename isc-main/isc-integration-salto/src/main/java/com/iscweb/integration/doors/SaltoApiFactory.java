package com.iscweb.integration.doors;

import com.iscweb.integration.doors.exceptions.SaltoConfigurationException;
import com.iscweb.common.sis.ISisServiceFactory;
import com.iscweb.common.sis.ISisServiceMapper;
import com.iscweb.common.sis.ISisServiceTransport;
import com.iscweb.common.sis.ITransportFactory;
import com.iscweb.common.sis.impl.SisServiceFactory;
import com.iscweb.common.sis.impl.tcp.BaseTcpConnectionPoolFactory;
import com.iscweb.common.sis.impl.tcp.TcpConnectionPoolConfig;
import com.iscweb.common.util.ValidationUtils;

import java.net.SocketException;

public final class SaltoApiFactory {

    public static class TransportBuilder {

        private String serverHost;
        private int serverPort;
        private int connectionPoolSize;
        private int readTimeout;
        private long connectionTimeout;

        public TransportBuilder host(String hostname) {
            this.serverHost = hostname;
            return this;
        }

        public TransportBuilder port(int port) {
            this.serverPort = port;
            return this;
        }

        public TransportBuilder poolSize(int poolSize) {
            this.connectionPoolSize = poolSize;
            return this;
        }

        public TransportBuilder readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public TransportBuilder connectionTimeout(long connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public ITransportFactory build() {
            ValidationUtils.isNotEmpty(serverHost, "Invalid 'salto.server.host' configuration value");

            final TcpConnectionPoolConfig config = new TcpConnectionPoolConfig();
            config.setMaxTotal(connectionPoolSize);
            config.setServerHostname(serverHost);
            config.setServerPort(serverPort);
            // Socket read timeout.
            config.setReadTimeout(readTimeout);
            // When the connection pool resource is exhausted, the caller will block for the maximum time
            // and the timeout will run out of exception.
            config.setMaxWaitMillis(connectionTimeout);

            return new BaseTcpConnectionPoolFactory(config) {

                @Override
                protected ISisServiceTransport createTransport() {
                    return SaltoSisServiceTransport.buildWith(getConfig());
                }
            };
        }
    }

    public static class ServiceBuilder {

        private ITransportFactory transportFactory;
        private IEventStreamListener eventStreamListener;

        public ServiceBuilder transportFactory(ITransportFactory transportFactory) {
            this.transportFactory = transportFactory;
            return this;
        }

        public ServiceBuilder eventStreamListener(IEventStreamListener eventStreamListener) {
            this.eventStreamListener = eventStreamListener;
            return this;
        }

        public ISaltoSisService build() {
            final ISisServiceMapper mapper = new SaltoSisServiceMapper();
            final ISisServiceFactory<ISaltoSisService> factory = new SisServiceFactory<>(transportFactory, mapper);
            final ISaltoSisService service = factory.build(ISaltoSisService.class);

            if (eventStreamListener != null) {
                try {
                    eventStreamListener.startListener();
                } catch (SocketException e) {
                    throw new SaltoConfigurationException("Unable to start Salto listener.", e);
                }
            }

            return service;
        }
    }
}
