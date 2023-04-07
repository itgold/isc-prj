package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.metadata.RadioStatus;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.jpa.RadioJpa;
import com.iscweb.persistence.model.jpa.RadioTagJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
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

/**
 * Custom DAO methods implementation for {@link RadioJpa} objects.
 */
@Slf4j
@Repository
public class RadioJpaRepositoryCustomImpl
        extends BaseJpaCompositeEntityRepositoryImpl<RadioJpa, RadioJpa>
        implements RadioJpaRepositoryCustom {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RadioTagJpaRepository radioTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagJpaRepository tagJpaRepository;

    public RadioJpaRepositoryCustomImpl() {
        super(RadioJpa.class);
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
    protected Order createOrder(CriteriaBuilder builder, Root<RadioJpa> root, Sort.Order order) {
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
    protected Predicate createPredicate(Root<RadioJpa> root, CriteriaBuilder builder, String fieldName, String fieldValue) {
        Predicate predicate;
        if ("region".equalsIgnoreCase(fieldName)) {
            predicate = createRegionsFilter(root, builder, fieldValue);
        } else if ("status".equalsIgnoreCase(fieldName) && !StringUtils.isBlank(fieldValue)) {
            RadioStatus status;
            try {
                status = RadioStatus.valueOf(fieldValue.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.trace("Unable to parse status from string: {}", fieldValue, e);
                status = RadioStatus.UNKNOWN;
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
    protected Predicate createTagsFilter(Root<RadioJpa> root, CriteriaBuilder builder, List<String> tags) {
        List<TagJpa> tagsList = getTagJpaRepository().findAllByNameIn(tags);
        List<RadioTagJpa> radioTags = getRadioTagJpaRepository().findAllByTagIn(tagsList);

        CriteriaBuilder.In<Long> inClause = builder.in(root.get("id"));
        for (RadioTagJpa radioTag : radioTags) {
            inClause.value(radioTag.getEntity().getId());
        }

        return inClause;
    }
}
