package com.iscweb.service.entity.user;

import com.iscweb.common.exception.ResourceNotFoundException;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.entity.core.UserDto;
import com.iscweb.common.model.entity.IUser;
import com.iscweb.common.service.IApplicationService;
import com.iscweb.common.util.SecurityUtils;
import com.iscweb.common.util.UserUtils;
import com.iscweb.persistence.model.jpa.UserJpa;
import com.iscweb.persistence.repositories.impl.UserJpaRepository;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.iscweb.common.security.ApplicationSecurity.ADMINISTRATORS_PERMISSION;
import static com.iscweb.common.security.ApplicationSecurity.PERMIT_ALL;
import static com.iscweb.common.util.ObjectUtils.ID_NONE;

/**
 * A helper to access certain {@link UserEntityService} functionality.
 */
@Slf4j
@Service
public class UserServiceHelper implements IApplicationService, IUserServiceHelper {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UserJpaRepository repository;


    /**
     * An explicitly insecure.method for finding users.
     *
     * @see UserEntityService#findUser(String)
     */
    @Override
    @Cacheable(cacheNames = "users", key = "#p0")
    public UserJpa findUser(String userEmail) throws ResourceNotFoundException {
        UserJpa result = findUserNoCache(userEmail, false);
        if (result == null) {
            throw new ResourceNotFoundException(String.format("User %s not found", userEmail));
        }
        return result;
    }

    /**
     * Non-cacheable version of findByPrincipalName method.
     * Call to query db directly, with no cache involved.
     *
     * @param name principal name.
     * @param sanitize if the result needs to be sanitized.
     * @return user jpa if found.
     */
    @SuppressWarnings("unchecked")
    @PreAuthorize(PERMIT_ALL)
    public <U extends IUser> U findUserNoCache(String name, boolean sanitize) {
        UserJpa user = getRepository().findByEmailIgnoreCase(name);
        UserJpa result;
        // TODO(serge_ku): Review.
        if (sanitize) {
            UserDto dto = Convert.my(user).scope(Scope.METADATA).boom();
            result = Convert.my(dto).scope(Scope.ALL).boom();
        } else {
            result = user;
        }
        return (U) result;
    }

    @Caching(evict = {
            @CacheEvict(value = "users", key = "#p0.guid", beforeInvocation = true),
            @CacheEvict(value = "users", key = "#p0.email", beforeInvocation = true)
    })
    public void refreshUserCache(UserJpa user) {
        // clean up user cache - no method body required
    }

    /**
     * Deletes user with a given id and removes from company repo.
     * Disallow current user deletion.
     * Disallow company admin users from deleting a user from a different company.
     * <p>
     * We attempt to update the company and then attempt to delete the actual user so if something goes
     * wrong we don't have an inconsistency between companies and users.
     *
     * @param userGuid user's guid to delete.
     * @return deleted true if succeeded
     * @throws ServiceException if can't perform operation.
     */
    @Transactional(transactionManager = "jpaTransactionManager", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize(ADMINISTRATORS_PERMISSION)
    public boolean deleteUser(String userGuid) throws ServiceException {
        boolean result;
        UserJpa user = getRepository().findByGuid(userGuid);

        String principalName = SecurityUtils.currentPrincipal();
        UserJpa principal = getRepository().findByEmailIgnoreCase(principalName);

        if (user != null) {
            if (user.getSchoolDistrict() != null && user.getSchoolDistrict() != principal.getSchoolDistrict()
                    && user.getSchoolDistrict().getId() != ID_NONE) {
                log.error("Company Admin user cannot delete user from a different company.");
                throw new ServiceException("Company Admin user cannot delete user from a different company.");
            }
            if (user.getEmail().equalsIgnoreCase(principal.getEmail())) {
                log.error("Users cannot be null or delete own accounts.");
                throw new ServiceException("Users cannot be null or delete own accounts.");
            }

            getRepository().delete(user);

            result = true;
            log.info("User {} deleted", user.getEmail());
        } else {
            result = false;
            log.info("User {} not found", userGuid);
        }

        return result;
    }

    /**
     * Deletes objects that depend on a user.
     *
     * @param userGuid user's global id
     * @param principalName user's principal
     */
    @Transactional(transactionManager = "jpaTransactionManager", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize(ADMINISTRATORS_PERMISSION)
    public UserJpa cleanUserObjects(String userGuid, String principalName) throws ServiceException {

        UserJpa user = getRepository().findByGuid(userGuid);
        UserJpa principal = getRepository().findByEmailIgnoreCase(principalName);

        if (user != null) {
            if (!UserUtils.hasAdminRole(principal) && user.getSchoolDistrict() != principal.getSchoolDistrict()) {
                log.error("Company Admin user cannot delete user from a different company.");
                throw new ServiceException("Company Admin user cannot delete user from a different company.");
            }
            if (user.getEmail().equalsIgnoreCase(principal.getEmail())) {
                log.error("Users cannot be null or delete own accounts.");
                throw new ServiceException("Users cannot be null or delete own accounts.");
            }
        }

        return user;
    }
}
