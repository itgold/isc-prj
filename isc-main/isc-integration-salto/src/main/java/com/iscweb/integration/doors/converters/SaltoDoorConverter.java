package com.iscweb.integration.doors.converters;

import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.metadata.DoorStatus;
import com.iscweb.common.model.metadata.DoorType;
import com.iscweb.common.util.ObjectUtils;
import com.iscweb.integration.doors.models.doors.SaltoDbDoorDto;
import com.iscweb.integration.doors.models.enums.DoorOpeningMode;

public class SaltoDoorConverter extends BaseConverter<DoorDto, SaltoDbDoorDto> {

    @Override
    public SaltoDbDoorDto toModel(DoorDto dto) {
        SaltoDbDoorDto model = new SaltoDbDoorDto();
        model.setId(dto.getExternalId());
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setBatteryStatus(dto.getBatteryLevel());
        model.setOpeningMode(convert(dto.getOpeningMode()));
        return model;
    }

    @Override
    public DoorDto toDto(SaltoDbDoorDto door) {
        DoorDto dto = new DoorDto();
        dto.setId(door.getId());
        dto.setExternalId(door.getId());
        dto.setStatus(DoorStatus.ACTIVATED);
        dto.setType(DoorType.UNKNOWN);
        dto.setName(door.getName());
        dto.setDescription(door.getDescription());
        dto.setBatteryLevel(door.getBatteryStatus());
        dto.setUpdateRequired(ObjectUtils.getBoolean(door.getUpdateRequired()));
        dto.setOpeningMode(door.getOpeningMode().toDto());
        return dto;
    }

//    TODO(eric): These two DoorOpeningMode classes are unnecessarily confusing.
    private DoorOpeningMode convert(com.iscweb.common.model.metadata.DoorOpeningMode metadata) {
        DoorOpeningMode result = DoorOpeningMode.STANDARD;
        if (metadata != null) {
            for (DoorOpeningMode mode : DoorOpeningMode.values()) {
                if (mode.name().equalsIgnoreCase(metadata.name())) {
                    result = mode;
                }
            }
        }

        return result;
    }
}
