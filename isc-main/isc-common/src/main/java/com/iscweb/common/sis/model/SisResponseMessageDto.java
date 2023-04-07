package com.iscweb.common.sis.model;

import com.iscweb.common.model.dto.IDto;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SisResponseMessageDto implements IDto {

    public static final Charset ENCODING = StandardCharsets.UTF_8;

    private final byte[] data;

    public SisResponseMessageDto(final byte[] data) {
        this.data = data;
    }

    public String toString() {
        return new String(data, ENCODING);
    }
}
