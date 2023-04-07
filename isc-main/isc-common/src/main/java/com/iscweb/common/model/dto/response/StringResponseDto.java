package com.iscweb.common.model.dto.response;

public class StringResponseDto<E> extends BaseResponseDto<String, E> {

    public StringResponseDto(String value) {
        super(value);
    }

    public StringResponseDto(E error, String message) {
        super(error, message);
    }

    public static <E> StringResponseDto<E> of(final String value) {
        return new StringResponseDto<E>(value);
    }

    public static <E> StringResponseDto<E> of(final E error, final String message) {
        return new StringResponseDto<E>(error, message);
    }
}
