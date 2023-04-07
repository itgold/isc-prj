package com.iscweb.common.sis.impl.tcp;

import com.iscweb.common.sis.ISisServiceTransport;
import lombok.Getter;
import lombok.Setter;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.NoRouteToHostException;
import java.net.Socket;

/**
 * Base abstract class helping to create Tcp/Ip protocol based transports.
 */
public abstract class BaseTcpSisServiceTransport implements ISisServiceTransport {

    @Getter
    @Setter
    private TcpConnectionPoolConfig config;

    @Getter
    @Setter
    private Socket socket;

    @Getter
    @Setter
    private OutputStream output;

    @Getter
    @Setter
    private InputStream input;

    public interface ISisServiceReader extends Closeable {
        int read() throws IOException;
        void close() throws IOException;
    }

    /**
     * @see ISisServiceTransport#connect()
     */
    @Override
    public void connect() throws IOException {
        try {
            Socket newSocket = new Socket(getConfig().getServerHostname(), getConfig().getServerPort());
            newSocket.setSoTimeout(getConfig().getReadTimeout());
            newSocket.setKeepAlive(true);

            setOutput(newSocket.getOutputStream());
            setInput(newSocket.getInputStream());

            setSocket(newSocket);
        } catch (NoRouteToHostException e) {
            throw new NoRouteToHostException("NoRouteToHostException invoking " +
                    getConfig().getServerHostname() + ":" + getConfig().getServerPort() + ": " + e.getMessage());
        }
    }

    /**
     * @see ISisServiceTransport#close()
     */
    @Override
    public void close() throws IOException {
        if (null != socket && !socket.isClosed()) {
            socket.close();
        }
    }

    /**
     * @see ISisServiceTransport#isConnected()
     */
    @Override
    public boolean isConnected() {
        return null == socket || !socket.isConnected();
    }

    protected void write(final byte[] data) throws IOException {
        if (null != socket) {
            output.write(data);
        } else {
            throw new IOException("Connection is not established");
        }
    }

    protected ISisServiceReader read() {

        final InputStreamReader reader = new InputStreamReader(input);

        return new ISisServiceReader() {
            @Override
            public int read() throws IOException {
                return reader.read();
            }

            @Override
            public void close() {
                // reader.close(); ????
            }
        };
    }
}
