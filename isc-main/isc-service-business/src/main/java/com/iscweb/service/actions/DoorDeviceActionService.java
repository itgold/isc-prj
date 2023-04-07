package com.iscweb.service.actions;

import com.iscweb.common.model.dto.DeviceActionResultDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.service.IDeviceActionHandler;
import com.iscweb.common.service.integration.door.IDoorActionsService;
import com.iscweb.service.DoorService;
import com.iscweb.service.entity.IDoorActionService;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.MAP_ACTION_PERMISSION;

@Service
public class DoorDeviceActionService extends BaseDeviceActionService<DoorDto, DoorService> implements IDoorActionService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IDoorActionsService doorActionsService;

    @PreAuthorize(MAP_ACTION_PERMISSION)
    public DeviceActionResultDto emergencyClose(Set<String> guids) {
        DeviceActionResultDto result;

        List<DoorDto> doors = getService().findByGuidIn(guids);
        if (!CollectionUtils.isEmpty(doors)) {
            result = getDoorActionsService().emergencyClose(doors
                    .stream()
                    .map(DoorDto::getExternalId)
                    .collect(Collectors.toSet()));

            if (doors.size() < guids.size()) {
                Set<String> missingDevices = guids
                        .stream()
                        .filter(guid -> doors
                                .stream()
                                .anyMatch(d -> StringUtils.equals(d.getId(), guid))
                        ).collect(Collectors.toSet());
                result = DeviceActionResultDto.fromResult(result,
                        new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                                "Unable to resolve the door(s) with id: " + StringUtils.join(missingDevices, ",")));
            }
        } else {
            result = DeviceActionResultDto.builder()
                    .status(DeviceActionResultDto.ActionResult.FAILURE)
                    .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                            "Unable to resolve the door(s) with id: " + StringUtils.join(guids, ","))))
                    .build();
        }

        return result;
    }

    @PreAuthorize(MAP_ACTION_PERMISSION)
    public DeviceActionResultDto endEmergencyMode(Set<String> guids) {
        DeviceActionResultDto result;

        List<DoorDto> doors = getService().findByGuidIn(guids);
        if (!CollectionUtils.isEmpty(doors)) {
            result = getDoorActionsService().endEmergency(doors
                    .stream()
                    .map(DoorDto::getExternalId)
                    .collect(Collectors.toSet()));

            if (doors.size() < guids.size()) {
                Set<String> missingDevices = guids
                        .stream()
                        .filter(guid -> doors
                                .stream()
                                .anyMatch(d -> StringUtils.equals(d.getId(), guid))
                        ).collect(Collectors.toSet());
                result = DeviceActionResultDto.fromResult(result,
                        new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                                "Unable to resolve the door(s) with id: " + StringUtils.join(missingDevices, ",")));
            }
        } else {
            result = DeviceActionResultDto.builder()
                    .status(DeviceActionResultDto.ActionResult.FAILURE)
                    .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                            "Unable to resolve the door(s) with id: " + StringUtils.join(guids, ","))))
                    .build();
        }

        return result;
    }

    @PreAuthorize(MAP_ACTION_PERMISSION)
    public DeviceActionResultDto emergencyOpen(Set<String> guids) {
        DeviceActionResultDto result;

        List<DoorDto> doors = getService().findByGuidIn(guids);
        if (!CollectionUtils.isEmpty(doors)) {
            result = getDoorActionsService().emergencyOpen(doors
                    .stream()
                    .map(DoorDto::getExternalId)
                    .collect(Collectors.toSet()));

            if (doors.size() < guids.size()) {
                Set<String> missingDevices = guids
                        .stream()
                        .filter(guid -> doors
                                .stream()
                                .anyMatch(d -> StringUtils.equals(d.getId(), guid))
                        ).collect(Collectors.toSet());
                result = DeviceActionResultDto.fromResult(result,
                        new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                                "Unable to resolve the door(s) with id: " + StringUtils.join(missingDevices, ",")));
            }
        } else {
            result = DeviceActionResultDto.builder()
                    .status(DeviceActionResultDto.ActionResult.FAILURE)
                    .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                            "Unable to resolve the door(s) with id: " + StringUtils.join(guids, ","))))
                    .build();
        }

        return result;
    }

    @PreAuthorize(MAP_ACTION_PERMISSION)
    public DeviceActionResultDto openDoor(Set<String> guids) {
        DeviceActionResultDto result;

        List<DoorDto> doors = getService().findByGuidIn(guids);
        if (!CollectionUtils.isEmpty(doors)) {
            result = getDoorActionsService().openDoor(doors
                    .stream()
                    .map(DoorDto::getExternalId)
                    .collect(Collectors.toSet()));

            if (doors.size() < guids.size()) {
                Set<String> missingDevices = guids
                        .stream()
                        .filter(guid -> doors
                                .stream()
                                .anyMatch(d -> StringUtils.equals(d.getId(), guid))
                        ).collect(Collectors.toSet());
                result = DeviceActionResultDto.fromResult(result,
                        new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                                "Unable to resolve the door(s) with id: " + StringUtils.join(missingDevices, ",")));
            }
        } else {
            result = DeviceActionResultDto.builder()
                    .status(DeviceActionResultDto.ActionResult.FAILURE)
                    .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                            "Unable to resolve the door(s) with id: " + StringUtils.join(guids, ","))))
                    .build();
        }

        return result;
    }
}
