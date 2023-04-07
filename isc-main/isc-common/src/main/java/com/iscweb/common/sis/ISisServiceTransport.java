package com.iscweb.common.sis;

import com.iscweb.common.sis.model.SisResponseMessageDto;

import java.io.Closeable;
import java.io.IOException;

/**
 * Base interface for all implementations of third party integration API communication transport.
 *
 * I.e. you can send payloads to the third party integration API using Tcp/Ip or Http or Unix Socket media, etc.
 */
public interface ISisServiceTransport extends Closeable {

    /**
     * Sends given payload over the network to the target system.
     * @param payload payload to deliver.
     * @return target system response.
     * @throws IOException if operation failed because of IO error.
     */
    SisResponseMessageDto send(String payload) throws IOException;

    /**
     * Connects transport to the target system.
     * @throws IOException if operation failed.
     */
    void connect() throws IOException;

    /**
     * Chacks if this transport is connected to the target system.
     * @return true if transport connected.
     */
    boolean isConnected();
}
