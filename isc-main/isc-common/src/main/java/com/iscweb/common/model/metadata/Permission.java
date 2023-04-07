package com.iscweb.common.model.metadata;

/**
 * Defines a set of permissions that can be used by the enclave.
 * At this point enclaves support 3 operations:
 * <ul>
 * <li>Read: users from this enclave can access this entity.</li>
 * <li>Create: users from this enclave can create new entities.</li>
 * <li>Update: users from this enclave can update entities.</li>
 * </ul>
 */
public enum Permission {
    READ,
    CREATE,
    UPDATE,
}
