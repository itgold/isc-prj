package com.iscweb.integration.cameras.mip.services.streaming;

import com.iscweb.common.util.StringUtils;
import com.iscweb.integration.cameras.mip.services.streaming.commands.LiveCommandStatus;
import com.iscweb.integration.cameras.mip.services.streaming.events.MipCameraStatusEventDto;
import com.iscweb.integration.cameras.mip.services.streaming.events.MipCameraStreamingStoppedEventDto;
import com.iscweb.integration.cameras.mip.utils.BitConverter;
import com.iscweb.common.util.IoUtils;
import com.iscweb.integration.cameras.mip.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.iscweb.integration.cameras.mip.utils.XmlUtils.IMG_SERVER_MSG_FOOTER;

@Slf4j
public final class StreamReadThread extends Thread {

    private final boolean mjpegStream;
    private final CameraEventListener eventListener;
    private final ImageServerConnection.ConnectionInfo connectionInfo;
    private final PipedInputStream in;
    private final OutputStream out;
    private byte[] bytesReceived = new byte[IoUtils.BUFFER_SIZE];
    private final AtomicBoolean stopped = new AtomicBoolean(Boolean.FALSE);

    private StreamReadThread(ImageServerConnection.ConnectionInfo connectionInfo,
                             boolean mjpegStream,
                             CameraEventListener eventListener) throws IOException {
        this.connectionInfo = connectionInfo;
        this.mjpegStream = mjpegStream;
        this.eventListener = eventListener;

        this.in = new PipedInputStream();
        this.out = new PipedOutputStream(this.in);
    }

