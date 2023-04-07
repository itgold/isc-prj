package com.iscweb.integration.cameras.mip.services.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
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

import static java.util.Collections.*;

/**
 * Config change events listening session implementation
 */
@Slf4j
public class ConfigStatusListeningSession extends BaseMipListeningSession {

    private static final String LISTENER_NAME = "CONFIG_STATS";

    @Getter
    private final List<CameraInfoDto> cameras;

    @Override
    public String name() {
        return ConfigStatusListeningSession.LISTENER_NAME;
    }

    private ConfigStatusListeningSession(String sessionId, MipCameraService cameraService, MipTokenService tokenService, IRecorderStatusService statusService, List<CameraInfoDto> cameras) {
        super(cameraService, tokenService, statusService, sessionId);
        this.cameras = ImmutableList.copyOf(cameras);
    }

    public static ConfigStatusListeningSession start(MipCameraService cameraService, MipTokenService tokenService, IRecorderStatusService statusService, List<CameraInfoDto> cameras) {
        final String token = tokenService.currentToken();
        final String sessionId = statusService.startStatusSession(token);
        log.debug("MIP Event listener :: CREATE SESSION {}. Token: {}, session id: {} ...", ConfigStatusListeningSession.LISTENER_NAME, token, sessionId);

        // Subscribe to configuration changes
        // Every time a change occurs, you will be informed next time you query the status
        log.warn("MIP Event listener :: subscribeConfigurationStatus ...");
        statusService.subscribeConfigurationStatus(token, sessionId, true);

        return new ConfigStatusListeningSession(sessionId, cameraService, tokenService, statusService, cameras);
    }

    protected List<IEvent<ITypedPayload>> processEvents(Status stats) {
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
}
