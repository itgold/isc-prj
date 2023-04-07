package com.iscweb.service.entity;

import com.iscweb.common.exception.ResourceNotFoundException;
import com.iscweb.common.model.entity.IIndex;
import com.iscweb.persistence.model.jpa.IndexJpa;
import com.iscweb.persistence.repositories.impl.IndexJpaRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * An index is a data bucket for a particular data source in ElasticSearch. This service is
 * responsible for providing index-related functionality.
 */
@Slf4j
@Service
public class IndexService extends BaseJpaEntityService<IndexJpaRepository, IIndex> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private @NonNull SchoolDistrictIndexService schoolDistrictIndexService;

    @Transactional(transactionManager = "jpaTransactionManager", isolation = Isolation.READ_COMMITTED)
    public IndexJpa findByGuid(String fileGuid) throws ResourceNotFoundException {
        IndexJpa result = getRepository().findByGuid(fileGuid);
        if (Objects.isNull(result)) {
            throw new ResourceNotFoundException(String.format("File not found by guid: %s", fileGuid));
        }

        return result;
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
