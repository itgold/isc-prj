package com.iscweb.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that is used to apply cache node update when one of the <code>CompositeCacheEntry.UpdateType</code> CRUD updates happened.
 * All CREATE/UPDATE methods marked with such annotation expected to have the first method parameter to be a Dto for the entity to be
 * created/updated and returns updated entity Dto.
 * All DELETE methods marked with such annotation expected to have the first method parameter to be a GUID for the entity to be deleted.
 * <br/>
 * For details on processing this see <code>CompositeCacheManagementAspect</code> class.
 *
 * @author dmorozov
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CompositeCacheEntry {

    enum UpdateType {
        CREATE,
        UPDATE,
        DELETE
    }

    /**
     * Update type.
     *
     * @return Defines type of cache entity update.
     */
    UpdateType value();
}
