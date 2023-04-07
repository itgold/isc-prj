package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.ISchoolDistrict;
import com.iscweb.common.model.metadata.SchoolDistrictStatus;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.jpa.SchoolDistrictJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Slf4j
@Repository
public class SchoolDistrictJpaRepositoryCustomImpl
        extends BaseRepositoryWithProjectionsImpl<ISchoolDistrict, SchoolDistrictJpa>
        implements SchoolDistrictJpaRepositoryCustom {

    public SchoolDistrictJpaRepositoryCustomImpl() {
        super(SchoolDistrictJpa.class);
    }

    @Override
    protected Predicate createPredicate(Root<SchoolDistrictJpa> root, CriteriaBuilder builder, String fieldName, String fieldValue) {
        Predicate predicate;
        if ("status".equalsIgnoreCase(fieldName) && !StringUtils.isBlank(fieldValue)) {
            SchoolDistrictStatus status;
            try {
                status = SchoolDistrictStatus.valueOf(fieldValue.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.trace("Unable to parse status from string: {}", fieldValue, e);
                status = SchoolDistrictStatus.UNKNOWN;
            }

            predicate = builder.equal(root.get(fieldName), status);
        } else {
            predicate = super.createPredicate(root, builder, fieldName, fieldValue);
        }

        return predicate;
    }
}
