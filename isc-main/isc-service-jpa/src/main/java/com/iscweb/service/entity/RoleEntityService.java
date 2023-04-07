package com.iscweb.service.entity;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.RoleDto;
import com.iscweb.common.model.dto.entity.core.RoleSearchResultDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IRole;
import com.iscweb.persistence.model.jpa.RoleJpa;
import com.iscweb.persistence.repositories.impl.RoleJpaRepository;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.service.converter.Convert.GUID;

/**
 * Service class for roles.
 */
@Slf4j
@Service
public class RoleEntityService extends BaseJpaEntityService<RoleJpaRepository, IRole> implements EntityService<RoleDto> {

    public RoleDto create(RoleDto roleDto) {
        RoleJpa result = Convert.my(roleDto)
                                .attr(GUID, true)
                                .boom();

        result = createOrUpdate(result);

        return Convert.my(result).boom();
    }

    public IRole createOrUpdate(RoleDto dto) {
        RoleJpa roleJpa = Convert.my(dto)
                                 .scope(Scope.ALL)
                                 .boom();

        return createOrUpdate(roleJpa);
    }

    public RoleDto update(RoleDto roleDto) throws ServiceException {
        String guid = roleDto.getId();
        RoleJpa roleJpa = getRepository().findByGuid(guid);

        if (roleJpa == null) {
            throw new ServiceException(String.format("Role %s cannot be found.", guid));
        }

        roleJpa.setName(roleDto.getName());
        roleJpa.setUpdated(ZonedDateTime.now());
        roleJpa = createOrUpdate(roleJpa);

        return Convert.my(roleJpa).boom();
    }

    public RoleJpa findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    public List<RoleDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        return getRepository().findAll(columns, paging)
            .stream()
            .map(role -> (RoleDto) Convert.my(role).boom())
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <E extends IRole> E findByRoleName(String roleName) {
        return (E) getRepository().findByName(roleName);
    }

    public PageResponseDto<RoleDto> findRoles(QueryFilterDto filter, Pageable paging) {
        Page<IRole> page = getRepository().findEntities(filter, paging);

        return RoleSearchResultDto.builder()
                .numberOfItems((int) page.getTotalElements())
                .numberOfPages(page.getTotalPages())
                .data(page.getContent()
                        .stream()
                        .map(door -> (RoleDto) Convert.my(door).scope(Scope.METADATA).boom())
                        .collect(Collectors.toList())).build();
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
