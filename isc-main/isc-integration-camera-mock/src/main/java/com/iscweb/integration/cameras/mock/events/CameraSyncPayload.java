package com.iscweb.integration.cameras.mock.events;

import com.iscweb.common.events.CommonEventTypes;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.integration.cameras.mock.dto.CameraInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CameraSyncPayload implements ITypedPayload {

    private CameraInfoDto data;

    @Override
    public String getType() {
        return CommonEventTypes.CAMERA_SYNC.code();
    }
}
