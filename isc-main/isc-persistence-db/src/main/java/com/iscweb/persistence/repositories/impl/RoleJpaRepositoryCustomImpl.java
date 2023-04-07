package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.IRole;
import com.iscweb.persistence.model.jpa.RoleJpa;
import org.springframework.stereotype.Repository;

@Repository
public class RoleJpaRepositoryCustomImpl
        extends BaseRepositoryWithProjectionsImpl<IRole, RoleJpa>
        implements RoleJpaRepositoryCustom {

    public RoleJpaRepositoryCustomImpl() {
        super(RoleJpa.class);
    }

}
