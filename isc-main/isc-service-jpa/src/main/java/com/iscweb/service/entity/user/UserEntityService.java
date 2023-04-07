package com.iscweb.service.entity.user;

import com.google.common.collect.Lists;
import com.iscweb.common.exception.InvalidOperationException;
import com.iscweb.common.exception.ResourceNotFoundException;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.exception.util.ErrorCode;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.BaseUserDto;
import com.iscweb.common.model.dto.entity.core.PublicUserMetadataDto;
import com.iscweb.common.model.dto.entity.core.UserDto;
import com.iscweb.common.model.dto.entity.core.UserSearchResultDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IUser;
import com.iscweb.common.model.metadata.UserStatus;
import com.iscweb.common.util.UserUtils;
import com.iscweb.persistence.model.jpa.SchoolDistrictJpa;
import com.iscweb.persistence.model.jpa.UserJpa;
import com.iscweb.persistence.repositories.impl.IndexJpaRepository;
import com.iscweb.persistence.repositories.impl.SchoolDistrictJpaRepository;
import com.iscweb.persistence.repositories.impl.UserJpaRepository;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.BaseJpaEntityService;
import com.iscweb.service.entity.EntityService;
import com.iscweb.service.entity.RoleEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.common.model.metadata.UserStatus.ACTIVATED;
import static com.iscweb.common.model.metadata.UserStatus.DEACTIVATED;
import static com.iscweb.common.security.ApplicationSecurity.ADMINISTRATORS_PERMISSION;
import static com.iscweb.common.util.ObjectUtils.ID_NONE;
import static com.iscweb.service.converter.Convert.GUID;

/**
 * A service for user operations.
 */
