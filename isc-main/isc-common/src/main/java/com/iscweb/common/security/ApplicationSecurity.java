package com.iscweb.common.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.Semaphore;

/**
 * Application security tools and constants.
 */
@Slf4j
public class ApplicationSecurity {

    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String SYSTEM_USER = "system@iscweb.io";

    /**
     * Constants which define ISC's security authorities.
     * <p>
     * ROLE_SYSTEM_ADMINISTRATOR = TBD: Define the role scope<br/>
     * ROLE_DISTRICT_ADMINISTRATOR = TBD: Define the role scope<br/>
     * ROLE_ANALYST = TBD: Define the role scope<br/>
     * ROLE_GUARD = TBD: Define the role scope<br/>
     * ROLE_GUEST = TBD: Define the role scope<br/>
     */
    public static final String ROLE_SYSTEM_ADMINISTRATOR = "ROLE_SYSTEM_ADMINISTRATOR";
    public static final String ROLE_DISTRICT_ADMINISTRATOR = "ROLE_DISTRICT_ADMINISTRATOR";
    public static final String ROLE_ANALYST = "ROLE_ANALYST";
    public static final String ROLE_GUARD = "ROLE_GUARD";
    public static final String ROLE_GUEST = "ROLE_GUEST";
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * Common security constraints used by @PreAuthorize annotation.
     */
    public static final String IS_AUTHENTICATED = "isAuthenticated()";
    public static final String PERMIT_ALL = "permitAll()";
    public static final String ADMINISTRATORS_PERMISSION = "hasAnyAuthority('"
            + ROLE_SYSTEM_ADMINISTRATOR + "', '" + ROLE_DISTRICT_ADMINISTRATOR + "')";
    public static final String MAP_ACTION_PERMISSION = "hasAnyAuthority('"
            + ROLE_SYSTEM_ADMINISTRATOR + "', '" + ROLE_ANALYST + "')";

    /**
     * This is the global scope mutex lock for system user execution (that is, when the application
     * is running a process that was not requested by a particular user, such as a scheduled task).
     * Multiple system users are not allowed to be executed simultaneously, so this only allows one
     * permit at a time.
     */
    private static final Semaphore SYSTEM_USER_MUTEX = new Semaphore(1);

    /**
     * Initializes application security context with given authentication.
     *
     * @param authentication object instance.
     * @throws AccessDeniedException if operation failed.
     */
    public static void initSecurityContext(Authentication authentication) throws AccessDeniedException {
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("User's {} security context initialized", authentication.getPrincipal());
        } else {
            throw new AccessDeniedException("Authentication object is null. Authorization failed.");
        }
    }

    /**
     * Factory method for ISystemUser implementation.
     * Enables system level security permissions for the try-with-resource block that executes it.
     * It should be used for cases where permissions need to be escalated or when there is no security
     * context at all, such as in @Async or @Scheduled services.
     * <pre>
     * Example:
     * {@code
     * try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
     *     getMyService().doOperationAsSystemUser();
     * } catch (ServiceException e) {
     *     //catch errors
     * }
     * }
     * </pre>
     *
     * @return IRunAsSystem implementation.
     */
    public static ISystemUser systemUserLogin() {
        return systemUserLogin(true);
    }

    /**
     * Factory method for ISystemUser implementation.
     * Enables system level security permissions for the try-with-resource block that executes it.
     * It should be used for cases where permissions need to be escalated or when there is no security
     * context at all, such as in @Async or @Scheduled services.
     * <pre>
     * Example:
     * {@code
     * try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
     *     getMyService().doOperationAsSystemUser();
     * } catch (ServiceException e) {
     *     //catch errors
     * }
     * }
     * </pre>
     *
     * @return IRunAsSystem implementation.
     */
    public static ISystemUser systemUserLogin(boolean exclusive) {
        return new SystemUser(exclusive);
    }

    /**
     * This class is used to authenticate the currently executing thread with the system user.
     * It is used to perform operations on services where authentication is required but there
     * is no user involved in the interaction.
     * <p>
     * It should be used in a try-with-resource block as it is possible to close the security
     * context once business operation is complete.
     */
    private static class SystemUser implements ISystemUser {

        public static final String SYSTEM_USER = "Frodo Baggins";
        private static final String EXTREMELY_CHALLENGING_PASSWORD = "heyLookAtThisStrongPassword";

        private final boolean exclusive;

        /**
         * Preserved original security context.
         */
        @Getter
        @Setter
        private SecurityContext originalContext;

        /**
         * Default constructor that logs in system user to current security context.
         */
        private SystemUser(boolean exclusive) {
            this.exclusive = exclusive;

            if (this.exclusive) {
                try {
                    SYSTEM_USER_MUTEX.acquire();
                } catch (InterruptedException e) {
                    throw new IllegalStateException("System user mutex has been interrupted", e);
                }
            }

            setOriginalContext(SecurityContextHolder.getContext());

            Object principal;
            if (getOriginalContext().getAuthentication() != null && getOriginalContext().getAuthentication().getPrincipal() != null) {
                principal = getOriginalContext().getAuthentication().getPrincipal();
            } else {
                log.debug("Original context {} doesn't have an authentication object - defaulting to system user", getOriginalContext());
                principal = SYSTEM_USER;
            }

            SecurityContext newContext = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken newAuthentication =
                    new UsernamePasswordAuthenticationToken(principal,
                                                            EXTREMELY_CHALLENGING_PASSWORD,
                                                            AuthorityUtils.createAuthorityList(ROLE_SYSTEM_ADMINISTRATOR));
            newContext.setAuthentication(newAuthentication);
            SecurityContextHolder.setContext(newContext);
            log.debug("Enabling execution of subsequent calls with a system privileged context");
        }

        /**
         * This cleans up the security context. If the original context has been preserved, it would be
         * restored at the end of the operation.
         */
        @Override
        public void close() {
            if (getOriginalContext() != null) {
                final Authentication authentication = getOriginalContext().getAuthentication();
                if (authentication != null) {
                    log.debug("Restoring original security context after executing with elevated privileges: {}",
                              authentication.getName());
                } else {
                    log.debug("Restoring original security context after executing with elevated privileges: empty authentication");
                }
                SecurityContextHolder.setContext(getOriginalContext());
            } else {
                SecurityContextHolder.clearContext();
            }
            setOriginalContext(null);
            log.debug("System privileged context execution finished");

            if (this.exclusive) {
                SYSTEM_USER_MUTEX.release();
            }
        }
    }
}
