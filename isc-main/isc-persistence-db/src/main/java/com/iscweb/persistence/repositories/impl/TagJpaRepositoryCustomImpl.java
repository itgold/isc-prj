package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.ITag;
import com.iscweb.persistence.model.jpa.TagJpa;
import org.springframework.stereotype.Repository;

@Repository
public class TagJpaRepositoryCustomImpl
    extends BaseRepositoryWithProjectionsImpl<ITag, TagJpa>
    implements TagJpaRepositoryCustom {

  public TagJpaRepositoryCustomImpl() {
    super(TagJpa.class);
  }

}
