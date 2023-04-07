package com.iscweb.integration.doors;

import java.net.SocketException;

/**
 * Event stream listener.
 */
public interface IEventStreamListener {

    void startListener() throws SocketException;

    void stopListener();
}
