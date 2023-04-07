package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.iscweb.common.util.StringUtils;
import lombok.Data;

@Data
public class SaltoDbBinaryDto implements ISaltoDto {
    private byte[] data;

    @JsonCreator
    public static SaltoDbBinaryDto forValue(String data) {
        SaltoDbBinaryDto dto = new SaltoDbBinaryDto();
        dto.setData(StringUtils.hexStringToBytes(data));
        return dto;
    }

    @JsonValue
    public String toValue() {
        return StringUtils.bytesToHex(this.getData());
    }
}
