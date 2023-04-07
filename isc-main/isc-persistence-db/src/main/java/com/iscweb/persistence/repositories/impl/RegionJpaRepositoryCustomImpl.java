package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.IRegion;
import com.iscweb.common.model.metadata.RegionStatus;
import com.iscweb.common.service.ICompositeCache;
import com.iscweb.common.util.StringUtils;
import com.iscweb.persistence.model.jpa.RegionJpa;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Slf4j
@Repository
public class RegionJpaRepositoryCustomImpl
        extends BaseJpaCompositeEntityRepositoryImpl<IRegion, RegionJpa>
        implements RegionJpaRepositoryCustom {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ICompositeCache compositeService;

    // @Getter
    // @Setter(onMethod = @__({@Autowired}))
    // private RegionTagJpaRepository regionTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagJpaRepository tagJpaRepository;

    public RegionJpaRepositoryCustomImpl() {
        super(RegionJpa.class);
    }

    @Override
    protected String mapPropertyName(String propertyName) {
        String result = propertyName;
        if ("id".equals(propertyName)) {
            result = "guid";
        } else if ("children".equals(propertyName) || "regions".equalsIgnoreCase(propertyName)) {
            result = null;
        }

        return result;
    }

    /*
    @Override
    protected Predicate createTagsFilter(Root<RegionJpa> root, CriteriaBuilder builder, List<String> tags) {
        List<TagJpa> tagsList = getTagJpaRepository().findAllByNameIn(tags);
        List<RegionTagJpa> doorTags = getRegionTagJpaRepository().findAllByTagIn(tagsList);

        CriteriaBuilder.In<Long> inClause = builder.in(root.get("id"));
        for (RegionTagJpa doorTag : doorTags) {
            inClause.value(doorTag.getEntity().getId());
        }

        return inClause;
    }
    */

    @Override
    protected Predicate createPredicate(Root<RegionJpa> root, CriteriaBuilder builder, String fieldName, String fieldValue) {
        Predicate predicate;
        if ("regions".equalsIgnoreCase(fieldName)) {
            predicate = createRegionsFilter(root, builder, fieldValue);
        } else if ("status".equalsIgnoreCase(fieldName) && !StringUtils.isBlank(fieldValue)) {
            RegionStatus status;
            try {
                status = RegionStatus.valueOf(fieldValue.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.trace("Unable to parse status from string: {}", fieldValue, e);
                status = RegionStatus.UNKNOWN;
            }

            predicate = builder.equal(root.get(fieldName), status);
        } else {
            predicate = super.createPredicate(root, builder, fieldName, fieldValue);
        }

        return predicate;
    }
}