    public static StreamReadThread create(ImageServerConnection.ConnectionInfo connectionInfo,
                                          boolean mjpegStream,
                                          CameraEventListener eventListener) throws IOException {
        StreamReadThread thread = new StreamReadThread(connectionInfo, mjpegStream, eventListener);
        thread.setName("VIDEO_STREAM_" + connectionInfo.cameraId);
        thread.start();
        return thread;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted() && !stopped.get()) {
                // Buffer size housekeeping
                int curBufSize = IoUtils.BUFFER_SIZE;
                int bytes = IoUtils.recvUntil(connectionInfo.input, bytesReceived, 0, IoUtils.BUFFER_SIZE);
                if (bytes < 0) {
                    throw new IOException("Receive error A");
                }

                if (bytesReceived[0] == '<') {
                    // This is XML status message
                    String statusMessage = new String(bytesReceived, 0, bytes);
                    LiveCommandStatus status = XmlUtils.fromXml(statusMessage, LiveCommandStatus.class);

                    MipCameraStatusEventDto statusEvent = new MipCameraStatusEventDto();
                    statusEvent.setStatusTime(status.getStatus().getStatusTime());
                    statusEvent.setStatus(status.getStatus().getStatusItems());
                    eventListener.onEvent(connectionInfo.cameraId, connectionInfo.streamId, statusEvent);

                    continue;
                }

                if (bytesReceived[0] == 'I') {
                    // Image
                    ImageInfo h = ParseHeader(bytesReceived, 0, bytes);

                    // Takes two first bytes
                    bytes = IoUtils.recvFixed(connectionInfo.input, bytesReceived, 0, 2);
                    if (2 != Math.abs(bytes)) {
                        throw new Exception("Unable to read from camera server connection");
                    }

                    if (bytesReceived[0] == (byte) 0xFF && bytesReceived[1] == (byte) 0xD8) {
                        int neededBufSize = h.Length;
                        if (neededBufSize > curBufSize) {
                            int newBufSize = RoundUpBufSize(neededBufSize);
                            curBufSize = newBufSize;
                            byte b0 = bytesReceived[0];
                            byte b1 = bytesReceived[1];
                            bytesReceived = new byte[curBufSize];
                            bytesReceived[0] = b0;
                            bytesReceived[1] = b1;
                        }

                        bytes = IoUtils.recvFixed(connectionInfo.input, bytesReceived, 2, neededBufSize - 2);
                        bytes = Math.abs(bytes);
                        if (bytes != neededBufSize - 2) {
                            throw new Exception("Receive error B");
                        }
                    } else {
                        bytes = IoUtils.recvFixed(connectionInfo.input, bytesReceived, 2, 30);
                        if (Math.abs(bytes) != 30) {
                            throw new Exception("Receive error C");
                        }

                        short dataType = BitConverter.toInt16(IoUtils.GetReversedSubarray(bytesReceived, 0, 2), 0);
                        int length = BitConverter.toInt32(IoUtils.GetReversedSubarray(bytesReceived, 2, 4), 0);
                        short codec = BitConverter.toInt16(IoUtils.GetReversedSubarray(bytesReceived, 6, 2), 0);
                        short seqNo = BitConverter.toInt16(IoUtils.GetReversedSubarray(bytesReceived, 8, 2), 0);
                        short flags = BitConverter.toInt16(IoUtils.GetReversedSubarray(bytesReceived, 10, 2), 0);
                        long timeStampSync = BitConverter.toInt64(IoUtils.GetReversedSubarray(bytesReceived, 12, 8), 0);
                        long timeStampPicture = BitConverter.toInt64(IoUtils.GetReversedSubarray(bytesReceived, 20, 8), 0);
                        int reserved = BitConverter.toInt32(IoUtils.GetReversedSubarray(bytesReceived, 28, 4), 0);

                        boolean isKeyFrame = (flags & 0x01) == 0x01;
                        int payloadLength = length - 32;

                        if (payloadLength > curBufSize) {
                            int newBufSize = RoundUpBufSize(payloadLength);
                            curBufSize = newBufSize;
                            bytesReceived = new byte[curBufSize];
                        }

                        //this appears to be the correct amount of data
                        bytes = IoUtils.recvFixed(connectionInfo.input, bytesReceived, 0, payloadLength);
                        bytes = Math.abs(bytes);
                        if (bytes != payloadLength) {
                            throw new IOException("Receive error D");
                        }
                    }

                    byte[] ms = Arrays.copyOf(bytesReceived, h.Length);
                    h.Data = ms;
                    streamFrame(h);
                }
            }
        } catch (Exception e) {
            if (!stopped.get()) {
                log.error("Streaming got busted!", e);
            }
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.trace("Unable to close output stream", e);
                }
            }
        }

        eventListener.onEvent(connectionInfo.cameraId, connectionInfo.streamId, new MipCameraStreamingStoppedEventDto());
    }

    private void streamFrame(ImageInfo h) throws IOException {
        if (mjpegStream) {
            out.write(("--BoundaryStringis\r\n" +
                    "Content-Type: image/jpeg\r\n" +
                    "Content-Length: " + h.Length +
                    IMG_SERVER_MSG_FOOTER).getBytes());
            out.write((byte[]) h.Data);
            out.write(IMG_SERVER_MSG_FOOTER.getBytes());
            out.flush();
            // log.debug("================== Stream video image. Size: {}, Time: {}", h.Length, new Date());
        } else {
            out.write((byte[]) h.Data);
        }

        // LastJPEG = bytesReceived;
        // String fileName = String.format("videoData%s.jpg", fileCounter.incrementAndGet());
        // log.debug("================== Stream video image. File {}, Size: {}", fileName, h.Length);
        // Files.write(Paths.get("/home/denis/Temp/" + fileName), (byte[]) h.Data);
    }

    private int RoundUpBufSize(int needed) {
        int roundup = (needed / 1024) * 1024 / 100 * 130;
        return roundup;
    }

    private static ImageInfo ParseHeader(byte[] buf, int offset, int bytes) {
        ImageInfo h = new ImageInfo();
        h.Length = 0;
        h.Type = "";

        String response = new String(buf, offset, bytes);
        String[] headers = response.split("\n");
        for (String header : headers) {
            String[] keyVal = header.split(":");
            String value = keyVal.length > 1 ? trim(keyVal[1], '\r') : StringUtils.EMPTY;
            if ("content-length".equalsIgnoreCase(keyVal[0]) && keyVal.length > 1) {
                h.Length = Integer.parseInt(value);
            }

            if ("content-type".equalsIgnoreCase(keyVal[0]) && keyVal.length > 1) {
                h.Type = value.toLowerCase();
            }

            if ("current".equalsIgnoreCase(keyVal[0]) && keyVal.length > 1) {
                h.Current = value;
            }

            if ("next".equalsIgnoreCase(keyVal[0]) && keyVal.length > 1) {
                h.Next = value;
            }

            if ("prev".equalsIgnoreCase(keyVal[0]) && keyVal.length > 1) {
                h.Prev = value;
            }
        }

        return h;
    }

    private static String trim(String text, char trimChar) {
        // matches multiple trimChar from the beginning or multiple trimChar at the end and replaces them with void.
        return text.replaceAll("(^" + trimChar + "*|" + trimChar + "*$)", "").trim();
    }

    public void cancel() {
        stopped.set(Boolean.TRUE);
        interrupt();
    }

    public InputStream getOutput() {
        return in;
    }

    public static class ImageInfo {
        public int Length;
        public String Type;
        public String Current;
        public String Next;
        public String Prev;
        public Object Data;

        public ImageInfo() {
            Length = -1;
            Type = "";
            Current = "";
            Next = "";
            Prev = "";
            Data = null;
        }
    }
}
