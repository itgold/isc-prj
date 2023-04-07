package com.iscweb.common.model.event.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Camera event payload changed sub-state details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraEventItem {
    private String code;
    private String value;
}
