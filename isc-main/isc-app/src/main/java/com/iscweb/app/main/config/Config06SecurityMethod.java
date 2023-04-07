package com.iscweb.app.main.config;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;
import java.util.Collection;

/**
 * This configuration is responsible for initializing method level application security.
 */
@Slf4j
@Order(6)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Config06SecurityMethod extends GlobalMethodSecurityConfiguration {

    /**
     * Initializing application's default decision manager.
     * We use PreInvocationAuthorizationAdviceVoter that is used
     * by @PreAuthorize annotation for spring EL permissions checking.
     *
     * @return reconfigured {@link AccessDecisionManager}
     */
    @Override
    public AccessDecisionManager accessDecisionManager() {
        final AccessDecisionManager manager = super.accessDecisionManager();
        return new AccessDecisionManager() {

            @Override
            public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
                try {
                    manager.decide(authentication, object, configAttributes);
                } catch (AccessDeniedException e) {
                    String targetObject = "Access Denied: ";
                    if (object instanceof MethodInvocation) {
                        MethodInvocation methodInvocation = (MethodInvocation) object;
                        targetObject += methodInvocation.getMethod().toString();
                    } else {
                        targetObject += object.toString();
                    }
                    log.error(targetObject);
                    throw e;
                }
            }

            @Override
            public boolean supports(ConfigAttribute attribute) {
                return manager.supports(attribute);
            }

            @Override
            public boolean supports(Class<?> clazz) {
                return manager.supports(clazz);
            }
        };
    }
}
