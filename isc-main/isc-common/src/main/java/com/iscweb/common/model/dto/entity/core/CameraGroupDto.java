package com.iscweb.common.model.dto.entity.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraGroupDto {
    private String groupId;
    private String name;
    private String description;
}
