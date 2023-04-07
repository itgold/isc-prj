package com.iscweb.common.model.dto;

/**
 * An application DTO that contains all the base functionality for all DTO objects.
 */
public abstract class BaseDto implements IDto {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
