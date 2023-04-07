package com.iscweb.common.util;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public final class IoUtils {

    public static int BUFFER_SIZE = 1024 * 16;

    // block should be end with \r\n\r\n
    private static final int BLOCK_SEPARATOR_LENGTH = 4;

    public static String readTextFromStream(BufferedReader in) throws IOException {
        StringBuilder response = new StringBuilder();

        String inputLine;
        do {
            inputLine = in.readLine();
            if (!StringUtils.isEmpty(inputLine)) {
                response.append(inputLine);
            }

        } while(!StringUtils.isEmpty(inputLine));

        return response.toString();
    }

    public static int recvUntil(InputStream stream, byte[] buf, int offset, int size) throws IOException {
        int i, bytes;
        int got = 0;
        int ended = BLOCK_SEPARATOR_LENGTH;

        while (got < size && ended > 0) {
            i = offset + got;
            bytes = stream.read(buf, i, 1);
            if (bytes == -1) { // can't read more
                ended = -1;
                got = -1;
                break;
            }

            if (buf[i] == '\r' || buf[i] == '\n') {
                ended--;
            } else {
                ended = BLOCK_SEPARATOR_LENGTH;
            }

            got += bytes;
        }

        if (got > size) {
            throw new IOException("Buffer overflow");
        }

        return ended == 0 ? got : -got;
    }

    public static int recvFixed(InputStream stream, byte[] buf, int offset, int size) throws IOException {
        int bytes, get;
        int miss = size;
        int got = 0;
        int maxb = IoUtils.BUFFER_SIZE;

        do {
            get = Math.min(miss, maxb);
            bytes = stream.read(buf, offset + got, get);
            got += bytes;
            miss -= bytes;
        } while (got < size);

        if (got > size) {
            throw new IOException("Buffer overflow");
        }

        int rez = -got;
        if (size >= BLOCK_SEPARATOR_LENGTH) {
            int i = offset + got - BLOCK_SEPARATOR_LENGTH;
            if (buf[i] == '\r' && buf[i + 1] == '\n' && buf[i + 2] == '\r' && buf[i + 3] == '\n') {
                rez = got;
            }
        }

        return rez;

    }

    public static byte[] GetReversedSubarray(byte[] array, int start, int length) {
        byte[] newArray = new byte[length];
        for (int i = 0; i < length; i++) {
            newArray[length - i - 1] = array[i + start];
        }

        return newArray;
    }
}
