package com.iscweb.common.model.event.camera;

import com.iscweb.common.model.event.ITypedPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Common Camera event payload.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraEventPayload implements ITypedPayload {

    private String cameraId;

    private String streamId;

    private String type;

    private long time;

    private List<CameraEventItem> events;
}
