package com.iscweb.common.sis.impl;

import com.iscweb.common.sis.ISisService;
import com.iscweb.common.sis.ISisServiceFactory;
import com.iscweb.common.sis.ISisServiceMapper;
import com.iscweb.common.sis.ISisServiceTransport;
import com.iscweb.common.sis.ITransportFactory;
import com.iscweb.common.sis.model.SisMethodMetadata;
import com.iscweb.common.sis.model.SisResponseMessageDto;
import com.iscweb.common.sis.model.SisServiceMetadata;
import com.iscweb.common.sis.utils.MetadataIntrospector;
import lombok.Getter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of <code>ISisServiceFactory</code> for third party API client code generation.
 * Details of payload generation and transferring data to the third party API endpoints are abstracted out
 * with provides implementations of <code>ISisServiceMapper</code> and <code>ITransportFactory</code> correspondingly.
 *
 * @param <T> Integration API definition interface.
 */
public class SisServiceFactory<T extends ISisService> implements ISisServiceFactory<T> {

    private static final String INSTANCE_HASH_CODE = "hashCode";
    private static final String INSTANCE_EQUALS = "equals";

    private final ITransportFactory transportFactory;
    private final ISisServiceMapper mapper;

    public SisServiceFactory(final ITransportFactory transportFactory, final ISisServiceMapper mapper) {
        this.transportFactory = transportFactory;
        this.mapper = mapper;
    }

    @SuppressWarnings("unchecked") //proxy class cast
    public T build(Class<T> clazz) {
        final ProxyImpl<T> proxy = new ProxyImpl<>(clazz);
        return (T) Proxy.newProxyInstance(SisServiceFactory.class.getClassLoader(), new Class<?>[] { clazz }, proxy);
    }

    protected class ProxyImpl<S extends ISisService> implements InvocationHandler {

        @Getter
        private final SisServiceMetadata metadata;

        @Getter
        private final Map<String, SisMethodMetadata> methods = new ConcurrentHashMap<>();

        public ProxyImpl(Class<S> clazz) {
            this.metadata = initializeProxy(clazz);
            this.metadata.getMethods().forEach(method -> methods.put(method.getKey(), method));
        }

        protected SisServiceMetadata initializeProxy(Class<S> clazz) {
            return MetadataIntrospector.inspect(clazz);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = null;

            // We need to implement hashCode/equals because of Spring caching all beans in internal map
            if (INSTANCE_HASH_CODE.equals(method.getName())) {

                result = metadata.getServiceName().hashCode();

            } else if (INSTANCE_EQUALS.equals(method.getName())) {

                ProxyImpl<ISisService> proxyBean = (ProxyImpl<ISisService>) args[0];
                result = metadata.getServiceName().equals(proxyBean.getMetadata().getServiceName());

            } else {

                final String methodKey = MetadataIntrospector.generateMethodKey(method);
                final SisMethodMetadata methodMetadata = methods.get(methodKey);

                String payload = mapper.writeAsString(methodMetadata, args);
                // ISisServiceTransport must we auto-closed to return transport into pool
                try (ISisServiceTransport transport = transportFactory.getConnection()) {
                    final SisResponseMessageDto response = transport.send(payload);
                    if (response != null) {
                        //todo: handle binary responses here
                        result = mapper.readFromString(methodMetadata, response.toString());
                    }
                }

            }

            return result;
        }
    }
}
