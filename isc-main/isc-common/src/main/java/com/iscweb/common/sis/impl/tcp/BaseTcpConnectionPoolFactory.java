package com.iscweb.common.sis.impl.tcp;

import com.iscweb.common.sis.ISisServiceTransport;
import com.iscweb.common.sis.ITransportFactory;
import com.iscweb.common.sis.exceptions.SisConnectionException;
import com.iscweb.common.sis.model.SisResponseMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.IOException;

/**
 * Base abstract class to create Tcp/Ip based transports factories.
 */
@Slf4j
public abstract class BaseTcpConnectionPoolFactory implements ITransportFactory {

    private final TcpConnectionPoolConfig config;

    private final GenericObjectPool<ISisServiceTransport> pool;

    public BaseTcpConnectionPoolFactory(TcpConnectionPoolConfig config) {
        this.config = config;

        final ConnectionFactory objFactory = createConnectionFactory();
        this.pool = new GenericObjectPool<>(objFactory, config);
    }

    protected abstract ISisServiceTransport createTransport();

    protected TcpConnectionPoolConfig getConfig() {
        return config;
    }

    protected ConnectionFactory createConnectionFactory() {
        return new ConnectionFactory();
    }

    @Override
    public ISisServiceTransport getConnection() throws SisConnectionException {
        ISisServiceTransport result;
        try {
            result = pool.borrowObject();
        } catch (Exception e) {
            throw new SisConnectionException("Error getting connection from the pool", e);
        }

        return result;
    }

    public void shutdown() {
        pool.close();
    }

    protected class ConnectionFactory extends BasePooledObjectFactory<ISisServiceTransport> {

        @Override
        public ISisServiceTransport create() throws Exception {
            ISisServiceTransport transport = createTransport();
            transport.connect();
            return new SisServiceTransportWrapper(transport);
        }

        @Override
        public PooledObject<ISisServiceTransport> wrap(ISisServiceTransport transport) {
            return new DefaultPooledObject<>(transport);
        }

        @Override
        public boolean validateObject(final PooledObject<ISisServiceTransport> p) {
            return null != p.getObject() && p.getObject().isConnected();
        }

        @Override
        public void destroyObject(final PooledObject<ISisServiceTransport> p) throws Exception  {
            if (null != p.getObject() && p.getObject() instanceof SisServiceTransportWrapper) {
                SisServiceTransportWrapper wrapper = (SisServiceTransportWrapper) p.getObject();
                wrapper.destroyTransport();
            }
        }
    }

    protected class SisServiceTransportWrapper implements ISisServiceTransport {

        private final ISisServiceTransport transport;

        public SisServiceTransportWrapper(ISisServiceTransport transport) {
            this.transport = transport;
        }

        @Override
        public SisResponseMessageDto send(String payload) throws IOException {
            try {
                return this.transport.send(payload);
            } catch (IOException e) {
                try {
                    pool.invalidateObject(this);
                } catch (Exception ex) {
                    log.warn("Unable to destroy transport", e);
                }

                throw e;
            }
        }

        @Override
        public void connect() throws IOException {
            this.transport.connect();
        }

        @Override
        public boolean isConnected() {
            return this.transport.isConnected();
        }

        @Override
        public void close() {
            pool.returnObject(this);
        }

        public void destroyTransport() throws IOException {
            if (this.transport != null) {
                this.transport.close();
            }
        }
    }
}
