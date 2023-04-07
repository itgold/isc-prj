package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.ISafety;
import com.iscweb.common.model.metadata.SafetyStatus;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.model.jpa.SafetyJpa;
import com.iscweb.persistence.model.jpa.SafetyTagJpa;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
public class SafetyJpaRepositoryCustomImpl extends BaseJpaCompositeEntityRepositoryImpl<ISafety, SafetyJpa> implements SafetyJpaRepositoryCustom {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SafetyTagJpaRepository safetyTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagJpaRepository tagJpaRepository;

    public SafetyJpaRepositoryCustomImpl() {
        super(SafetyJpa.class);
    }

    @Override
    protected String mapPropertyName(String propertyName) {
        String result = null;
        // exclude tags column from DB query
        if (!"tags".equalsIgnoreCase(propertyName) && !"state".equalsIgnoreCase(propertyName) && !"regions".equalsIgnoreCase(propertyName)) {
            result = super.mapPropertyName(propertyName);
        }

        return result;
    }

    @Override
    protected Order createOrder(CriteriaBuilder builder, Root<SafetyJpa> root, Sort.Order order) {
        Order queryOrder = null;
        if ("region".equalsIgnoreCase(order.getProperty())) {
            // TODO: figure out if we can sort by association property and by one-to-many and many-to-many collections
            // queryOrder = order.isAscending() ? builder.asc(root.get("regions.name")) : builder.desc(root.get(order.getProperty()));
        } else {
            queryOrder = super.createOrder(builder, root, order);
        }

        return queryOrder;
    }

    @Override
    protected Predicate createPredicate(Root<SafetyJpa> root, CriteriaBuilder builder, String fieldName, String fieldValue) {
        Predicate predicate;
        if ("region".equalsIgnoreCase(fieldName)) {
            predicate = createRegionsFilter(root, builder, fieldValue);
        } else if ("status".equalsIgnoreCase(fieldName) && !StringUtils.isBlank(fieldValue)) {
            SafetyStatus status;
            try {
                status = SafetyStatus.valueOf(fieldValue.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.trace("Unable to parse status from string: {}", fieldValue, e);
                status = SafetyStatus.UNKNOWN;
            }

            predicate = builder.equal(root.get(fieldName), status);
        } else if ("name".equalsIgnoreCase(fieldName) && !StringUtils.isBlank(fieldValue)) {
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

    @Override
    protected Predicate createTagsFilter(Root<SafetyJpa> root, CriteriaBuilder builder, List<String> tags) {
        List<TagJpa> tagsList = getTagJpaRepository().findAllByNameIn(tags);
        List<SafetyTagJpa> doorTags = getSafetyTagJpaRepository().findAllByTagIn(tagsList);

        CriteriaBuilder.In<Long> inClause = builder.in(root.get("id"));
        for (SafetyTagJpa doorTag : doorTags) {
            inClause.value(doorTag.getEntity().getId());
        }

        return inClause;
    }
}
