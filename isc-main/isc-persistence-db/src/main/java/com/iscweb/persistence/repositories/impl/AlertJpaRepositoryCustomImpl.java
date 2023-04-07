package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.ISearchEntityVo;
import com.iscweb.common.model.entity.IAlert;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.jpa.AlertJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Slf4j
@Repository
public class AlertJpaRepositoryCustomImpl
        extends BaseJpaCompositeEntityRepositoryImpl<IAlert, AlertJpa>
        implements AlertJpaRepositoryCustom {

    public AlertJpaRepositoryCustomImpl() {
        super(AlertJpa.class);
    }

    @Override
    protected Predicate createPredicate(Root<AlertJpa> root, CriteriaBuilder builder, String fieldName, String fieldValue) {
        Predicate predicate;
        if ("name".equalsIgnoreCase(fieldName) && !StringUtils.isBlank(fieldValue)) {
            predicate = builder.or(
                    builder.like(
                            builder.lower(root.get(fieldName)),
                            builder.lower(builder.literal("%" + fieldValue + "%"))
                    ),
                    builder.like(
                            builder.lower(root.get("guid")),
                            builder.lower(builder.literal("%" + fieldValue + "%"))
                    )
            );
        } else if ("schoolId".equalsIgnoreCase(fieldName) && !StringUtils.isBlank(fieldValue)) {
            predicate = builder.or(
                    builder.equal(root.get(fieldName), fieldValue),
                    builder.equal(root.get(fieldName), ISearchEntityVo.SEARCH_FIELD_ANY),
                    builder.isNull(root.get(fieldName))
            );
        } else {
            predicate = super.createPredicate(root, builder, fieldName, fieldValue);
        }

        return predicate;
    }
}
