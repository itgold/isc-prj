package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.IUser;
import com.iscweb.common.model.metadata.UserStatus;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.jpa.UserJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Slf4j
@Repository
public class UserJpaRepositoryCustomImpl
    extends BaseRepositoryWithProjectionsImpl<IUser, UserJpa>
    implements UserJpaRepositoryCustom {

  public UserJpaRepositoryCustomImpl() {
    super(UserJpa.class);
  }

  @Override
  protected Predicate createPredicate(Root<UserJpa> root, CriteriaBuilder builder, String fieldName, String fieldValue) {
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
