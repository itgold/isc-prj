package com.iscweb.persistence.repositories;

import com.iscweb.common.model.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

/**
 * A repository interface which constrains entities to be searched by an identifier of type Long.
 *
 * @param <T> the type of {@link IEntity}.
 */
public interface IPersistenceRepository<T extends IEntity> extends JpaRepository<T, Long> {

    void deleteById(@NonNull Long id);

    void deleteByGuid(String guid);
}
