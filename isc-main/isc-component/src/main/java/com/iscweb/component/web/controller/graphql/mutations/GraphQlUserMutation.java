package com.iscweb.component.web.controller.graphql.mutations;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.entity.core.RoleDto;
import com.iscweb.common.model.dto.entity.core.UserDto;
import com.iscweb.component.web.controller.graphql.common.UpdateResultDto;
import com.iscweb.component.web.controller.graphql.common.UpdateResultDto.Status;
import com.iscweb.component.web.util.GraphQlUtils;
import com.iscweb.service.RoleService;
import com.iscweb.service.UserService;
import com.iscweb.service.security.IscPrincipal;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL mutation for users.
 */
@Component
public class GraphQlUserMutation implements GraphQLMutationResolver {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UserService userService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RoleService roleService;

    public UserDto newUser(UserDto userDto) throws ServiceException {
        return getUserService().create(userDto);
    }

    public UserDto updateUser(UserDto userDto) throws ServiceException {
        return getUserService().update(userDto);
    }

    public UpdateResultDto deleteUser(String userId, DataFetchingEnvironment env) throws ServiceException {
        IscPrincipal principal = GraphQlUtils.currentUser(env);
        boolean success = getUserService().deleteUser(userId, principal.getUsername());
        return success ? new UpdateResultDto(Status.SUCCESS.name(), userId)
                : new UpdateResultDto(Status.FAILURE.name(), userId);
    }

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
