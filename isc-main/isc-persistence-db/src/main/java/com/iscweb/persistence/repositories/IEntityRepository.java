package com.iscweb.persistence.repositories;

import com.iscweb.common.model.IEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Contract for all persistence repositories.
 *
 * @param <E> the type of {@link IEntity}.
 */
public interface IEntityRepository<E extends IEntity> extends PagingAndSortingRepository<E, String> {

    /**
     * Finds a specific {@link IEntity} by its ID. IDs are always in string form
     *
     * @return found object or null.
     */
    @Override
    Optional<E> findById(String id);
}
