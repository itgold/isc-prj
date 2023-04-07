package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.EntityType;
import com.iscweb.persistence.model.jpa.AlertJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for{@link AlertJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface AlertJpaRepository extends IPersistenceRepository<AlertJpa>, AlertJpaRepositoryCustom {

  AlertJpa findByGuid(String guid);

  @Override
  void deleteByGuid(String guid);

  AlertJpa findByTriggerIdAndDeviceIdAndDeviceType(String triggerId, String deviceId, EntityType deviceType);
}
