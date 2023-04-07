package com.iscweb.service.entity;

import com.iscweb.common.model.entity.IIntegration;
import com.iscweb.persistence.repositories.impl.IntegrationIndexJpaRepository;
import com.iscweb.persistence.repositories.impl.IntegrationJpaRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for integrations.
 */
@Slf4j
@Service
public class IntegrationService extends BaseJpaEntityService<IntegrationJpaRepository, IIntegration> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IntegrationIndexJpaRepository integrationIndexRepository;

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