@Slf4j
@Service
public class UserEntityService extends BaseJpaEntityService<UserJpaRepository, IUser> implements IUserServiceHelper, EntityService<UserDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RoleEntityService roleEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SchoolDistrictJpaRepository schoolDistrictRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IndexJpaRepository indexRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UserServiceHelper helper;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private PasswordEncoder passwordEncoder;

    /**
     * Takes the {@link UserDto} passed and saves it into the repository (creating a new {@link UserJpa}0,
     * setting a new GUID, and password for it. When the user is saved, it gets converted back into DTO and
     * is returned to the caller.
     *
     * @param userDto {@link UserDto} with the user data (not on the repository)
     * @return {@link UserDto} representing the new user stored in the repository
     */
    public UserDto create(UserDto userDto) throws ServiceException {

        if (getRepository().findByEmailIgnoreCase(userDto.getEmail()) != null) {
            log.info("Unable to add user. Email {} already exists.", userDto.getEmail());
            throw new InvalidOperationException(String.format("Email %s is already registered", userDto.getEmail()));
        }

        UserJpa result = Convert.my(userDto)
                                .scope(Scope.ALL)
                                .attr(GUID, true)
                                .boom();
        ZonedDateTime timestamp = ZonedDateTime.now();
        result.setStatusDate(timestamp);
        if (!StringUtils.isEmpty(userDto.getPassword())) {
            result.setPassword(getPasswordEncoder().encode(userDto.getPassword()));
        }

        if (userDto.getSchoolDistrict() != null && !StringUtils.isEmpty(userDto.getSchoolDistrict().getId())) {
            result.setSchoolDistrict(getSchoolDistrictRepository().findByGuid(userDto.getSchoolDistrict().getId()));
        } else {
            result.setSchoolDistrict(getSchoolDistrictRepository().findById(ID_NONE).orElseThrow());
        }

        if (!CollectionUtils.isEmpty(userDto.getRoles())) {
            result.setRoles(userDto.getRoles().stream().map(roleDto -> roleEntityService.findByGuid(roleDto.getId())).collect(Collectors.toSet()));
        }

        result = this.createOrUpdate(result);
        return Convert.my(result).boom();
    }

    /**
     * Updates an existing user. The user is evicted from the user cache. The user cannot change
     * their email to match an existing email; in this case, an exception is raised.
     *
     * @return {@link UserDto} the updated user
     */
    @Caching(evict = {
            @CacheEvict(value = "users", key = "#p0.id", beforeInvocation = true),     // by guid (dto.id == jpa.guid)
            @CacheEvict(value = "users", key = "#p0.email", beforeInvocation = true)   // by email
    })
    public UserDto update(UserDto userDto) throws ServiceException {
        String guid = userDto.getId();
        UserJpa userJpa = getRepository().findByGuid(guid);
        if (userJpa == null) {
            throw new ServiceException(String.format("User %s cannot be found", guid));
        }

        String oldPassword = userJpa.getPassword();
        UserStatus oldStatus = userJpa.getStatus();

        if (!userDto.getEmail().equalsIgnoreCase(userJpa.getEmail())) {
            UserJpa userWithEmail = getRepository().findByEmailIgnoreCase(userDto.getEmail());
            if (userWithEmail != null && !userWithEmail.equals(userJpa)) {
                throw new InvalidOperationException(
                    String.format("Cannot update user. Email address %s is already in use", userDto.getEmail())
                );
            }
        }

        UserJpa result = Convert.my(userDto)
                                .withJpa(userJpa)
                                .scope(Scope.ALL).boom();

        ZonedDateTime timestamp = ZonedDateTime.now();
        if (userDto.getStatus() != null && userDto.getStatus() != oldStatus) {
            result.setStatusDate(timestamp);
            if (userDto.getStatus() == ACTIVATED) {
                result.setActivationDate(timestamp);
            }
        }
        if (!StringUtils.isEmpty(userDto.getPassword()) && !StringUtils.equals(userDto.getPassword(), oldPassword)) {
            result.setPassword(passwordEncoder.encode(userDto.getPassword()));
        } else {
            result.setPassword(oldPassword);
        }

        if (userDto.getSchoolDistrict() != null && userDto.getSchoolDistrict().getId() != null) {
            result.setSchoolDistrict(getSchoolDistrictRepository().findByGuid(userDto.getSchoolDistrict().getId()));
        } else {
            result.setSchoolDistrict(getSchoolDistrictRepository().findById(ID_NONE).orElseThrow());
        }

        if (!CollectionUtils.isEmpty(userDto.getRoles())) {
            result.setRoles(userDto.getRoles().stream().map(roleDto -> roleEntityService.findByGuid(roleDto.getId())).collect(Collectors.toSet()));
        }

        result = this.createOrUpdate(result);
        return Convert.my(result).boom();
    }

    @Caching(evict = {
            @CacheEvict(value = "users", key = "#p0.guid", beforeInvocation = true),
            @CacheEvict(value = "users", key = "#p0.email", beforeInvocation = true)
    })
    public IUser update(IUser user) {
        UserJpa result = (UserJpa) user;
        result = this.createOrUpdate(result);

        return result;
    }

    @Override
    // @CacheEvict(value = "users", key = "#p0", beforeInvocation = true) method not suppose to be called
    public void delete(String guid) {
        throw new IllegalStateException("Please use deleteUser method");
    }

    @Override
    // @CacheEvict(value = "users", allEntries = true, beforeInvocation = true) method not suppose to be called
    public void delete(Long entityId) {
        throw new IllegalStateException("Please use deleteUser method");
    }

    /**
     * Executes the user deletion logic. All objects connected to a user are deleted or detached,
     * then the user itself is removed.
     *
     * @param userGuid GUID of the user to be deleted.
     * @param principalEmail user requesting deletion (must have administrative permissions)
     * @return {@code true} when the user is successfully deleted
     * @throws ServiceException when deletion cannot be performed
     */
    @Caching(evict = {
            @CacheEvict(value = "users", key = "#p0", beforeInvocation = true),
            @CacheEvict(value = "users", key = "#p1", beforeInvocation = true)
    })
    public boolean deleteUser(String userGuid, String principalEmail) throws ServiceException {
        UserJpa user = getHelper().cleanUserObjects(userGuid, principalEmail);
        boolean rez = getHelper().deleteUser(userGuid);

        getHelper().refreshUserCache(user);
        return rez;
    }

    @Cacheable(cacheNames = "users", key = "#p0")
    public UserDto findByGuid(String guid) {
        UserJpa user = getRepository().findByGuid(guid);
        return Convert.my(user).scope(Scope.METADATA).boom();
    }

    /**
     * Finds a user by email; throws an exception if the user is not found.
     * The requested user is cached, and the JPA version is returned.
     *
     * @param userEmail user email
     * @return {@link UserJpa} JPA user
     */
    @Cacheable(cacheNames = "users", key = "#p0")
    public UserJpa findUser(String userEmail) throws ResourceNotFoundException {
        return getHelper().findUser(userEmail);
    }

    /**
     * Updates a user to either be deactivated or reactivated.
     *
     * @param userId guid of user to be updated
     * @param activated is user activated?
     * @return true if user is successfully updated
     */
    public UserDto updateUserActivation(Long userId, boolean activated) throws ResourceNotFoundException {
        UserJpa user = (UserJpa) findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException(String.format("User %s not found", userId));
        }
        if (activated) {
            if (user.getActivationDate() == null) {
                user.setActivationDate(ZonedDateTime.now());
            }
            user.setStatus(ACTIVATED);
        } else {
            user.setStatus(DEACTIVATED);
        }
        user.setStatusDate(ZonedDateTime.now());
        user = getRepository().save(user);
        getHelper().refreshUserCache(user);
        return Convert.my(user).scope(Scope.ALL).boom();
    }

    /**
     * Updates a user's alias; this should be unique, otherwise the call will fail.
     *
     * @return {@link UserDto} updated user
     */
    // TODO(serge_ku): Should this throw a ServiceException?
    @CacheEvict(value = "users", key = "#p0", allEntries = true, beforeInvocation = true)
    public UserDto updateUsername(String userEmail, String newEmail) {
        UserJpa user = getHelper().findUserNoCache(userEmail, false);
        if (user != null) {
            user.setEmail(newEmail);
            user = getRepository().save(user);
            getHelper().refreshUserCache(user);
        }
        return Convert.my(user).scope(Scope.ALL).boom();
    }

    /**
     * Fetches all users.
     */
    public List<UserDto> getAllUsers() {
        return getRepository().findAll()
                              .stream()
                              .map(user -> Convert.my(user).scope(Scope.ALL).<UserDto>boom())
                              .collect(Collectors.toList());
    }

    /**
     * Fetches all users from a given company. Company admins can see all details,
     * while regular users will see first / last name and email address
     *
     * @param userId current principal's id.
     */
    public List<? extends BaseUserDto> getAllCompanyUsers(Long userId) {
        List<BaseUserDto> result;
        UserJpa user = getRepository().findById(userId).orElse(null);
        if (user != null) {
            SchoolDistrictJpa schoolDistrict = user.getSchoolDistrict();
            result = getRepository().findBySchoolDistrict(schoolDistrict).stream()
                                    .map(u -> (BaseUserDto) Convert.my(u).scope(Scope.METADATA).<UserDto>boom())
                                    .collect(Collectors.toList());
        } else {
            result = Lists.newArrayList();
        }
        return result;
    }

    /**
     * Retrieves the provided user's object from db and converts it to a DTO.
     *
     * @param userEmail user's email.
     * @return user's dto.
     */
    public UserDto findUserDtoByPrincipalName(String userEmail) {
        return Convert.my(getHelper().<UserJpa>findUserNoCache(userEmail, false)).boom();
    }

    public UserDto getSanitizedUser(String userGuid) {
        log.debug("Getting user info -> " + userGuid);
        return Convert.my(getRepository().findByGuid(userGuid)).scope(Scope.METADATA).boom();
    }

    public UserJpa getUserByGuid(String userGuid) {
        log.debug("Getting user info -> " + userGuid);
        return getRepository().findByGuid(userGuid);
    }

    @PreAuthorize(ADMINISTRATORS_PERMISSION)
    public UserDto getSanitizedUser(String userGuid, String principalName) throws ServiceException {
        log.debug("Getting user info -> " + userGuid);
        UserJpa user = getRepository().findByGuid(userGuid);
        UserJpa principal = getRepository().findByEmailIgnoreCase(principalName);
        if (principal != null && user != null && principal.getSchoolDistrict() != user.getSchoolDistrict()) {
            log.error("User {} does not have permission to view {} data", principal.getEmail(), user.getEmail());
            throw new ServiceException(String.format("User %s does not have permission to view user %s",
                                                     principal.getEmail(), user.getEmail()),
                                       ErrorCode.ERROR_CODE_FORBIDDEN);
        }
        return Convert.my(user).scope(Scope.METADATA).boom();
    }

    public PageResponseDto<UserDto> findUsers(QueryFilterDto filter, Pageable paging) {
        Page<IUser> page = getRepository().findEntities(filter, paging);

        return UserSearchResultDto.builder()
                .numberOfItems((int) page.getTotalElements())
                .numberOfPages(page.getTotalPages())
                .data(page.getContent()
                        .stream()
                        .map(door -> (UserDto) Convert.my(door).scope(Scope.METADATA).boom())
                        .collect(Collectors.toList())).build();
    }

    public PublicUserMetadataDto getUserMetadata(String guid) throws ServiceException {
        UserJpa user = getRepository().findByGuid(guid);

        if (user == null) {
            throw new ServiceException("User not found");
        }

        String alias = user.getName();
        boolean isAdmin = UserUtils.hasAdminRole(user);

        return PublicUserMetadataDto.valueOf(guid, alias, isAdmin);
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
