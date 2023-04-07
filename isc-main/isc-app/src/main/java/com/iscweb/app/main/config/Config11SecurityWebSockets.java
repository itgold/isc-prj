package com.iscweb.app.main.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@Profile("web")
public class Config11SecurityWebSockets extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected boolean sameOriginDisabled() {
        return true; // this is to disable CSRF token
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        /*
        messages
                // Any message without a destination (i.e. anything other that Message type of MESSAGE or SUBSCRIBE) will require the user to be authenticated
                .nullDestMatcher()
                    .authenticated()
                // Any message that has a destination starting with "/rest/channel/" will be require the user to have the role Administrator
                .simpDestMatchers("/rest/channel/**")
                    .hasRole(ApplicationSecurity.ADMINISTRATOR)
                // Anyone can subscribe to /user/queue/errors
                .simpSubscribeDestMatchers("/queue/errors")
                    .permitAll()
                // Any message that starts with "/queue/" or "/topic/" that is of type SUBSCRIBE will require Administrator
//                .simpSubscribeDestMatchers("/queue/**", "/topic/**")
//                    .hasRole(ApplicationSecurity.ADMINISTRATOR)
                .anyMessage()
                    .denyAll();
        */
        messages.anyMessage().authenticated();
    }
}
