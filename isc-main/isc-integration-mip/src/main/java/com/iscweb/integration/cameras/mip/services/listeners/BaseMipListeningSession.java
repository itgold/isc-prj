package com.iscweb.integration.cameras.mip.services.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.integration.cameras.mip.dto.CameraInfoDto;
import com.iscweb.integration.cameras.mip.events.MipCameraDeviceEvent;
import com.iscweb.integration.cameras.mip.services.MipCameraService;
import com.iscweb.integration.cameras.mip.services.MipTokenService;
import com.iscweb.integration.cameras.mip.services.streaming.dto.LiveStatusItem;
import com.iscweb.integration.cameras.mip.services.streaming.events.MipCameraStatusEventDto;
import com.iscweb.integration.cameras.mip.services.mip.IRecorderStatusService;
import com.mip.recorderStatus.CameraDeviceStatus;
import com.mip.recorderStatus.Status;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for Milestone listening session implementation
 */
@Slf4j
public abstract class BaseMipListeningSession implements IMipListeningSession {
    @Getter
    private final MipCameraService cameraService;

    @Getter
    private final IRecorderStatusService statusService;

    @Getter
    private final MipTokenService tokenService;

    @Getter
    private final String sessionId;

    /**
     * This field is used for debug purposes only and will be removed in the future.
     */
    @Deprecated
    @Getter
    private final ObjectMapper json;

    public BaseMipListeningSession(MipCameraService cameraService, MipTokenService tokenService, IRecorderStatusService recorderStatusService, String sessionId) {
        this.sessionId = sessionId;
        this.cameraService = cameraService;
        this.tokenService = tokenService;
        this.statusService = recorderStatusService;

        this.json = new ObjectMapper();
    }

    protected List<IEvent<ITypedPayload>> processEvents(Status stats) throws ServiceException {
        return Collections.EMPTY_LIST;
    }

    /**
     * Process session events
     *
     * @return Collection of all events for the camera devices received from Milestone
     */
    public List<IEvent<ITypedPayload>> process() throws InvalidMipSessionException {
        List<IEvent<ITypedPayload>> events = Lists.newArrayList();

        try {
            Status stats = getStatusService().getStatus(getTokenService().currentToken(), getSessionId(), 5000);
            if (stats != null) {
                try {
                    List<IEvent<ITypedPayload>> statusEvents = processEvents(stats);
                    events.addAll(statusEvents);

                    if (stats.getCameraDeviceStatusArray() != null && !CollectionUtils.isEmpty(stats.getCameraDeviceStatusArray().getCameraDeviceStatus())) {
                        log.debug("MIP Event listener :: Received {} DEVICEs status update from {} session. Session id: {}",
                                stats.getCameraDeviceStatusArray().getCameraDeviceStatus().size(), name(), getSessionId());
                        if (log.isTraceEnabled()) {
                            try {
                                log.trace("    {}", getJson().writeValueAsString(stats.getCameraDeviceStatusArray()));
                            } catch (JsonProcessingException e) {
                                log.trace("Unable to generate JSON for the model to debug.", e);
                            }
                        }

                        List<IEvent<? extends ITypedPayload>> deviceEvents = Lists.newArrayList();
                        for (CameraDeviceStatus event : stats.getCameraDeviceStatusArray().getCameraDeviceStatus()) {
                            processCameraDeviceEvent(event, deviceEvents);
                        }

                        events.addAll(deviceEvents
                                .stream()
                                .map(event -> (IEvent<ITypedPayload>) event)
                                .collect(Collectors.toList()));
                    }
                } catch (ServiceException e) {
                    log.error("Unable to post Milestone events", e);
                }
            } else {
                throw new InvalidMipSessionException();
            }
        } catch (Exception e) {
            log.error("MIP Event listener :: Unable to fetch Milestone events", e);
            throw e;
        }

        return events;
    }

    protected void processCameraDeviceEvent(CameraDeviceStatus event, List<IEvent<? extends ITypedPayload>> events) {
        CameraInfoDto camera = getCameraService().findById(event.getDeviceId());
        if (camera != null) {
            if (event.isIsChange()) {
                MipCameraStatusEventDto cameraStatusEvent = new MipCameraStatusEventDto();
                cameraStatusEvent.setStatus(Lists.newArrayList());

                List<LiveStatusItem.StatusType> hardwareFlags = Lists.newArrayList();

                if (event.isError()) {
                    LiveStatusItem statusItem = (event.isErrorNoConnection() || !event.isEnabled())
                            ? LiveStatusItem.fromType(LiveStatusItem.StatusType.ConnectionLost)
                            : LiveStatusItem.fromType(LiveStatusItem.StatusType.ConnectionRestored);
                    cameraStatusEvent.getStatus().add(statusItem);

                    if (event.isErrorNotLicensed()) {
                        hardwareFlags.add(LiveStatusItem.StatusType.LicenseIssue);
                    }
                    if (event.isErrorOverflow()) {
                        hardwareFlags.add(LiveStatusItem.StatusType.OverflowIssue);
                    }
                    if (event.isErrorWritingGop()) {
                        hardwareFlags.add(LiveStatusItem.StatusType.WritingGopIssue);
                    }
                } else {
                    LiveStatusItem connectionItem = LiveStatusItem.fromType(LiveStatusItem.StatusType.ConnectionRestored);
                    cameraStatusEvent.getStatus().add(connectionItem);

                    LiveStatusItem motionItem = event.isMotion()
                            ? LiveStatusItem.fromType(LiveStatusItem.StatusType.FeedWithMotion)
                            : LiveStatusItem.fromType(LiveStatusItem.StatusType.FeedNoMotion);
                    cameraStatusEvent.getStatus().add(motionItem);

                    LiveStatusItem recordingItem = event.isRecording()
                            ? LiveStatusItem.fromType(LiveStatusItem.StatusType.FeedRecorded)
                            : LiveStatusItem.fromType(LiveStatusItem.StatusType.FeedNotRecorded);
                    cameraStatusEvent.getStatus().add(recordingItem);
                }

                if (!CollectionUtils.isEmpty(hardwareFlags)) {
                    LiveStatusItem otherError = LiveStatusItem.fromType(LiveStatusItem.StatusType.CameraMaintenance,
                            StringUtils.join(hardwareFlags.stream().map(Enum::name).collect(Collectors.toList()), ","));
                    cameraStatusEvent.getStatus().add(otherError);
                }

                events.add(new MipCameraDeviceEvent(event.getDeviceId()).payload(cameraStatusEvent));
            }
        } else {
            log.warn("MIP EVENT [CAMERA]: Event for an unknown camera. Camera Id: {}", event.getDeviceId());
        }
    }

    @Override
    public void close() {
        getStatusService().stopStatusSession(getTokenService().currentToken(), getSessionId());
    }
}
