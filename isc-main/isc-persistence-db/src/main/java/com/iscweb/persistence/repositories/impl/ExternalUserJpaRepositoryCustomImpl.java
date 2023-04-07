package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.IExternalUser;
import com.iscweb.common.model.metadata.UserStatus;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.jpa.ExternalUserJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Slf4j
@Repository
public class ExternalUserJpaRepositoryCustomImpl
    extends BaseRepositoryWithProjectionsImpl<IExternalUser, ExternalUserJpa>
    implements ExternalUserJpaRepositoryCustom {

  public ExternalUserJpaRepositoryCustomImpl() {
    super(ExternalUserJpa.class);
  }

  @Override
  protected Predicate createPredicate(Root<ExternalUserJpa> root, CriteriaBuilder builder, String fieldName, String fieldValue) {
    Predicate predicate;
    if ("status".equalsIgnoreCase(fieldName) && !StringUtils.isBlank(fieldValue)) {
      UserStatus status;
      try {
        status = UserStatus.valueOf(fieldValue.toUpperCase());
      } catch (IllegalArgumentException e) {
        log.trace("Unable to parse status from string: {}", fieldValue, e);
        status = UserStatus.UNKNOWN;
      }

      predicate = builder.equal(root.get(fieldName), status);
    } else {
      predicate = super.createPredicate(root, builder, fieldName, fieldValue);
    }

    return predicate;
  }
}
