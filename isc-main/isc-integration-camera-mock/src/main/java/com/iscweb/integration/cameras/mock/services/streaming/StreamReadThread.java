package com.iscweb.integration.cameras.mock.services.streaming;

import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraStreamingStoppedEventDto;
import com.iscweb.common.util.IoUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class to read mjpeg file and stream as separate frames like video cameras do
 *
 * The original mjpeg files can be prepared as the following:
 * ffmpeg -i SOURCE_FILE.mp4 -c:v mjpeg -f mjpeg output.mjpeg
 */
@Slf4j
public final class StreamReadThread extends Thread {

    private final CameraEventListener eventListener;
    private final ImageServerConnection.ConnectionInfo connectionInfo;
    private final PipedInputStream in;
    private final OutputStream out;
    private final AtomicBoolean stopped = new AtomicBoolean(Boolean.FALSE);

    public static final String IMG_SERVER_MSG_FOOTER = "\r\n\r\n";

    private StreamReadThread(ImageServerConnection.ConnectionInfo connectionInfo, CameraEventListener eventListener) throws IOException {
        this.connectionInfo = connectionInfo;
        this.eventListener = eventListener;

        this.in = new PipedInputStream();
        this.out = new PipedOutputStream(this.in);
    }

    public static StreamReadThread create(ImageServerConnection.ConnectionInfo connectionInfo,
                                          CameraEventListener eventListener) throws IOException {
        StreamReadThread thread = new StreamReadThread(connectionInfo, eventListener);
        thread.setName("VIDEO_STREAM_" + connectionInfo.cameraId);
        thread.start();
        return thread;
    }

    @Override
    public void run() {
        ByteArrayOutputStream jpgOut = null;
        try {
            int cur, prev = 0;
            while (!isInterrupted() && !stopped.get()) {
                try (InputStream inputStream = connectionInfo.getStream()) {
                    if (inputStream == null) {
                        break;
                    }
                    while (((cur = inputStream.read()) >= 0)) {
                        if (prev == 0xFF && cur == 0xD8) { // start of image
                            jpgOut = new ByteArrayOutputStream(IoUtils.BUFFER_SIZE);
                            jpgOut.write((byte) prev);
                        }
                        if (jpgOut != null) {
                            jpgOut.write((byte) cur);
                        }

                        if (prev == 0xFF && cur == 0xD9) { // end of image
                            int numberOfBytesToWrite = jpgOut.size();
                            out.write(("--BoundaryStringis\r\n" +
                                    "Content-Type: image/jpeg\r\n" +
                                    "Content-Length: " + numberOfBytesToWrite +
                                    IMG_SERVER_MSG_FOOTER).getBytes());
                            jpgOut.writeTo(out);
                            out.write(IMG_SERVER_MSG_FOOTER.getBytes());
                            out.flush();

                            try {
                                jpgOut.close();
                            } catch (Exception e) {
                                log.trace("Unable to close buffer stream", e);
                            }
                            jpgOut = null;
                        }

                        prev = cur;
                    }

                    connectionInfo.close();
                }
            }

        } catch (Exception e) {
            if (!stopped.get()) {
                log.error("Streaming got busted!", e);
            }
        } finally {
            if (jpgOut != null) {
                try {
                    jpgOut.close();
                } catch (IOException e) {
                    log.trace("Unable to close buffer stream", e);
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.trace("Unable to close output stream", e);
                }
            }

            connectionInfo.close();
        }

        eventListener.onEvent(connectionInfo.cameraId, new MipCameraStreamingStoppedEventDto());
    }

    public void cancel() {
        stopped.set(Boolean.TRUE);
        interrupt();
    }

    public InputStream getOutput() {
        return in;
    }
}
