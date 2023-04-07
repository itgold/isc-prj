package com.iscweb.common.model.event;

import com.iscweb.common.model.dto.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceIncrementalUpdatePayload<T extends BaseEntityDto> implements IIncrementalUpdatePayload {

    private String type;

    private String deviceId;

    private String code;

    private String description;

    private T device;

    public String getNotes() { return null; }
    public String getUser() { return null; }
}
