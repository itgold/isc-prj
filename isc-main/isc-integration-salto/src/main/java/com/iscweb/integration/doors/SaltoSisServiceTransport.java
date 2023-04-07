package com.iscweb.integration.doors;

import com.iscweb.common.sis.impl.tcp.BaseTcpSisServiceTransport;
import com.iscweb.common.sis.impl.tcp.TcpConnectionPoolConfig;
import com.iscweb.common.sis.model.SisResponseMessageDto;
import com.iscweb.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SaltoSisServiceTransport extends BaseTcpSisServiceTransport {

    private static final String PROTOCOL = "STP";
    private static final String VERSION = "01";
    private static final char HEADER_SEPARATOR = '/';
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final int MAX_BODY_SIZE = 512 * 1024;

    /**
     * Factory method for salto service transport.
     * @param config tcp configuration.
     * @return a new instance of salto transport initialized.
     */
    public static SaltoSisServiceTransport buildWith(TcpConnectionPoolConfig config) {
        SaltoSisServiceTransport result = new SaltoSisServiceTransport();
        result.setConfig(config);

        return result;
    }

    @Override
    public SisResponseMessageDto send(String payload) throws IOException {
        final byte[] data = payload.getBytes(UTF8);
        final String header = SaltoSisServiceTransport.createHeader(data.length);
        final byte[] headerData = header.getBytes(UTF8);

        write(headerData);
        write(data);

        ShipMessageContext readContext = new ShipMessageContext();
        ReaderState readState = ReaderState.MESSAGE_START;
        try (ISisServiceReader reader = read()) {
            while (ReaderState.MESSAGE_END != readState && ReaderState.INVALID != readState) {
                int character = reader.read();
                if (-1 == character) {
                    break; // response connection closed
                }
                readState = readState.read(readContext, (byte) character);
            }
        }

        if (ReaderState.MESSAGE_END == readState) {
            return readContext.getMessage();
        }

        throw new IOException("Unable to parse response. The connection is busted. Closing...");
    }

    protected static String createHeader(int contentLength) {
        return PROTOCOL + HEADER_SEPARATOR + VERSION  + HEADER_SEPARATOR + contentLength + HEADER_SEPARATOR;
    }

    private static class ShipMessageContext {
        private int headerLength = 0;
        private byte[] header = new byte[6];
        private int messageLength = 0;
        private byte[] messageData = null;

        private String version;
        private int size;

        public int getMessageLength() {
            return messageLength;
        }

        public SisResponseMessageDto getMessage() {
            return new SisResponseMessageDto(messageData);
        }

        public int updateBody(byte data) {
            messageData[messageLength++] = data;
            return messageLength;
        }

        public void resetHeader() {
            headerLength = 0;
        }

        public int getHeaderLength() {
            return headerLength;
        }

        public String getHeader() {
            return new String(header, 0, headerLength, UTF8);
        }

        public int updateHeader(byte data) {
            if (headerLength < header.length) {
                header[headerLength++] = data;
            } else {
                log.error("BAD SALTO response. Unexpected header length. Character: {}", (char) data);
            }

            return headerLength;
        }

        // ---------------------------------
        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
            this.messageData = new byte[size];
        }
    }

    @FunctionalInterface
    public interface ReadStateResolver {
        ReaderState read(ShipMessageContext context, byte data);
    }

    enum ReaderState implements ReadStateResolver {
        NO_STATE_CHANGE(),

        MESSAGE_END((context, data) -> NO_STATE_CHANGE),

        INVALID((context, data) -> NO_STATE_CHANGE),

        BODY((context, data) -> {
            ReaderState result = NO_STATE_CHANGE;
            if (context.updateBody(data) == context.getSize()) {
                log.trace("STP Reader: BODY done");
                result = MESSAGE_END;
            }
            return result;
        }),

        HEADER_SIZE((context, data) -> {
            ReaderState result = NO_STATE_CHANGE;
            if (HEADER_SEPARATOR == data) {
                try {
                    context.setSize(Integer.parseInt(context.getHeader()));
                    log.trace("STP Reader: SIZE done, message size: " + context.getSize());
                    result = ReaderState.BODY;
                } catch (NumberFormatException e) {
                    result = ReaderState.INVALID;
                }
            } else {
                context.updateHeader(data);
            }

            return result;
        }),

        HEADER_VERSION((context, data) -> {
            ReaderState result = NO_STATE_CHANGE;
            if (HEADER_SEPARATOR == data && context.getHeaderLength() == VERSION.length()) {
                context.setVersion(context.getHeader());
                context.resetHeader();
                log.trace("STP Reader: VERSION done");
                result = ReaderState.HEADER_SIZE;
            }
            if (HEADER_SEPARATOR != data) {
                context.updateHeader(data);
            }

            return result;
        }),

        MESSAGE_START((context, data) -> {
            ReaderState result = NO_STATE_CHANGE;
            if (HEADER_SEPARATOR == data && context.getHeaderLength() == PROTOCOL.length()) {
                if (PROTOCOL.equals(context.getHeader())) {
                    context.resetHeader();
                    log.trace("STP Reader: PROTOCOL done");
                    result = ReaderState.HEADER_VERSION;
                } else {
                    result = ReaderState.INVALID;
                }
            }
            if (HEADER_SEPARATOR != data) {
                context.updateHeader(data);
            }

            return result;
        });

        private final ReadStateResolver resolver;

        ReaderState(final ReadStateResolver resolver) {
            this.resolver = resolver;
        }

        ReaderState() {
            this.resolver = null;
        }

        public ReaderState read(ShipMessageContext context, byte data) {
            ReaderState state = null != this.resolver ? this.resolver.read(context, data) : NO_STATE_CHANGE;
            return null != state && NO_STATE_CHANGE != state ? state : this;
        }
    }
}
