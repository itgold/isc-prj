package com.iscweb.persistence.repositories.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.iscweb.common.model.dto.ColumnFilterDto;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.util.DateUtils;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.utils.AliasToBeanNestedMultiLevelResultTransformer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.criteria.internal.path.SingularAttributePath;
import org.hibernate.transform.ResultTransformer;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract base class implementing projection methods to reduce load from DB.
 *
 * @param <T> Result class/interface
 * @param <E> Entity class. Specific implementation of the result class/interface.
 */
@Slf4j
public abstract class BaseRepositoryWithProjectionsImpl<T, E extends T> {

    private static final List<String> DATE_FIELDS = Lists.newArrayList("created", "updated");

    private final Class<E> entityClass;

    private final ResultTransformer transformer;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private EntityManager entityManager;

    public BaseRepositoryWithProjectionsImpl(Class<E> entityClass) {
        this.entityClass = entityClass;

        // By default, use the custom results transformer which transforms row data <code>Object[]</code>
        // to JPA using setters. The default Hibernate transformer uses the corresponding constructor,
        // which does not work in the case of dynamic projection columns.
        // This transformer supports n-level nested sub-entities.
        this.transformer = new AliasToBeanNestedMultiLevelResultTransformer(this.entityClass);
    }

    /**
     * @return a custom JPA query results transformer.
     */
    protected ResultTransformer getTransformer() {
        return this.transformer;
    }

    /**
     * Returns specific JPA implementation metadata.
     *
     * @param cq criteria query.
     * @return specific JPA implementation metadata.
     */
    protected Root<?> resultsMetadata(CriteriaQuery<?> cq) {
        return cq.from(this.entityClass);
    }

    /**
     * Map column name from <code>ProjectionDto</code> to JPA entity.
     *
     * @param propertyName property name.
     * @return column name in JPA object.
     */
    protected String mapPropertyName(String propertyName) {
        String result = propertyName;
        if ("id".equals(propertyName)) {
            result = "guid";
        }

        return result;
    }

    /**
     * Map column name from <code>ProjectionDto</code> to JPA entity for nested entities.
     *
     * @param parentPropertyName property name.
     * @param propertyName nested object property name.
     * @return column name in JPA object.
     */
    protected String mapPropertyName(String parentPropertyName, String propertyName) {
        assert parentPropertyName != null;
        assert propertyName != null;

        return propertyName;
    }

    /**
     * Find all entities using pagination and projections to minimize load from DB.
     *
     * @param columns projection for the query.
     * @param paging page information.
     * @return list of found entities.
     */
    public List<T> findAll(List<ProjectionDto> columns, PageRequest paging) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<?> metadata = resultsMetadata(cq);
        List<Selection<?>> columnsSelection = createProjection(metadata, columns);
        cq.multiselect(columnsSelection);

        if (paging != null) {
            cq.orderBy(QueryUtils.toOrders(paging.getSort(), metadata, cb));
        }

        TypedQuery<Object[]> query = getEntityManager().createQuery(cq);
        if (paging != null) {
            query.setFirstResult((int) paging.getOffset());
            query.setMaxResults(paging.getPageSize());
        }

