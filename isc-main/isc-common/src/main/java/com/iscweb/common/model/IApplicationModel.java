package com.iscweb.common.model;

import com.iscweb.common.model.metadata.ConverterType;

import java.io.Serializable;

/**
 * An interface for all domain model classes.
 */
public interface IApplicationModel extends Serializable {

    /**
     * Used for resolving converter type for current Phantasm entity.
     *
     * @return type of converter that might be used with this entity.
     */
    ConverterType getConverterType();
}
