package com.iscweb.integration.doors;

import com.google.common.collect.Lists;
import com.iscweb.common.model.dto.DeviceActionResultDto;
import com.iscweb.common.service.IDeviceActionHandler;
import com.iscweb.common.service.integration.door.IDoorActionsService;
import com.iscweb.integration.doors.models.doors.SaltoExtDoorIdListDto;
import com.iscweb.integration.doors.models.doors.SaltoOnlineDoorActionResultDto;
import com.iscweb.integration.doors.models.request.SaltoEmergencyOpenRequestDto;
import com.iscweb.integration.doors.models.response.SaltoOnlineDoorActionResponseDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.net.ConnectException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of <code>IDoorActionsService</code> service with Salto software.
 *
 * @see IDoorActionsService
 */
@Slf4j
public class SaltoDoorActionsService implements IDoorActionsService {

    @Getter
    private final ISaltoSisService saltoService;

    public SaltoDoorActionsService(ISaltoSisService saltoService) {
        this.saltoService = saltoService;
    }

    @Override
    public String serviceInfo() {
        return null;
    }

    /**
     * @see IDoorActionsService#emergencyOpen(Set)
     */
    @Override
    public DeviceActionResultDto emergencyOpen(Set<String> deviceIds) {
        log.debug("Salto Emergency Open action ...");
        DeviceActionResultDto result;
        try {
            SaltoEmergencyOpenRequestDto request = new SaltoEmergencyOpenRequestDto();
            SaltoExtDoorIdListDto doorIds = new SaltoExtDoorIdListDto();
            doorIds.setDoorIds(Lists.newArrayList(deviceIds));
            request.setDoorIds(doorIds);

            SaltoOnlineDoorActionResponseDto response = getSaltoService().emergencyOpenDoor(request);
            result = parseResult(response, deviceIds);
        } catch (Exception e) {
            result = generateErrorResponse("emergency open", e);
        }

        return result;
    }

    /**
     * @see IDoorActionsService#emergencyClose(Set)
     */
    @Override
    public DeviceActionResultDto emergencyClose(Set<String> deviceIds) {
        log.debug("Salto Emergency Close action ...");
        DeviceActionResultDto result;
        try {
            SaltoEmergencyOpenRequestDto request = new SaltoEmergencyOpenRequestDto();
            SaltoExtDoorIdListDto doorIds = new SaltoExtDoorIdListDto();
            doorIds.setDoorIds(Lists.newArrayList(deviceIds));
            request.setDoorIds(doorIds);

            SaltoOnlineDoorActionResponseDto response = getSaltoService().emergencyCloseDoor(request);
            result = parseResult(response, deviceIds);
        } catch (Exception e) {
            result = generateErrorResponse("emergency close", e);
        }

        return result;
    }

    /**
     * @see IDoorActionsService#endEmergency(Set)
     */
    @Override
    public DeviceActionResultDto endEmergency(Set<String> deviceIds) {
        log.debug("Salto End Emergency action ...");
        DeviceActionResultDto result;
        try {
            SaltoEmergencyOpenRequestDto request = new SaltoEmergencyOpenRequestDto();
            SaltoExtDoorIdListDto doorIds = new SaltoExtDoorIdListDto();
            doorIds.setDoorIds(Lists.newArrayList(deviceIds));
            request.setDoorIds(doorIds);

            SaltoOnlineDoorActionResponseDto response = getSaltoService().emergencyEnd(request);
            result = parseResult(response, deviceIds);
        } catch (Exception e) {
            result = generateErrorResponse("cancel emergency mode", e);
        }

        return result;
    }

    /**
     * @see IDoorActionsService#openDoor(Set)
     */
    @Override
    public DeviceActionResultDto openDoor(Set<String> deviceIds) {
        log.debug("Salto Open Door action ...");
        DeviceActionResultDto result;
        try {
            SaltoEmergencyOpenRequestDto request = new SaltoEmergencyOpenRequestDto();
            SaltoExtDoorIdListDto doorIds = new SaltoExtDoorIdListDto();
            doorIds.setDoorIds(Lists.newArrayList(deviceIds));
            request.setDoorIds(doorIds);

            SaltoOnlineDoorActionResponseDto response = getSaltoService().openDoor(request);
            result = parseResult(response, deviceIds);
        } catch (Exception e) {
            result = generateErrorResponse("open door", e);
        }

        return result;
    }

    private DeviceActionResultDto parseResult(SaltoOnlineDoorActionResponseDto result, Collection<String> deviceIds) {
        List<SaltoOnlineDoorActionResultDto> results = result.getResultsList().getResults();

        List<DeviceActionResultDto.DeviceActionError> errors = results
                .stream()
                .map(r -> {
                    DeviceActionResultDto.DeviceActionError actionError = null;
                    if (r.getErrorCode() > 0) {
                        String errorMessage = r.getErrorMessage() + ". Door id: " + r.getId();
                        actionError = new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.ACTION_FAILED.name(), errorMessage);
                    }

                    return actionError;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        DeviceActionResultDto.ActionResult actionResult = errors.size() == deviceIds.size()
                ? DeviceActionResultDto.ActionResult.FAILURE
                : (errors.size() > 0
                ? DeviceActionResultDto.ActionResult.WARNING : DeviceActionResultDto.ActionResult.SUCCESS);

        return DeviceActionResultDto.builder()
                .status(actionResult)
                .errors(errors)
                .build();
    }

    private DeviceActionResultDto generateErrorResponse(String action, Exception e) {
        String errorMessage = e.getMessage();
        if (e.getCause() != null && e.getCause() instanceof ConnectException) {
            log.error("Failed to trigger Salto door {} action. Salto service is not available.", action);
            errorMessage = "Salto server is not available";
        } else {
            log.error("Failed to trigger Salto door {} action", action, e);
            if (e.getCause() != null) {
                errorMessage = e.getCause().getMessage();
            }
        }

        return DeviceActionResultDto.builder()
                .status(DeviceActionResultDto.ActionResult.FAILURE)
                .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.ACTION_FAILED.name(), errorMessage)))
                .build();
    }
}
