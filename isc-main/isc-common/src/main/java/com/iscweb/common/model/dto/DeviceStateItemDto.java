package com.iscweb.common.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Base class implements device sub-state information.
 */
@Data
@Builder
public class DeviceStateItemDto implements IDto {

    private String type;
    private String value;
    private ZonedDateTime updated;

    public DeviceStateItemDto() {
        this.updated = ZonedDateTime.now();
    }

    public DeviceStateItemDto(String type, String value) {
        this();

        this.type = type;
        this.value = value;
    }

    public DeviceStateItemDto(String type, String value, ZonedDateTime updated) {
        this(type, value);

        this.updated = updated != null ? updated : ZonedDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceStateItemDto that = (DeviceStateItemDto) o;
        return type.equals(that.type) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
