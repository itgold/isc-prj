package com.iscweb.integration.cameras.mip.services.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.integration.cameras.mip.dto.CameraInfoDto;
import com.iscweb.integration.cameras.mip.events.BuiltInEventTypes;
import com.iscweb.integration.cameras.mip.services.MipCameraService;
import com.iscweb.integration.cameras.mip.services.MipTokenService;
import com.iscweb.integration.cameras.mip.services.mip.IRecorderStatusService;
import com.mip.recorderStatus.Status;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * Event processing events listening session implementation
 */
@Slf4j
public class EventStatusListeningSession extends BaseMipListeningSession {

    private static final String LISTENER_NAME = "EVENTS_STATS";

    @Getter
    private final List<CameraInfoDto> cameras;

    @Override
    public String name() {
        return EventStatusListeningSession.LISTENER_NAME;
    }

    private EventStatusListeningSession(String sessionId, MipCameraService cameraService, MipTokenService tokenService, IRecorderStatusService statusService, List<CameraInfoDto> cameras) {
        super(cameraService, tokenService, statusService, sessionId);
        this.cameras = ImmutableList.copyOf(cameras);
    }

    public static EventStatusListeningSession start(MipCameraService cameraService, MipTokenService tokenService, IRecorderStatusService statusService, List<CameraInfoDto> cameras) {
        final String token = tokenService.currentToken();
        final String sessionId = statusService.startStatusSession(token);
        log.debug("MIP Event listener :: CREATE SESSION {}. Token: {}, session id: {} ...", EventStatusListeningSession.LISTENER_NAME, token, sessionId);

        List<String> subscribeTheseEventIds = Lists.newArrayList(
                BuiltInEventTypes.CommunicationError.getGuid(),
                BuiltInEventTypes.CommunicationStarted.getGuid(),
                BuiltInEventTypes.CommunicationStopped.getGuid(),
                BuiltInEventTypes.DeviceSettingsChanged.getGuid(),
                BuiltInEventTypes.DeviceSettingsChangedError.getGuid(),
                BuiltInEventTypes.HardwareSettingsChanged.getGuid(),
                BuiltInEventTypes.HardwareSettingsChangedError.getGuid(),
                BuiltInEventTypes.LiveClientFeedRequested.getGuid(),
                BuiltInEventTypes.LiveClientFeedTerminated.getGuid(),
                BuiltInEventTypes.MotionStarted.getGuid(),
                BuiltInEventTypes.MotionStopped.getGuid(),
                BuiltInEventTypes.RecordingStarted.getGuid(),
                BuiltInEventTypes.RecordingStopped.getGuid()
        );

        com.mip.recorderStatus.ArrayOfGuid ids = new com.mip.recorderStatus.ArrayOfGuid();
        ids.getGuid().addAll(subscribeTheseEventIds);

        log.warn("MIP Event listener :: subscribeEventStatus ...");
        statusService.subscribeEventStatus(token, sessionId, ids);

        return new EventStatusListeningSession(sessionId, cameraService, tokenService, statusService, cameras);
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

        return Collections.EMPTY_LIST;
    }
}
