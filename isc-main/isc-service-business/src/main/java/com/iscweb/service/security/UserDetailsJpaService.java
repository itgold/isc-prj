package com.iscweb.service.security;

import com.iscweb.common.exception.ResourceNotFoundException;
import com.iscweb.common.util.UserUtils;
import com.iscweb.persistence.model.jpa.UserJpa;
import com.iscweb.service.entity.user.UserServiceHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * An implementation of {@link UserDetailsService} which is loaded by default and provides
 * user details based on login information.
 */
@Slf4j
@Primary
@Component
@Qualifier("userDetailsService")
public class UserDetailsJpaService implements UserDetailsService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UserServiceHelper userServiceHelper;

    /**
     * Retrieves the user associated with an email.
     *
     * @param userEmail user to load.
     * @return a new instance of user details.
     * @throws UsernameNotFoundException if user can't be found.
     */
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        log.info("Authorizing user {} ", userEmail);

        UserJpa user;
        try {
            user = getUserServiceHelper().findUser(userEmail);
        } catch (ResourceNotFoundException ex) {
            log.warn("Unable to find user {} - Authorization failed", userEmail);
            throw new UsernameNotFoundException(userEmail);
        }

        if (!user.isActive()) {
            log.info("User {} has been deactivated and is not authorized to login to Loop", userEmail);
//            getSendGridService().sendDeactivationReminderEmail(user);
            throw new UsernameNotFoundException("User is deactivated and not authorized to login");
        }

        return new IscPrincipal(user.getEmail(),
                user.getPassword(),
                user.getId(),
                UserUtils.grantedAuthoritiesFor(user));
    }
}
