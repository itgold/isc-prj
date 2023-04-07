package com.iscweb.integration.cameras.mip.services;

import com.google.common.collect.Lists;
import com.iscweb.common.events.integration.ServerNotAvailableEvent;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.service.IEventHub;
import com.iscweb.integration.cameras.mip.dto.CameraInfoDto;
import com.iscweb.integration.cameras.mip.services.listeners.CameraDeviceEventsListeningSession;
import com.iscweb.integration.cameras.mip.services.listeners.ConfigStatusListeningSession;
import com.iscweb.integration.cameras.mip.services.listeners.EventStatusListeningSession;
import com.iscweb.integration.cameras.mip.services.listeners.InvalidMipSessionException;
import com.iscweb.integration.cameras.mip.services.listeners.IMipListeningSession;
import com.iscweb.integration.cameras.mip.services.mip.IRecorderStatusService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.transport.http.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@Profile("time")
public class MipEventsListener {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private MipCameraService cameraService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private MipTokenService tokenService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IRecorderStatusService recorderStatusService;

    @Getter
    @Setter(onMethod = @__({@Value("${cameras.active.events.enabled:#{true}}")}))
    private boolean enabled;

    @Getter
    @Setter(onMethod = @__({@Value("${cameras.active.events.batchSize:#{100}}")}))
    private int batchSize;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    private final AtomicReference<List<IMipListeningSession>> SESSIONS = new AtomicReference<>(null);
    private static final int MAX_SYNC_SKIP = 12;
    private final AtomicInteger connectionCount = new AtomicInteger(MAX_SYNC_SKIP);

    @Scheduled(cron = "${cameras.active.events.interval}")
    public void checkStats() {
        if (isEnabled()) {
            processStatusCheck();
        }
    }

    private void processStatusCheck() {
        boolean invalidateSessions = false;
        List<IMipListeningSession> sessions = resolveListeningSessions();
        if (!CollectionUtils.isEmpty(sessions)) {
            for (IMipListeningSession session : sessions) {
                try {
                    List<IEvent<ITypedPayload>> events = session.process();
                    log.debug("MIP Event listener :: PROCESSED session {}. Total: {} events, session id: {}", session.name(), events.size(), session.getSessionId());

                    for (IEvent<ITypedPayload> event : events) {
                        getEventHub().post(event);
                    }
                } catch (InvalidMipSessionException e) {
                    invalidateSessions = true;
                    log.error("MIP Event listener :: SKIP Milestone session {} got compromised. Will re-initialize next run", session.name(), e);
                } catch (Exception e) {
                    log.error("MIP Event listener :: ERROR Unable to process Milestone events session {} \n{}", session.name(), session.details(), e);
                }
            }
        } else {
            invalidateSessions = true;
            log.debug("MIP Event listener :: SKIP - no valid listening session");
        }

        if (invalidateSessions) {
            log.debug("MIP Event listener :: INVALIDATE sessions ...");
            sessions = SESSIONS.getAndSet(null);
            if (!CollectionUtils.isEmpty(sessions)) {
                for (IMipListeningSession session : sessions) {
                    try {
                        session.close();
                    } catch (Exception e) {
                        log.warn("Unable to close Milestone events listening session {}", session.name(), e);
                    }
                }
            }

            if(connectionCount.decrementAndGet() == 0) {
                ServerNotAvailableEvent event = new ServerNotAvailableEvent();
                event.setPayload(new ServerNotAvailableEvent.ServerNotAvailablePayload(EntityType.CAMERA, null, "MIP"));
                try {
                    getEventHub().post(event);
                    connectionCount.set(MAX_SYNC_SKIP);
                } catch (ServiceException e) {
                    log.error("Unable to post event", e);
                }
            }
        }
    }

    private List<IMipListeningSession> resolveListeningSessions() {
        List<IMipListeningSession> sessions = SESSIONS.get();
        if (CollectionUtils.isEmpty(sessions)) {
            try {
                final List<IMipListeningSession> newSessions = Lists.newArrayList();
                final List<CameraInfoDto> cameras = getCameraService().listCameras();
                if (!CollectionUtils.isEmpty(cameras)) {
                    newSessions.add(EventStatusListeningSession.start(getCameraService(), getTokenService(), getRecorderStatusService(), cameras));
                    newSessions.add(ConfigStatusListeningSession.start(getCameraService(), getTokenService(), getRecorderStatusService(), cameras));

                    IntStream.range(0, (cameras.size() + batchSize - 1) / batchSize)
                            .mapToObj(i -> cameras.subList(i * batchSize, Math.min(cameras.size(), (i + 1) * batchSize)))
                            .forEach(batch -> {
                                try {
                                    newSessions.add(CameraDeviceEventsListeningSession.start(getCameraService(), getTokenService(), getRecorderStatusService(), batch));
                                } catch (Exception e) {
                                    log.error("MIP Event listener :: BATCH Unable to subscribe to device events. \n{}",
                                            StringUtils.join(batch
                                                    .stream()
                                                    .map(cam -> cam.getInfo().getName())
                                                    .collect(Collectors.toList()), "\n"));
                                }
                            });

                    SESSIONS.set(newSessions);
                    sessions = newSessions;
                } else {
                    log.error("MIP Event listener :: SESSION - SKIP because of no cameras");
                }
            } catch (Exception e) {
                if (e.getCause() != null && (
                        e.getCause() instanceof NoRouteToHostException
                                || e.getCause() instanceof ConnectException
                                || e.getCause() instanceof HTTPException && ((HTTPException)e.getCause()).getResponseCode() == 503)) {
                    log.error("MIP Event listener :: SESSION - Unable to connect to Milestone server");
                } else {
                    log.error("MIP Event listener :: SESSION - Unable to generate Milestone events session(s)", e);
                }
            }
        }

        return sessions;
    }
}
