package com.iscweb.persistence.repositories.impl;

import com.google.common.collect.Sets;
import com.iscweb.common.service.ICompositeCache;
import com.iscweb.persistence.model.BaseJpaCompositeEntity;
import com.iscweb.persistence.model.BaseJpaTrackedEntity;
import com.iscweb.persistence.model.jpa.DoorJpa;
import com.iscweb.persistence.model.jpa.RegionJpa;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseJpaCompositeEntityRepositoryImpl<T, E extends T> extends BaseRepositoryWithProjectionsImpl<T, E> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ICompositeCache compositeService;

    public BaseJpaCompositeEntityRepositoryImpl(Class<E> entityClass) {
        super(entityClass);
    }

    /**
     * Create query predicate for search criteria for the <code>regions</code> OneToMany relation.
     *
     * @param root Entity metadata
     * @param builder Criteria builder
     * @param filter Parent region path filter
     * @param <TYPE> Entity type
     * @return Query predicate
     */
    protected <TYPE extends BaseJpaCompositeEntity> Predicate createRegionsFilter(Root<TYPE> root, CriteriaBuilder builder, String filter) {
        Predicate predicate = null;

        Set<String> regionIds = getCompositeService().findChildRegionsByParentPath(filter);
        Set<RegionJpa> regions = !CollectionUtils.isEmpty(regionIds) ? findRegionsByGuids(regionIds) : null;
        List<Long> ids = !CollectionUtils.isEmpty(regions) ? regions.stream().map(BaseJpaTrackedEntity::getId).collect(Collectors.toList()) : List.of(-1L);
        if (!CollectionUtils.isEmpty(ids)) {
            SetJoin<DoorJpa, RegionJpa> children = root.joinSet("regions", JoinType.LEFT);
            CriteriaBuilder.In<Long> inClause = builder.in(children.get("id"));
            for (Long regionId : ids) {
                inClause.value(regionId);
            }

            predicate = inClause;
        }

        return predicate;
    }

    protected Set<RegionJpa> findRegionsByGuids(Set<String> regionIds) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<RegionJpa> queryObj = builder.createQuery(RegionJpa.class);
        Root<RegionJpa> root = queryObj.from(RegionJpa.class);

        CriteriaQuery<RegionJpa> selectQuery = queryObj.select(root);
        selectQuery.select(root);

        Predicate query = root.get("guid").in(regionIds);
        selectQuery.where(query);
        selectQuery.distinct(true);

        TypedQuery<RegionJpa> typedQuery = getEntityManager().createQuery(selectQuery);
        List<RegionJpa> entityList = typedQuery.getResultList();
        return Sets.newHashSet(entityList);
    }
}
