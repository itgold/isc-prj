package com.iscweb.service.converter;

import lombok.Getter;

/**
 * Enum for the SecurityLevel of a converted object.
 * The SecurityLevel describes how much sensitive data is populated in the dto/jpa object during conversion.
 * {@code
 * Convert.my(enclave).setSecurityLevel(SecurityLevel.USER).setScope(Scope.ALL).boom()
 * }
 */
public enum SecurityLevel {

    /**
     * Converted object will contain limited data that can only be used for the object identification.
     */
    IDENTITY(10),

    /**
     * Converted object will contain no sensitive data. When no scope is set, this is the default.
     */
    BASIC(30),

    /**
     * Converted object will contain sensitive data sanitized with respect to the user.
     */
    USER(60),

    /**
     * Converted object will contain all sensitive data, un-sanitized. This object is for internal use
     * and should never be returned to FE or external client.
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
    SecurityLevel(int level) {
        this.level = level;
    }

    /**
     * Checks if current scope is greater than a given scope.
     * @param scope converter scope to do a check against.
     * @return true if the scope level is greater.
     */
    public boolean gt(SecurityLevel scope) {
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
    public boolean gte(SecurityLevel scope) {
        boolean result = false;
        if (scope != null) {
            result = getLevel() >= scope.getLevel();
        }
        return result;
    }
}
