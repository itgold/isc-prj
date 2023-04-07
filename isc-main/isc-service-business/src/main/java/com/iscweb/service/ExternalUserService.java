package com.iscweb.service;

import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.ExternalUserSearchResultDto;
import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.service.entity.user.ExternalUserEntityService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "jpaTransactionManager")
public class ExternalUserService implements IApplicationSecuredService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ExternalUserEntityService entityService;


    public ExternalUserSearchResultDto findUsers(QueryFilterDto filter, PageRequest pageRequest) {
        return (ExternalUserSearchResultDto) getEntityService().findUsers(filter, pageRequest);
    }
}
