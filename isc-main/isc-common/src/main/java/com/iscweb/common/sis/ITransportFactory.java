package com.iscweb.common.sis;

import com.iscweb.common.sis.exceptions.SisConnectionException;

/**
 * ITransportFactory defines base contract for integration layer transport factory.
 *
 * The caller code is not suppose to create transport for each integration service call but delegate
 * that to the correspondent transport factory. This way we can abstract out usage of connection pools
 * under the hood and hide details of protocol level transport creation.
 */
public interface ITransportFactory {

    /**
     * Establishes and returns a new service transport connection.
     * @return requested connection.
     * @throws SisConnectionException connection failed to establish.
     */
    ISisServiceTransport getConnection() throws SisConnectionException;

}
