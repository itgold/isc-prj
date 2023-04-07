package com.iscweb.service.entity;

import com.google.common.collect.Lists;
import com.iscweb.common.model.IApplicationComponent;
import com.iscweb.common.model.IEntity;
import com.iscweb.common.service.IApplicationService;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Base class for application entity services.
 * Encapsulates common services functionality.
 *
 * @param <E> A JPA type.
 * @param <R> A repository type for the JPA type.
 *
 * @author skurenkov
 */
// Because services work with JPA interfaces and repositories work with particular JPA implementations,
// we cast generic method results to JPA interfaces. This is why we would have type erasure warnings.
@SuppressWarnings({"rawtypes",
                   "unchecked"})
@NoArgsConstructor
@Transactional(transactionManager = "jpaTransactionManager")
public abstract class BaseJpaEntityService<R extends IPersistenceRepository, E extends IEntity> implements IApplicationService, IApplicationComponent {

    /**
     * Default repository.
     */
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private @NonNull R repository;

    /**
     * Searches for all entities, applying pagination.
     *
     * @param paging pagination/sorting parameters
     * @return list of requested entities.
     */
    public List<E> findAll(PageRequest paging) {
        getLogger().debug("- Service: find all invoked with paging criteria.");
        return Lists.newArrayList(getRepository().findAll(paging));
    }

    /**
     * Searches for all entities, applying the specified sorting criteria.
     * {@link BaseJpaEntityService#findAll(PageRequest)} should be used instead, as it supports
     * result pagination.
     *
     * @return list of all entities.
     */
    public List<E> findAll(Sort sort) {
        getLogger().debug("- Service: find all invoked with sorting criteria");
        return Lists.newArrayList(getRepository().findAll(sort));
    }

    /**
     * Searches for all entities.
     *
     * @return list of all entities.
     */
    public List<E> findAll() {
        getLogger().debug("- Service: find all invoked.");
        return Lists.newArrayList(this.findAllIterable());
    }

    public List<E> findAllAuthenticated() {
        return findAll();
    }

    /**
     * Iterable version of #findAll method.
     *
     * @return Iterable result.
     */
    Iterable<E> findAllIterable() {
        getLogger().debug("- Service: find all invoked.");
        return getRepository().findAll();
    }

    /**
     * Looks up an entity by its ID.
     *
     * @param id entity ID.
     * @return entity instance (if found).
     */
    public E findById(Long id) {
        E result = null;
        Optional<E> one = getRepository().findById(id);
        if (one.isPresent()) {
            result = one.get();
        }
        return result;
    }

    /**
     * Persists an entity.
     *
     * @param entity entity to persist.
     * @return updated instance.
     */
    public <T extends E> T createOrUpdate(E entity) {
        return createOrUpdate(entity, false);
    }

    public <T extends E> T createOrUpdate(E entity, boolean flush) {
        R repository = getRepository();
        return (T)  (flush ? repository.saveAndFlush(entity) : repository.save(entity));
    }

    /**
     * Deletes an entity by GUID.
     *
     * @param guid GUID associated with the entity to be deleted.
     */
    public void delete(String guid) {
        getLogger().debug("- Service: delete by id: {} invoked.", guid);
        getRepository().deleteByGuid(guid);
    }

    /**
     * Deletes an entity by ID.
     *
     * @param entityId ID associated with entity to delete.
     */
    public void delete(Long entityId) {
        getLogger().debug("- Service: delete by id: {} invoked.", entityId);
        getRepository().deleteById(entityId);
    }
}
