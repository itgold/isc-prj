package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.BaseExternalEntityDto;
import com.iscweb.common.model.metadata.CameraStatus;
import com.iscweb.common.model.metadata.CameraType;
import com.iscweb.common.model.metadata.ConverterType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.util.List;
import java.util.Set;

/**
 * Camera DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CameraDto extends BaseExternalEntityDto {

    private CameraStatus status;
    private CameraType type;

    private CameraGroupDto cameraGroup;
    private boolean live;
    private String cameraServiceHost;
    private int cameraServicePort;
    private boolean cameraServiceSsl;
    private List<CameraStreamDto> streams;
    private Set<DeviceStateItemDto> state;

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.CAMERA;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CAMERA;
    }

    @Override
    public String getEntityId() {
        return getId();
    }
}
