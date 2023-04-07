package com.iscweb.component.web.controller.graphql.mutations;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.entity.core.RoleDto;
import com.iscweb.component.web.controller.graphql.common.UpdateResultDto;
import com.iscweb.component.web.controller.graphql.common.UpdateResultDto.Status;
import com.iscweb.service.RoleService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL mutation for roles.
 */
@Component
public class GraphQlRoleMutation implements GraphQLMutationResolver {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RoleService roleService;

    public RoleDto newRole(RoleDto roleDto) throws ServiceException {
        return getRoleService().create(roleDto);
    }

    public RoleDto updateRole(RoleDto roleDto) throws ServiceException {
        return getRoleService().update(roleDto);
    }

    public UpdateResultDto deleteRole(String roleId) {
        getRoleService().delete(roleId);
        return new UpdateResultDto(Status.SUCCESS.name(), roleId);
    }

}
