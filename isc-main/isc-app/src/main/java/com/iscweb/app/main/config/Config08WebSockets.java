package com.iscweb.app.main.config;

import com.iscweb.common.exception.util.ErrorCode;
import com.iscweb.component.web.auth.SecurityConstants;
import com.iscweb.component.web.auth.jwt.JwtAuthenticationProvider;
import com.iscweb.component.web.auth.jwt.exceptions.InvalidJwtToken;
import com.iscweb.component.web.auth.jwt.extractor.ITokenExtractor;
import com.iscweb.component.web.auth.jwt.model.token.JwtAuthenticationToken;
import com.iscweb.component.web.auth.jwt.model.token.RawAccessJwtToken;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
// import org.springframework.web.socket.server.standard.UndertowRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * This config implements authentication logic for the WS connections.
 * Every WS message has to be signed by JWT token and it is getting
 * validated by this config.
 */
@Slf4j
@Configuration
@Profile("web")
@EnableWebSocketMessageBroker
public class Config08WebSockets extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

    // how often to send heartbeat messages to the client
    private static final long SERVER_HEARTBEAT_PERIOD = 10000;
    // client read timeout will be 120 seconds. Default multiplier is 3
    // @see SimpleBrokerMessageHandler::SessionInfo::HEARTBEAT_MULTIPLIER
    // I.e. the server will close the connection if there will be more then 10 missed
    // heartbeat messages from the client side
    private static final long CLIENT_HEARTBEAT_PERIOD = 40000;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private JwtAuthenticationProvider authenticationProvider;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ITokenExtractor tokenExtractor;

    /**
     * @see WebSocketMessageBrokerConfigurer#configureClientInboundChannel(ChannelRegistration)
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {

        registration.interceptors(new ChannelInterceptor() {

            /**
             * @see ChannelInterceptor#preSend(Message, MessageChannel)
             */
            @Override
            @SuppressWarnings({"StatementWithEmptyBody", "NullableProblems"})
            public Message<?> preSend(Message<?> result, MessageChannel channel) {

                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(result, StompHeaderAccessor.class);

                if (accessor != null) {
                    // if (true) throw new InvalidJwtToken();

                    if (accessor.isHeartbeat()) {
                        //this is a heartbeat, so just do nothing
                    } else {
                        List<String> tokenList = accessor.getNativeHeader(SecurityConstants.AUTHENTICATION_HEADER_NAME);
                        String token = !CollectionUtils.isEmpty(tokenList) ? tokenList.get(0) : null;

                        if (token != null) {
                            try {
                                RawAccessJwtToken jwtToken = new RawAccessJwtToken(getTokenExtractor().extract(token));
                                Authentication auth = getAuthenticationProvider().authenticate(new JwtAuthenticationToken(jwtToken));
                                accessor.setUser(auth);
                                log.debug("WebSocket {} authenticated for {}", result.getHeaders().get("simpMessageType"),  auth.getName());
                            } catch (Exception e) { //catching Exception because in case of any error we don't want to authenticate a user
                                throw new InvalidJwtToken();
                            }
                        }
                    }
                }

                return result;
            }
        });

        super.configureClientInboundChannel(registration);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        /*
         * The application destination prefix is an arbitrary prefix to
         * differentiate between messages that need to be routed to
         * message-handling methods for application level work vs messages to be
         * routed to the broker to broadcast to subscribed clients. After
         * application level work is finished the message can be routed to
         * broker for broadcasting.
         */
        config.setApplicationDestinationPrefixes("/rest/channel");

        /*
         * The list of destination prefixes provided in this are based on what
         * broker is getting used. In this case we will use in-memory broker
         * which doesn't have any such requirements. For the purpose of
         * maintaining convention the "/topic" and the "/queue" prefixes are
         * chosen. The convention dictates usage of "/topic" destination for
         * pub-sub model targeting many subscribers and the "/queue" destination
         * for point to point messaging.
         */
        config
                .enableSimpleBroker("/topic", "/queue")
                .setHeartbeatValue(new long[] {SERVER_HEARTBEAT_PERIOD, CLIENT_HEARTBEAT_PERIOD})
                .setTaskScheduler(getTaskScheduler());

    }

    @Bean("wsTaskScheduler")
    public TaskScheduler getTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        threadPoolTaskScheduler.setThreadNamePrefix("ISC_WS");
        return threadPoolTaskScheduler;
    }

    @Override
    public void configureStompEndpoints(StompEndpointRegistry registry) {
        /*
         * Override may entry point URL which will be connected by UI web socket and mark it to use SockJS to
         * support failover channels when web socket protocol is not available by some reason.
         */
        registry
                .setErrorHandler(new StompSubProtocolErrorHandler() {
                    @Override
                    @Nonnull
                    protected Message<byte[]> handleInternal(StompHeaderAccessor errorHeaderAccessor,
                                                             byte[] errorPayload,
                                                             @Nullable Throwable cause,
                                                             @Nullable StompHeaderAccessor clientHeaderAccessor) {
                        Message<byte[]> result;
                        if (cause != null && cause.getCause() != null &&
                                (cause.getCause() instanceof InvalidJwtToken || cause.getCause() instanceof AccessDeniedException)) {
                            String payload = ErrorCode.ERROR_CODE_UNAUTHORIZED.getCode();
                            result = MessageBuilder
                                    .createMessage(payload.getBytes(), errorHeaderAccessor.getMessageHeaders());
                        } else {
                            result = MessageBuilder.createMessage(errorPayload, errorHeaderAccessor.getMessageHeaders());
                        }
                        return result;
                    }
                })
                .addEndpoint(SecurityConstants.API_WS_ROOT_URL)
                .addInterceptors(new HttpHandshakeInterceptor())
                .setHandshakeHandler(new DefaultHandshakeHandler(
                        // new UndertowRequestUpgradeStrategy()
                        new TomcatRequestUpgradeStrategy()
                ))
                .withSockJS()
                .setSupressCors(true)
                .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.0/sockjs.min.js");

    }

    public class HttpHandshakeInterceptor implements HandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                       Map attributes) throws Exception {
            if (request instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                HttpSession session = servletRequest.getServletRequest().getSession();
                attributes.put("sessionId", session.getId());
                attributes.put("SPRING.SESSION.ID", session.getId());
            }
            return true;
        }

        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Exception ex) {
        }
    }

    /*
    @Bean
    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> deploymentCustomizer() {
        return factory -> {
            UndertowDeploymentInfoCustomizer customizer = deploymentInfo -> {
                WebSocketDeploymentInfo inf = new WebSocketDeploymentInfo();
                inf.setBuffers(new DefaultByteBufferPool(false, 1024));
                deploymentInfo.addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME, inf);
            };
            factory.addDeploymentInfoCustomizers(customizer);
        };
    }
     */

    /**
     * @see WebSocketMessageBrokerConfigurer#configureWebSocketTransport(WebSocketTransportRegistration)
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            @Nonnull
            public WebSocketHandler decorate(@Nonnull final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {

                    @Override
                    public void afterConnectionEstablished(@Nonnull final WebSocketSession session) throws Exception {
                        super.afterConnectionEstablished(session);

                        String username = session.getPrincipal() != null ? session.getPrincipal().getName() : "UNKNOWN";
                        log.debug("WS Session OPENED. User: " + username);
                    }

                    @Override
                    public void afterConnectionClosed(@Nonnull WebSocketSession session, @Nonnull CloseStatus closeStatus) throws Exception {
                        String username = session.getPrincipal() != null ? session.getPrincipal().getName() : "UNKNOWN";
                        try {
                            super.afterConnectionClosed(session, closeStatus);
                        } catch (AccessDeniedException e) {
                            log.debug("WS Session CLOSED - expired JWT token. User: " + username);
                        } catch (Exception e) {
                            if (e.getCause() != null && (e.getCause() instanceof InvalidJwtToken || e.getCause() instanceof AccessDeniedException)) {
                                log.debug("WS Session CLOSED - expired JWT token. User: " + username);
                            } else {
                                log.debug("WS Session CLOSED. User: " + username);
                                throw e;
                            }
                        }
                    }
                };
            }
        });

        super.configureWebSocketTransport(registration);
    }
}
