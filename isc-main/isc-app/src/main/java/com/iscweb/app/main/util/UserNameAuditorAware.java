package com.iscweb.app.main.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
public class UserNameAuditorAware implements AuditorAware<String> {

    /**
     * @see AuditorAware#getCurrentAuditor()
     */
    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        Optional<String> result = Optional.empty();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.debug("UserNameAditorAware resolved authentication to -> " + authentication);

        if (authentication != null &&
                authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof UserDetails) {

            result = Optional.of(((UserDetails) authentication.getPrincipal()).getUsername());
        }

        return result;
    }

}
