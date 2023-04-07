package com.iscweb.common.events.integration.camera;

import com.iscweb.common.events.CommonEventTypes;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.event.ITypedPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraUpdatePayload implements ITypedPayload {

    private CameraDto data;

    @Override
    public String getType() {
        return CommonEventTypes.CAMERA_UPDATE.code();
    }
}
