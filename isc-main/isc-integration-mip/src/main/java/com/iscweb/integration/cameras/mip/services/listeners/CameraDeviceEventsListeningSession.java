package com.iscweb.integration.cameras.mip.services.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.integration.cameras.mip.dto.CameraInfoDto;
import com.iscweb.integration.cameras.mip.services.MipCameraService;
import com.iscweb.integration.cameras.mip.services.MipTokenService;
import com.iscweb.integration.cameras.mip.services.mip.IRecorderStatusService;
import com.mip.recorderStatus.Status;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.*;

/**
 * Camera Device events listening session implementation
 */
@Slf4j
public class CameraDeviceEventsListeningSession extends BaseMipListeningSession {

    private static final String LISTENER_NAME = "DEVICE_STATS";

    @Getter
    private final List<CameraInfoDto> cameras;

    @Override
    public String name() {
        return CameraDeviceEventsListeningSession.LISTENER_NAME;
    }

    private CameraDeviceEventsListeningSession(String sessionId, MipCameraService cameraService, MipTokenService tokenService, IRecorderStatusService statusService, List<CameraInfoDto> cameras) {
        super(cameraService, tokenService, statusService, sessionId);
        this.cameras = ImmutableList.copyOf(cameras);
    }

    public static CameraDeviceEventsListeningSession start(MipCameraService cameraService, MipTokenService tokenService, IRecorderStatusService statusService, List<CameraInfoDto> cameras) {
        final String token = tokenService.currentToken();
        final String sessionId = statusService.startStatusSession(token);
        log.debug("MIP Event listener :: CREATE SESSION {}. Token: {}, session id: {} ...", CameraDeviceEventsListeningSession.LISTENER_NAME, token, sessionId);

        com.mip.recorderStatus.ArrayOfGuid idList = new com.mip.recorderStatus.ArrayOfGuid();
        idList.getGuid().addAll(
                cameras
                        .stream()
                        .map(CameraInfoDto::getCameraId)
                        .collect(Collectors.toList())
        );
        statusService.subscribeDeviceStatus(token, sessionId, idList);

        return new CameraDeviceEventsListeningSession(sessionId, cameraService, tokenService, statusService, cameras);
    }

    @Override
    protected List<IEvent<ITypedPayload>> processEvents(Status stats) throws ServiceException {
        if (log.isTraceEnabled()) {
            log.trace("MIP Event listener :: Received status for {} session", name());
            try {
                log.trace("    {}", getJson().writeValueAsString(stats));
            } catch (JsonProcessingException e) {
                log.trace("Unable to generate JSON for the model to debug.", e);
            }
        }

        return EMPTY_LIST;
    }

    public String details() {
        return getCameras().stream().map(c -> c.getInfo().getName()).collect(Collectors.joining(",\n"));
    }
}
