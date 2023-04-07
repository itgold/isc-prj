package com.iscweb.component.web.controller.graphql.mutations;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.DeviceActionDto;
import com.iscweb.common.model.dto.DeviceActionResultDto;
import com.iscweb.common.service.IDeviceActionHandler;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GraphQL mutation for device actions.
 */
@Component
public class GraphQlDeviceActionMutation implements GraphQLMutationResolver {

    @Getter
    @Setter(onMethod = @__({@Autowired(required = false)}))
    private List<IDeviceActionHandler> actionHandlers;

    public DeviceActionResultDto deviceAction(DeviceActionDto action) throws ServiceException {
        DeviceActionResultDto result;

        IDeviceActionHandler handler = getActionHandlers().stream()
                .filter(h -> StringUtils.equalsAnyIgnoreCase(h.actionCode(), action.getAction()))
                .findFirst()
                .orElse(null);
        if (handler != null) {
            try {
                result = handler.execute(action);
            } catch (Exception e) {
                result = DeviceActionResultDto.builder()
                        .status(DeviceActionResultDto.ActionResult.FAILURE)
                        .errors(List.of(new DeviceActionResultDto.DeviceActionError(e.getMessage())))
                        .build();
            }
        } else {
            result = DeviceActionResultDto.builder()
                    .status(DeviceActionResultDto.ActionResult.FAILURE)
                    .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.NOT_IMPLEMENTED.name())))
                    .build();
        }

        return result;
    }
}
