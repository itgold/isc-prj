package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.IDrone;
import com.iscweb.common.model.metadata.DroneStatus;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.jpa.DroneJpa;
import com.iscweb.persistence.model.jpa.DroneTagJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
public class DroneJpaRepositoryCustomImpl
        extends BaseJpaCompositeEntityRepositoryImpl<IDrone, DroneJpa>
        implements DroneJpaRepositoryCustom {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DroneTagJpaRepository droneTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagJpaRepository tagJpaRepository;

    public DroneJpaRepositoryCustomImpl() {
        super(DroneJpa.class);
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
    protected Predicate createTagsFilter(Root<DroneJpa> root, CriteriaBuilder builder, List<String> tags) {
        List<TagJpa> tagsList = getTagJpaRepository().findAllByNameIn(tags);
        List<DroneTagJpa> doorTags = getDroneTagJpaRepository().findAllByTagIn(tagsList);

        CriteriaBuilder.In<Long> inClause = builder.in(root.get("id"));
        for (DroneTagJpa doorTag : doorTags) {
            inClause.value(doorTag.getEntity().getId());
        }

        return inClause;
    }

    @Override
    protected Predicate createPredicate(Root<DroneJpa> root, CriteriaBuilder builder, String fieldName, String fieldValue) {
        Predicate predicate;
        if ("region".equalsIgnoreCase(fieldName)) {
            predicate = createRegionsFilter(root, builder, fieldValue);
        } else if ("status".equalsIgnoreCase(fieldName) && !StringUtils.isBlank(fieldValue)) {
            DroneStatus status;
            try {
                status = DroneStatus.valueOf(fieldValue.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.trace("Unable to parse status from string: {}", fieldValue, e);
                status = DroneStatus.UNKNOWN;
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
}