        return Lists.newArrayList(transformResults(query.getResultStream(), columnsSelection));
    }

    /**
     * Generates an alias (with a path to the parent) for the results transformer aliases list.
     *
     * @param path the lowest-level entity column path (ex. SchoolJpa :: SchoolDistrictJpa :: id).
     * @return an alias including all levels (ex: a SchoolJpa entity will produce "schoolDistrict.id").
     */
    protected String generateAlias(SingularAttributePath<?> path) {
        String alias = path.getAttribute().getName();
        if (path.getParentPath() != null && path.getParentPath() instanceof SingularAttributePath) {
            Stack<String> stack = new Stack<>();
            stack.push(path.getAttribute().getName());

            SingularAttributePath<?> parentPath = (SingularAttributePath<?>) path.getParentPath();
            while (parentPath != null) {
                stack.push(parentPath.getAttribute().getName());
                parentPath = parentPath.getParentPath() != null && parentPath.getParentPath() instanceof SingularAttributePath
                        ? (SingularAttributePath<?>) parentPath.getParentPath() : null;
            }

            StringBuilder sb = new StringBuilder();
            while (stack.size() > 0) {
                if (sb.length() > 0) {
                    sb.append(".");
                }
                sb.append(stack.pop());
            }
            alias = sb.toString();
        }

        return alias;
    }

    /**
     * Transforms raw data from query results into the target list of entities.
     *
     * @param resultsStream results stream.
     * @param columnsSelection projection used for the query.
     * @return list of result entities.
     */
    @SuppressWarnings("unchecked")
    protected List<E> transformResults(Stream<Object[]> resultsStream, List<Selection<?>> columnsSelection) {
        String[] aliases = columnsSelection.stream()
                .map(selection -> selection instanceof SingularAttributePath ? generateAlias((SingularAttributePath<?>) selection) : null)
                .filter(name -> !StringUtils.isEmpty(name))
                .collect(Collectors.toList())
                .toArray(new String[]{});
        ResultTransformer transformer = getTransformer();
        return resultsStream
                .map(result -> (E) transformer.transformTuple(result, aliases))
                .collect(Collectors.toList());
    }

    /**
     * Creates a Hibernate CriteriaQuery projection with nesting.
     *
     * @param metadata target JPA entity metadata.
     * @param columns projection columns tree.
     * @return Hibernate CriteriaQuery projection (hierarchical property selection tree for all nested entities).
     */
    protected List<Selection<?>> createProjection(Path<?> metadata, List<ProjectionDto> columns) {
        List<Selection<?>> columnsSelection = Lists.newArrayList();
        columns.forEach(column -> {
            if (CollectionUtils.isEmpty(column.getChildren())) {
                String propName = mapPropertyName(column.getColumnName());
                if (propName != null) {
                    Path<?> path = metadata.get(propName);
                    columnsSelection.add(path);
                }
            } else {
                String parentColumnName = mapPropertyName(column.getColumnName());
                if (parentColumnName != null) {
                    Path<?> path = metadata.get(parentColumnName);

                    // Important!
                    // Some types are BASIC and can be queried only as a whole piece from DB.
                    // For example, geo.Point is persistable type. You can't query only x or y property
                    boolean basicType = isBasicPersistableType(path);
                    if (column.getChildren().size() > 0 && !basicType) {
                        List<Selection<?>> childSelection = createProjection(path, column.getChildren());
                        if (!CollectionUtils.isEmpty(childSelection)) {
                            columnsSelection.addAll(childSelection);
                        }
                    } else if (basicType) {
                        columnsSelection.add(path);
                    }
                }
            }
        });

        return columnsSelection;
    }

    /**
     * Special check to prevent creating Hibernate projection selection for the columns associated with types
     * which must be stored in the DB together.
     *
     * @param path Nested objects hierarchy path
     * @return <code>true</code> if the represented path is pointing to the object which can be loaded from DB as single piece.
     */
    protected boolean isBasicPersistableType(Path<?> path) {
        boolean result = false;
        if (path != null && path.getJavaType() != null) {
            if (Point.class.isAssignableFrom(path.getJavaType()) ||
                    Polygon.class.isAssignableFrom(path.getJavaType()) ||
                    List.class.isAssignableFrom(path.getJavaType())) {
                result = true;
            }
        }

        return result;
    }

    protected Predicate createPredicate(Root<E> root, CriteriaBuilder builder, String fieldName, String fieldValue) {
        Predicate predicate = null;
        Expression<String> exp = root.get(fieldName);
        if (exp != null) {
            if (DATE_FIELDS.contains(fieldName)) {
                ZonedDateTime startTime = null;
                ZonedDateTime endTime = null;
                String[] dates = fieldValue != null ? fieldValue.split(" ") : null;
                if (dates != null && dates.length > 0 && dates.length <= 2) {
                    startTime = DateUtils.parseAsZonedDateTime(dates[0], null);
                    endTime = dates.length > 1 ? DateUtils.parseAsZonedDateTime(dates[1], null) : null;
                    if (startTime != null || endTime != null) {
                        if (startTime != null && endTime == null || startTime == null && endTime != null) {
                            ZonedDateTime dateTime = startTime != null ? startTime : endTime;
                            startTime = dateTime.toLocalDate().atStartOfDay(dateTime.getZone());
                            endTime = dateTime.with(LocalTime.MAX);
                        }

                        startTime = startTime.with(LocalTime.MIN);
                        endTime = endTime.with(LocalTime.MAX);
                    }
                }

                if (startTime == null && endTime == null) { // not resolved
                    startTime = DateUtils.DAY_IN_FUTURE;
                    endTime = DateUtils.DAY_IN_FUTURE;
                }

                Expression<ZonedDateTime> exp2 = root.get(fieldName);
                predicate = builder.between(exp2, startTime, endTime);
            } else {
                // make it case-insensitive "like" by default
                predicate = builder.like(
                        builder.lower(exp),
                        builder.lower(builder.literal("%" + fieldValue + "%"))
                );
            }
        }

        return predicate;
    }

    protected Order createOrder(CriteriaBuilder builder, Root<E> root, Sort.Order order) {
        return order.isAscending() ?
                builder.asc(root.get(order.getProperty())) : builder.desc(root.get(order.getProperty()));
    }

    protected List<Order> createOrderList(CriteriaBuilder builder, Root<E> root, Sort sort) {
        List<Order> orderList = sort.get()
                .map(order -> createOrder(builder, root, order))
                .filter(order -> order != null)
                .collect(Collectors.toList());

        return orderList;
    }

    protected Predicate createTagsFilter(Root<E> root, CriteriaBuilder builder, List<String> tags) {
        return null;
    }

    public Page<T> findEntities(QueryFilterDto filter, Pageable paging) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<E> queryObj = builder.createQuery(entityClass);
        Root<E> root = queryObj.from(entityClass);

        CriteriaQuery<E> selectQuery = queryObj.select(root);
        selectQuery.select(root);

        Predicate query = null;
        if (filter.getColumns().size() > 0) {
            List<Predicate> filters = Lists.newArrayList();
            for (ColumnFilterDto entry : filter.getColumns()) {
                Predicate predicate = createPredicate(root, builder, entry.getKey(), entry.getValue());
                if (predicate != null) {
                    filters.add(predicate);
                }
            }

            if (filters.size() > 0) {
                query = builder.and(filters.toArray(new Predicate[] {}));
            }
        }

        if (!CollectionUtils.isEmpty(filter.getTags())) {
            Predicate tags = createTagsFilter(root, builder, filter.getTags());
            if (tags != null) {
                query = query == null ? tags : builder.and(query, tags);
            }
        }

        if (query != null) {
            selectQuery.where(query);
        }

        // Set sorting
        if (paging.getSort() != null) {
            List<Order> orderList = createOrderList(builder, root, paging.getSort());
            if (!CollectionUtils.isEmpty(orderList)) {
                selectQuery.orderBy(orderList);
            }
        }

        selectQuery.distinct(true);

        // Get paged results
        TypedQuery<E> typedQuery = getEntityManager().createQuery(selectQuery);
        typedQuery
                .setFirstResult(paging.getPageNumber() * paging.getPageSize())
                .setMaxResults(paging.getPageSize());

        List<T> entityList = typedQuery.getResultList()
                .stream().map(r -> (T) r)
                .collect(Collectors.toList());

        // Get total records count
        Long totalResult = count(builder, selectQuery, root);
        return new PageImpl<>(entityList, paging, totalResult);
    }

    /**
     * Query count for existing criteria query taking into account all where clauses, all joints etc.
     *
     * @param builder Criteria builder
     * @param selectQuery Main query
     * @param root Jpa metadata
     * @param <T> Generic type of the main Jpa type
     * @return Count of records
     */
    protected <T> long count(final CriteriaBuilder builder, final CriteriaQuery<T> selectQuery, Root<T> root) {
        CriteriaQuery<Long> query = createCountQuery(builder, selectQuery, root);
        return getEntityManager().createQuery(query).getSingleResult();
    }

    private <T> CriteriaQuery<Long> createCountQuery(final CriteriaBuilder cb,
                                                     final CriteriaQuery<T> criteria, final Root<T> root) {

        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        final Root<T> countRoot = countQuery.from(criteria.getResultType());

        doJoins(root.getJoins(), countRoot);
        doJoinsOnFetches(root.getFetches(), countRoot);

        countQuery.select(cb.count(countRoot));
        Predicate whereClause = criteria.getRestriction();
        if (whereClause != null) {
            countQuery.where(whereClause);
        }

        countRoot.alias(root.getAlias());
        return countQuery.distinct(criteria.isDistinct());
    }

    @SuppressWarnings("unchecked")
    private void doJoinsOnFetches(Set<? extends Fetch<?, ?>> joins, Root<?> root) {
        doJoins((Set<? extends Join<?, ?>>) joins, root);
    }

    private void doJoins(Set<? extends Join<?, ?>> joins, Root<?> root) {
        for (Join<?, ?> join : joins) {
            Join<?, ?> joined = root.join(join.getAttribute().getName(), join.getJoinType());
            joined.alias(join.getAlias());
            doJoins(join.getJoins(), joined);
        }
    }

    private void doJoins(Set<? extends Join<?, ?>> joins, Join<?, ?> root) {
        for (Join<?, ?> join : joins) {
            Join<?, ?> joined = root.join(join.getAttribute().getName(), join.getJoinType());
            joined.alias(join.getAlias());
            doJoins(join.getJoins(), joined);
        }
    }
}
