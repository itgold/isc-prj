package com.iscweb.common.security;

/**
 * An interface that represents operation that runs with the system privileges.
 * Should be used in try-with-resource block for auto-closing security context.
 */
public interface ISystemUser extends AutoCloseable {

    /**
     * Overridden version of AutoCloseable.close method with no exception declaration.
     *
     * @see AutoCloseable#close()
     */
    @Override
    void close();
}
