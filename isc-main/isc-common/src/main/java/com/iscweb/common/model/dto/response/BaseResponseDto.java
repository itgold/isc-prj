package com.iscweb.common.model.dto.response;

import com.iscweb.common.model.dto.IDto;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public abstract class BaseResponseDto<T, E> implements IDto {
    private final T value;
    private final E error;
    private final String message;
    private final ZonedDateTime timestamp;
    private final Status status;

    public enum Status { SUCCEEDED, FAILED }

    public BaseResponseDto(final T value) {
        this(value, Status.SUCCEEDED, null, null);
    }

    public BaseResponseDto(final E error, final String message) {
        this(null, Status.FAILED, error, message);
    }

    public BaseResponseDto(final T value, final Status status, final E error, final String message) {
        this.timestamp = ZonedDateTime.now();
        this.status = status;
        this.value = value;
        this.error = error;
        this.message = message;
    }
}
