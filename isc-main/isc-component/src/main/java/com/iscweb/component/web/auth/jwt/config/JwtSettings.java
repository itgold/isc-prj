package com.iscweb.component.web.auth.jwt.config;

import com.iscweb.component.web.auth.jwt.model.token.IJwtToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "isc.security.jwt")
public class JwtSettings {

    /**
     * {@link IJwtToken} will expire after this time.
     */
    @Getter
    @Setter
    private Integer tokenExpirationTime;

    /**
     * Token issuer.
     */
    @Getter
    @Setter
    private String tokenIssuer;

    /**
     * Key used to sign {@link IJwtToken}.
     */
    @Getter
    @Setter
    private String tokenSigningKey;

    /**
     * {@link IJwtToken} can be refreshed during this timeframe.
     */
    @Getter
    @Setter
    private Integer refreshTokenExpTime;
}
