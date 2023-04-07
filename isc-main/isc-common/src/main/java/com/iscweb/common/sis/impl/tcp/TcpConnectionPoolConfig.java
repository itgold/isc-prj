package com.iscweb.common.sis.impl.tcp;

import com.iscweb.common.sis.ISisServiceTransport;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Common configuration to store TCP/IP protocol based transports.
 */
public class TcpConnectionPoolConfig extends GenericObjectPoolConfig<ISisServiceTransport> {

    public static final int DEFAULT_READ_TIMEOUT = 1000;

    @Getter
    @Setter
    private String serverHostname;

    @Getter
    @Setter
    private int serverPort;

    @Getter
    @Setter
    private int readTimeout = TcpConnectionPoolConfig.DEFAULT_READ_TIMEOUT;

    @Override
    protected void toStringAppendFields(final StringBuilder builder) {
        super.toStringAppendFields(builder);
        builder.append(", serverHostname=");
        builder.append(serverHostname);
        builder.append(", serverPort=");
        builder.append(serverPort);
        builder.append(", readTimeout=");
        builder.append(readTimeout);
    }
}
