package com.iscweb.service.entity.user;

import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.ExternalUserDto;
import com.iscweb.common.model.dto.entity.core.ExternalUserSearchResultDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IExternalUser;
import com.iscweb.persistence.model.jpa.ExternalUserJpa;
import com.iscweb.persistence.repositories.impl.ExternalUserJpaRepository;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.BaseJpaEntityService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.ADMINISTRATORS_PERMISSION;
import static com.iscweb.service.converter.Convert.GUID;

/**
 * A service for external user operations.
 */
@Slf4j
@Service
public class ExternalUserEntityService extends BaseJpaEntityService<ExternalUserJpaRepository, IExternalUser> {

    /**
     * Takes the {@link ExternalUserDto} passed and saves it into the repository (creating a new {@link ExternalUserJpa}0,
     * setting a new GUID, and password for it. When the user is saved, it gets converted back into DTO and
     * is returned to the caller.
     *
     * @param userDto {@link ExternalUserDto} with the user data (not on the repository)
     * @return {@link ExternalUserDto} representing the new user stored in the repository
     */
    public ExternalUserDto create(ExternalUserDto userDto) {

        ExternalUserJpa result = Convert.my(userDto)
                .attr(GUID, true)
                .boom();

        result = this.createOrUpdate(result);
        return Convert.my(result).scope(Scope.ALL).boom();
    }

    /**
     * Updates an existing external user. The user is evicted from the user cache. The user cannot change
     * their external id.
     *
     * @return {@link ExternalUserDto} the updated user
     */
    @CacheEvict(value = "external-users", key = "#p0.id", beforeInvocation = true)
    public ExternalUserDto update(ExternalUserDto userDto) {
        ExternalUserJpa result = Convert.my(userDto)
                .withJpa(getRepository().findByGuid(userDto.getId()))
                .scope(Scope.ALL).boom();

        result = (ExternalUserJpa) update(result);
        return Convert.my(result).scope(Scope.ALL).boom();
    }

    @CacheEvict(value = "external-users", key = "#p0.guid", beforeInvocation = true)
    public IExternalUser update(IExternalUser user) {
        ExternalUserJpa result = (ExternalUserJpa) user;
        result = this.createOrUpdate(result);

        return result;
    }

    @Override
    @CacheEvict(value = "external-users", key = "#p0", beforeInvocation = true)
    public void delete(String guid) {
        super.delete(guid);
    }

    @Cacheable(cacheNames = "external-users", key = "#p0")
    public ExternalUserDto findByGuid(String guid, List<LazyLoadingField> fields) {
        IExternalUser entity = getRepository().findByGuid(guid);
        return entity != null ? Convert.my(entity).scope(Scope.fromLazyField(fields)).boom() : null;
    }

    /**
     * Fetches all users.
     */
    @PreAuthorize(ADMINISTRATORS_PERMISSION)
    public PageResponseDto<ExternalUserDto> findUsers(QueryFilterDto filter, Pageable paging) {
        Page<IExternalUser> page = getRepository().findEntities(filter, paging);

        return ExternalUserSearchResultDto.builder()
                .numberOfItems((int) page.getTotalElements())
                .numberOfPages(page.getTotalPages())
                .data(page.getContent()
                        .stream()
                        .map(door -> (ExternalUserDto) Convert.my(door).scope(Scope.METADATA).boom())
                        .collect(Collectors.toList())).build();
    }

    public ExternalUserDto findByExternalId(String externalId, List<LazyLoadingField> fields) {
        IExternalUser entity = getRepository().findByExternalId(externalId);
        return entity != null ? Convert.my(entity).scope(Scope.fromLazyField(fields)).boom() : null;
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
