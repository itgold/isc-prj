package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.ISchool;
import com.iscweb.common.model.metadata.SchoolStatus;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.jpa.SchoolJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Slf4j
@Repository
public class SchoolJpaRepositoryCustomImpl extends BaseRepositoryWithProjectionsImpl<ISchool, SchoolJpa> implements SchoolJpaRepositoryCustom {

    public SchoolJpaRepositoryCustomImpl() {
        super(SchoolJpa.class);
    }

    @Override
    protected String mapPropertyName(String propertyName) {
        String result = propertyName;
        if ("district".equals(propertyName)) {
            result = "schoolDistrict";
        } else if ("id".equals(propertyName)) {
            result = "guid";
        }

        return result;
    }

    @Override
    protected Predicate createPredicate(Root<SchoolJpa> root, CriteriaBuilder builder, String fieldName, String fieldValue) {
        Predicate predicate;
        if ("status".equalsIgnoreCase(fieldName) && !StringUtils.isBlank(fieldValue)) {
            SchoolStatus status;
            try {
                status = SchoolStatus.valueOf(fieldValue.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.trace("Unable to parse status from string: {}", fieldValue, e);
                status = SchoolStatus.UNKNOWN;
            }

            predicate = builder.equal(root.get(fieldName), status);
        } else {
            predicate = super.createPredicate(root, builder, fieldName, fieldValue);
        }

        return predicate;
    }
}
