package com.iscweb.integration.cameras.mip.services.streaming;

import com.iscweb.integration.cameras.mip.services.streaming.commands.LiveCommand;
import com.iscweb.integration.cameras.mip.services.streaming.commands.StopStreamingCommand;
import com.iscweb.integration.cameras.mip.services.streaming.events.MipCameraStreamingStoppedEventDto;
import com.iscweb.common.exception.ImageServerException;
import com.iscweb.common.util.IoUtils;
import com.iscweb.integration.cameras.mip.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class ImageServerConnection implements Closeable {

    private final AtomicReference<ConnectionInfo> connection = new AtomicReference<>(null);
    private final AtomicReference<StreamReadThread> streamingThread = new AtomicReference<>(null);
    private final Object socketMutex = new Object();

    private final String cameraId;
    private final String streamId;
    private final CameraEventListener eventListener;

    public ImageServerConnection(String cameraId, String streamId, CameraEventListener eventListener) {
        this.cameraId = cameraId;
        this.streamId = streamId;
        this.eventListener = eventListener;
    }

    public void connect(String host, int port, boolean ssl) throws IOException {
        if (connection.get() != null) {
            close();
        }

        if (ssl) {
            try {
                SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                SSLSocket socket = (SSLSocket) factory.createSocket(host, port);

                /*
                 * send http request
                 *
                 * Before any application data is sent or received, the
                 * SSL socket will do SSL handshaking first to set up
                 * the security attributes.
                 *
                 * SSL handshaking can be initiated by either flushing data
                 * down the pipe, or by starting the handshaking by hand.
                 *
                 * Handshaking is started manually in this example because
                 * We want the sendCommand to catch all messaging related exceptions.
                 */
                socket.startHandshake();

                connection.set(new ConnectionInfo(socket, cameraId, streamId));
            } catch (Exception e) {
                throw new IOException("Unable to connect Image Server", e);
            }
        } else {
            Socket socket = new Socket(host, port);
            connection.set(new ConnectionInfo(socket, cameraId, streamId));
        }
    }

    @Override
    public void close() {
        if (connection.get() != null) {
            stopStreaming();

            final ConnectionInfo socket = connection.getAndSet(null);
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    // do nothing
                }
            }
        }
    }

    /**
     * Send new command to the Image Service.
     *
     * @param request
     * @param responseClass
     * @param <REQUEST>
     * @param <RESPONSE>
     * @return
     * @throws ImageServerException Exception will be thrown in case:
     *  - Unable to send command to the service
     *  - The connection is in streaming mode and tye command is not a <code>StopStreamingCommand</code>.
     */
    public <REQUEST, RESPONSE> RESPONSE sendCommand(REQUEST request, Class<RESPONSE> responseClass) throws ImageServerException {
        RESPONSE result = null;
        final ConnectionInfo socket = connection.get();
        if (socket != null) {
            try {
                synchronized (socketMutex) {
                    final String payload = XmlUtils.toXml(request);
                    socket.output.print(payload);
                    socket.output.print(XmlUtils.IMG_SERVER_MSG_FOOTER);
                    if (socket.output.checkError()) {
                        throw new ImageServerException("Unable to send command to ImageServer");
                    }

                    if (responseClass != null) {
                        String response = IoUtils.readTextFromStream(socket.inputReader);
                        result = XmlUtils.fromXml(response, responseClass);
                    } else {
                        result = null;
                    }
                }
            } catch (JAXBException e) {
                throw new ImageServerException("Unable to marshal/unmarshal payload", e);
            } catch (IOException e) {
                throw new ImageServerException("Unable to read response", e);
            } catch (ImageServerException e) {
                throw e;
            }
        } else {
            throw new ImageServerException("Connection is closed");
        }

        return result;
    }

    /**
     * Start video/audio streaming.
     *
     * @return
     * @throws ImageServerException
     */
    public InputStream startStreaming(boolean mjpegStream) throws ImageServerException {
        LiveCommand liveCommand = new LiveCommand();
        liveCommand.setCompressionRate(40);
        sendCommand(liveCommand, null);

        StreamReadThread thread = null;
        try {
            thread = StreamReadThread.create(connection.get(), mjpegStream, (cameraId, streamId, event) -> {
                eventListener.onEvent(cameraId, streamId, event);

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
        try {
            log.info("Stop streaming for camera {}", cameraId);
            StopStreamingCommand stopCommand = new StopStreamingCommand();
            sendCommand(stopCommand, null);
        } catch (Exception e) {
            log.debug("Unable to send stop live streaming command", e);
        }

        StreamReadThread thread = streamingThread.getAndSet(null);
        if (thread != null) {
            thread.cancel();
        }
    }

    public static class ConnectionInfo implements Closeable {
        final Socket socket;
        final String cameraId;
        final String streamId;

        final InputStream input;
        final BufferedReader inputReader;
        final PrintWriter output;

        public ConnectionInfo(final Socket socket, String cameraId, String streamId) throws IOException {
            this.socket = socket;
            this.cameraId = cameraId;
            this.streamId = streamId;

            this.input = socket.getInputStream();
            this.inputReader = new BufferedReader(new InputStreamReader(this.input));
            this.output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        }

        @Override
        public void close() {
            try {
                this.inputReader.close();
            } catch (Exception e) {
                // do nothing
            }

            try {
                this.input.close();
            } catch (Exception e) {
                // do nothing
            }

            try {
                this.output.close();
            } catch (Exception e) {
                // do nothing
            }

            try {
                this.socket.close();
            } catch (Exception e) {
                // do nothing
            }
        }
    }
}
