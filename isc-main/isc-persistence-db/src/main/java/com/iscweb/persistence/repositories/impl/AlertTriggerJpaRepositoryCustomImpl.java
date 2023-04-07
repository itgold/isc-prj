package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.IAlertTrigger;
import com.iscweb.persistence.model.jpa.AlertTriggerJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class AlertTriggerJpaRepositoryCustomImpl
        extends BaseJpaCompositeEntityRepositoryImpl<IAlertTrigger, AlertTriggerJpa>
        implements AlertTriggerJpaRepositoryCustom {

    public AlertTriggerJpaRepositoryCustomImpl() {
        super(AlertTriggerJpa.class);
    }

    /*
    @Override
    protected Predicate createPredicate(Root<AlertTriggerJpa> root, CriteriaBuilder builder, String fieldName, String fieldValue) {
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
        } else {
            predicate = super.createPredicate(root, builder, fieldName, fieldValue);
        }

        return predicate;
    }
    */
}
