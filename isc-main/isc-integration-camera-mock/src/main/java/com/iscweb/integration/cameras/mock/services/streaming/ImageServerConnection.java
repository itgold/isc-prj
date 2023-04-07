package com.iscweb.integration.cameras.mock.services.streaming;

import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraStreamingStoppedEventDto;
import com.iscweb.common.exception.ImageServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.ConcurrentException;

import java.io.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class ImageServerConnection implements Closeable {

    private final ConnectionInfo connection;
    private final AtomicReference<StreamReadThread> streamingThread = new AtomicReference<>(null);

    private final CameraEventListener eventListener;

    public ImageServerConnection(String cameraId, String videoPath, CameraEventListener eventListener) throws IOException {
        this.eventListener = eventListener;

        if (videoPath != null) {
            this.connection = new ConnectionInfo(videoPath, cameraId);
        } else {
            throw new IOException("Camera not found");
        }
    }

    @Override
    public void close() {
        stopStreaming();
        this.connection.close();
    }

    /**
     * Start video/audio streaming.
     *
     * @return
     * @throws ImageServerException
     */
    public InputStream startStreaming() throws ImageServerException {
        log.info("Start streaming for camera {}", connection.cameraId);
        StreamReadThread thread;
        try {
            thread = StreamReadThread.create(connection, (cameraId, event) -> {
                eventListener.onEvent(cameraId, event);

                if (event instanceof MipCameraStreamingStoppedEventDto) {
                    close();
                }
            });
            streamingThread.set(thread);
            return thread.getOutput();
        } catch (Exception e) {
            close();
            throw new ImageServerException("Unable to start streaming", e);
        }
    }

    /**
     * Send <code>StopStreamingCommand</code> to the Image Service and stop listening thread.
     */
    public void stopStreaming() {
        log.info("Stop streaming for camera {}", connection.cameraId);
        StreamReadThread thread = streamingThread.getAndSet(null);
        if (thread != null) {
            thread.cancel();
        }
    }

    public static class ConnectionInfo implements Closeable {
        final String cameraId;

        final String fileName;

        // Stores the managed object.
        private volatile InputStream stream = null;

        public ConnectionInfo(final String fileName, String cameraId) {
            this.cameraId = cameraId;
            this.fileName = fileName;
        }

        public InputStream getStream() throws ConcurrentException {
            // use a temporary variable to reduce the number of reads of the volatile field
            InputStream result = stream;

            if (result == null) {
                synchronized (this) {
                    result = stream;
                    if (result == null) {
                        stream = result = initialize();
                    }
                }
            }

            return result;
        }

        private InputStream initialize() {
            boolean loadedFromClasspath = true;
            InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
            if (stream == null) {
                loadedFromClasspath = false;
                File file = new File(fileName);
                if (file.exists()) {
                    try {
                        stream = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        log.trace("Unable to read file", e);
                    }
                }
            }

            if (stream == null) {
                log.warn("Mock video file {} not found!", fileName);
            } else {
                log.warn("Mock video file loaded from: {} / from classpath: {}", fileName, loadedFromClasspath);
            }

            return stream;
        }

        @Override
        public void close() {
            if (stream != null) {
                synchronized (this) {
                    InputStream result = stream;
                    stream = null;
                    if (result != null) {
                        try {
                            result.close();
                        } catch (Exception e) {
                            log.trace("Unable to clean up resources", e);
                        }
                    }
                }
            }
        }
    }
}
