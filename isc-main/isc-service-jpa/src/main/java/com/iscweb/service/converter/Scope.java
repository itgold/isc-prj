package com.iscweb.service.converter;

import com.iscweb.common.model.LazyLoadingField;
import lombok.Getter;

import java.util.List;

/**
 * Enum for the scope of a converted object.
 * The scope describes how much data is populated in the dto/jpa object during conversion.
 * {@code
 * Convert.my(enclave).setScope(Scope.USER).use(SubmissionUtils.USER).boom()
 * }
 */
public enum Scope {

    /**
     * Converted object will contain limited data that can only be used for the object identification.
     */
    IDENTITY(10),

    /**
     * Converted object will contain basic data about the object. This is the default value for the conversion scope if it is not explicitly provided.
     */
    BASIC(30),

    /**
     * Converted object will contain basic data + extra metadata about the object.
     */
    METADATA(60),

    /**
     * Converted object will contain all data about the object including metadata and some relations.
     */
    ALL(100);

    /**
     * This variable defines the level of restrictiveness of a conversion process.
     * Lower the number, more open it is. The range is from 0 to 100.
     */
    @Getter
    private final int level;

    /**
     * Converter scope level initializing constructor.
     * @param level current scope level.
     */
    Scope(int level) {
        this.level = level;
    }

    /**
     * Checks if current scope is greater than a given scope.
     * @param scope converter scope to do a check against.
     * @return true if the scope level is greater.
     */
    public boolean gt(Scope scope) {
        boolean result = false;
        if (scope != null) {
            result = getLevel() > scope.getLevel();
        }
        return result;
    }

    /**
     * Checks if current scope is greater than or equal to a given scope.
     * @param scope converter scope to do a check against.
     * @return true if the scope level is greater or equal.
     */
    public boolean gte(Scope scope) {
        boolean result = false;
        if (scope != null) {
            result = getLevel() >= scope.getLevel();
        }
        return result;
    }

    public static Scope fromLazyField(List<LazyLoadingField> fields) {
        Scope scope = Scope.BASIC;
        if (fields != null) {
            if (fields.contains(LazyLoadingField.METADATA)) {
                scope = Scope.METADATA;
            }
            if (fields.contains(LazyLoadingField.DEVICE_STATE) || fields.contains(LazyLoadingField.PARENT_REGION)) {
                scope = Scope.ALL;
            }
        }

        return scope;
    }
}
