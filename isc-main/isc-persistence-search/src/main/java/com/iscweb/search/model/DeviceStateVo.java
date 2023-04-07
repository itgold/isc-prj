package com.iscweb.search.model;

import com.iscweb.common.model.dto.DeviceStateItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Collection;

@Data
@TypeAlias(DeviceStateVo.TYPE)
@Document(indexName = DeviceStateVo.INDEX)
@EqualsAndHashCode(callSuper = true)
public class DeviceStateVo extends BaseSearchEntityVo {

    public static final String INDEX = "states";
    public static final String TYPE = "device_state";

    @Field(type = FieldType.Keyword)
    String deviceId;

    @Field(type = FieldType.Auto)
    Collection<DeviceStateItemDto> state;

    public DeviceStateVo() {
        super(DeviceStateVo.TYPE);
    }
}
