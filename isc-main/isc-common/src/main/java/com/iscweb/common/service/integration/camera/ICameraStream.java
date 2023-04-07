package com.iscweb.common.service.integration.camera;

import com.iscweb.common.exception.ImageServerException;

import java.io.Closeable;
import java.io.InputStream;

/**
 * Abstraction layer to represent camera video stream.
 *
 * Note: The camera API is streaming video data together with video metadata in proprietary format.
 * Using this abstraction we can hide details of the underlie system and provide clean video stream to be consumed by UI.
 */
public interface ICameraStream extends Closeable {

    InputStream stream() throws ImageServerException;

}
