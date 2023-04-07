package com.iscweb.service.entity;

import com.iscweb.common.model.IEntity;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import com.iscweb.persistence.repositories.impl.RegionJpaRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Abstract base class for services that work within a region scope.
 *
 * @param <R> repository type.
 * @param <E> entity type.
 * @author skurenkov
 */
@SuppressWarnings("rawtypes")
@Slf4j
@Service
public abstract class BaseRegionEntityService<R extends IPersistenceRepository, E extends IEntity> extends BaseJpaEntityService<R, E> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionJpaRepository regionRepository;

}
