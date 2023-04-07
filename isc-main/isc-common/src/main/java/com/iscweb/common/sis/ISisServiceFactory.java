package com.iscweb.common.sis;

/**
 * Base interface for third party integration API stubs generation.
 *
 * I.e. in most of cases when deal with a third party application API you can generate client calling code
 * based on metadata provided by regular method prototype declaration. Java provides flexible reflection utilities
 * to collect metadata about specific call: i.e. method name, names and types of parameters etc.
 * Please see <code>ISisService</code> for such third party API interface definition.
 *
 * @param <T> Defines the integration API this specific factory generates stubs for.
 */
public interface ISisServiceFactory<T extends ISisService> {

    /**
     * Builds a particular instance of the integration service by provided class.
     * @param clazz class type for service instantiation.
     * @return a new instance of the target system service.
     */
    T build(Class<T> clazz);
}
