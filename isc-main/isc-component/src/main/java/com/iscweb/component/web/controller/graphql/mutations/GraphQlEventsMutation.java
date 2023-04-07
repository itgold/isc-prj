package com.iscweb.component.web.controller.graphql.mutations;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.alert.AlertTriggerDto;
import com.iscweb.common.model.dto.AlertActionDto;
import com.iscweb.common.model.dto.entity.core.AlertDto;
import com.iscweb.component.web.controller.graphql.common.UpdateResultDto;
import com.iscweb.component.web.controller.graphql.common.UpdateResultDto.Status;
import com.iscweb.component.web.util.GraphQlUtils;
import com.iscweb.service.AlertService;
import com.iscweb.service.AlertTriggerService;
import com.iscweb.service.security.IscPrincipal;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL mutation for alert triggers.
 */
@Component
public class GraphQlEventsMutation implements GraphQLMutationResolver {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertTriggerService alertTriggerService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertService alertService;

    public AlertTriggerDto newAlertTrigger(AlertTriggerDto alertTrigger) throws ServiceException {
        return getAlertTriggerService().create(alertTrigger);
    }

    public AlertTriggerDto updateAlertTrigger(AlertTriggerDto alertTriggerDto) throws ServiceException {
        return getAlertTriggerService().update(alertTriggerDto);
    }

    public UpdateResultDto deleteAlertTrigger(String alertTriggerId) {
        getAlertTriggerService().delete(alertTriggerId);
        return new UpdateResultDto(Status.SUCCESS.name(), alertTriggerId);
    }

    public UpdateResultDto updateAlert(AlertActionDto action, DataFetchingEnvironment env) throws ServiceException {
        if (action.getAlertId() != null) {
            getAlertService().updateAlert(action, GraphQlUtils.currentUser(env));
            return new UpdateResultDto(Status.SUCCESS.name(), action.getAlertId());
        }

        return new UpdateResultDto(Status.FAILURE.name(), action.getAlertId());
    }
}
