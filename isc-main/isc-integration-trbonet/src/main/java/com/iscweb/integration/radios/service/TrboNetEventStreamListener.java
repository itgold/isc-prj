package com.iscweb.integration.radios.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.common.service.IEventHub;
import com.iscweb.integration.doors.IEventStreamListener;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * UDP listener implementation for TRBOnet stream events.
 */
@Slf4j
public class TrboNetEventStreamListener extends Thread implements IEventStreamListener {

    // The maximum message size.
    private static final int MESSAGE_BUFFER_SIZE = 4096;

    private final int port;
    private final AtomicBoolean started = new AtomicBoolean(Boolean.FALSE);
    private final TrboNetEventListeningService processor;
    private final byte[] buf = new byte[MESSAGE_BUFFER_SIZE];

    private DatagramSocket socket;

    protected TrboNetEventStreamListener(int port, IEventHub eventHub, ObjectMapper objectMapper) {
        this.port = port;
        this.processor = new TrboNetEventListeningService(eventHub, objectMapper);
    }

    @Override
    public void startListener() throws SocketException {
        socket = new DatagramSocket(port);
        this.processor.startProcessor();
        super.start();

        started.set(Boolean.TRUE);
    }

    @Override
    public void stopListener() {
        this.processor.stopProcessor();

        started.set(Boolean.FALSE);
    }

    public void run() {
        this.setName("TRBONET_EVENTS_LISTENER");

        while (started.get()) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                log.debug("Received event. Address {}:{}, length: {}", address.toString(), port, packet.getLength());

                handleMessage(packet.getData(), packet.getLength());

            } catch (IOException e) {
                log.error("TRBOnet listener I/O error", e);
            } catch (InterruptedException e) {
                log.error("TRBOnet listener canceled", e);
                break;
            }
        }
        
        socket.close();
    }

    protected void handleMessage(byte[] data, int length) throws InterruptedException {
        processor.process(new String(data, 0, length));
    }

    @Builder(builderMethodName = "Builder")
    public static TrboNetEventStreamListener newListener(int port, IEventHub eventHub, ObjectMapper objectMapper) {
        return new TrboNetEventStreamListener(port, eventHub, objectMapper);
    }
}
