package com.iscweb.component.time;

import com.iscweb.common.events.integration.OnDemandDeviceSyncEvent;
import com.iscweb.common.events.integration.OnDemandDeviceSyncPayload;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.event.ServerErrorEvent;
import com.iscweb.common.model.event.camera.CameraIncrementalSyncStartEvent;
import com.iscweb.common.model.event.door.DoorIncrementalSyncStartEvent;
import com.iscweb.common.model.event.radio.RadioIncrementalSyncStartEvent;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.common.security.ISystemUser;
import com.iscweb.common.service.IApplicationService;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.IEventSubscriber;
import com.iscweb.common.service.integration.IUserSyncService;
import com.iscweb.common.service.integration.camera.ICameraSyncService;
import com.iscweb.common.service.integration.door.IDoorSyncService;
import com.iscweb.common.service.integration.radio.IRadioSyncService;
import com.iscweb.common.sis.exceptions.SisConnectionException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.SocketException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * This service is responsible for running scheduled content polling for active users.
 */
@Slf4j
@Component
public class SyncScheduler implements IApplicationService {

    private static final long INIT_TIMEOUT = 5000;

    @Getter
    @Setter(onMethod = @__({@Autowired(required = false)}))
    private List<IUserSyncService> userSyncServices;

    @Getter
    @Setter(onMethod = @__({@Value("${users.active.sync.enabled:#{true}}")}))
    private boolean enabledUserSync;

    @Getter
    @Setter(onMethod = @__({@Autowired(required = false)}))
    private List<IDoorSyncService> doorSyncServices;

    @Getter
    @Setter(onMethod = @__({@Value("${doors.active.sync.enabled:#{true}}")}))
    private boolean enabledDoorSync;

    @Getter
    @Setter(onMethod = @__({@Autowired(required = false)}))
    private List<ICameraSyncService> cameraSyncServices;

    @Getter
    @Setter(onMethod = @__({@Value("${cameras.active.sync.enabled:#{true}}")}))
    private boolean enabledCameraSync;

    @Getter
    @Setter(onMethod = @__({@Autowired(required = false)}))
    private List<IRadioSyncService> radioSyncServices;

    @Getter
    @Setter(onMethod = @__({@Value("${radios.active.sync.enabled:#{true}}")}))
    private boolean enabledRadioSync;

    @Getter
    @Setter(onMethod = @__({@Autowired, @Qualifier("mainAsyncTaskScheduler")}))
    private TaskScheduler taskScheduler;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @PostConstruct
    public void initialize() {
        getTaskScheduler().schedule(() -> {
            log.info("cron.sync=Initial sync ...");
            String batchId = UUID.randomUUID().toString();
            try {
                syncActiveUsersData(true, ApplicationSecurity.SYSTEM_USER, batchId);
                syncDoorsData(true, ApplicationSecurity.SYSTEM_USER, batchId);
                syncCamerasData(true, ApplicationSecurity.SYSTEM_USER, batchId);
                syncRadiosData(true, ApplicationSecurity.SYSTEM_USER, batchId);
                log.info("cron.sync=Initial sync FINISHED.");
            } catch (Exception e) {
                log.error("Failed to do initial sync", e);
            }
        }, new Date(System.currentTimeMillis() + INIT_TIMEOUT));

        getEventHub().register(OnDemandDeviceSyncEvent.class, (IEventSubscriber<OnDemandDeviceSyncEvent, OnDemandDeviceSyncPayload>) event -> {
            if (event.getPayload().getEntityTypes().contains(EntityType.DOOR)) {
                syncDoorsData(false, event.getPayload().getTriggeredByUser(), event.getPayload().getJobId());
            }
            if (event.getPayload().getEntityTypes().contains(EntityType.USER)) {
                syncActiveUsersData(false, event.getPayload().getTriggeredByUser(), event.getPayload().getJobId());
            }
            if (event.getPayload().getEntityTypes().contains(EntityType.CAMERA)) {
                syncCamerasData(false, event.getPayload().getTriggeredByUser(), event.getPayload().getJobId());
            }
            if (event.getPayload().getEntityTypes().contains(EntityType.RADIO)) {
                syncRadiosData(false, event.getPayload().getTriggeredByUser(), event.getPayload().getJobId());
            }
        });
    }

    /**
     * Main method to trigger meta content refresh request in accordance with configured cron interval.
     */
    @Scheduled(cron = "${users.active.sync.interval}")
    public void syncActiveUsersData() {
        syncActiveUsersData(true, ApplicationSecurity.SYSTEM_USER, UUID.randomUUID().toString());
    }

    protected void syncActiveUsersData(boolean scheduled, String user, String batchId) {
        if (getUserSyncServices() != null && isEnabledUserSync()) {
            DoorIncrementalSyncStartEvent syncEvent = new DoorIncrementalSyncStartEvent();
            try {
                syncEvent.setCorrelationId(batchId);
                DoorIncrementalSyncStartEvent.DoorDeviceUpdatePayload payload = new DoorIncrementalSyncStartEvent.DoorDeviceUpdatePayload(scheduled, user);
                payload.setType(EntityType.USER.name());
                syncEvent.setPayload(payload);
                getEventHub().post(syncEvent);
            } catch (ServiceException e) {
                log.error("Unable to post sync user event", e);
            }

            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                for (IUserSyncService syncService : getUserSyncServices()) {
                    try {
                        syncService.process(syncEvent.getEventId(), batchId);
                        log.info("cron.sync=USER sync service '{}' service is DONE.", syncService.friendlyName());
                    } catch (Exception e) {
                        traceError("USER", syncService.friendlyName(), e);
                    }
                }
            }
            log.info("cron.sync=USER sync service FINISHED.");

        } else {
            log.info("cron.sync=USER sync service SKIPPED. No registered sync services.");
            if (!scheduled) {
                try {
                    ServerErrorEvent event = new ServerErrorEvent(EntityType.USER, null, "User", "User sync is disabled");
                    event.setCorrelationId(batchId);
                    getEventHub().post(event);
                } catch (ServiceException e) {
                    log.error("Unable to post disabled integration event", e);
                }
            }
        }
    }

    /**
     * Main method to trigger meta content refresh request in accordance with configured cron interval.
     */
    @Scheduled(cron = "${doors.active.sync.interval}")
    public void syncDoorsData() {
        syncDoorsData(true, ApplicationSecurity.SYSTEM_USER, UUID.randomUUID().toString());
    }

    protected void syncDoorsData(boolean scheduled, String user, String batchId) {
        if (getDoorSyncServices() != null && isEnabledDoorSync()) {
            DoorIncrementalSyncStartEvent syncEvent = new DoorIncrementalSyncStartEvent();
            try {
                syncEvent.setCorrelationId(batchId);
                syncEvent.setPayload(new DoorIncrementalSyncStartEvent.DoorDeviceUpdatePayload(scheduled, user));
                getEventHub().post(syncEvent);
            } catch (ServiceException e) {
                log.error("Unable to post sync door event", e);
            }

            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                for (IDoorSyncService syncService : getDoorSyncServices()) {
                    try {
                        syncService.process(syncEvent.getEventId(), batchId);
                        log.info("cron.sync=DOOR sync service '{}' service is DONE.", syncService.friendlyName());
                    } catch (Exception e) {
                        traceError("DOOR", syncService.friendlyName(), e);
                    }
                }
            }
            log.info("cron.sync=DOOR sync service FINISHED.");
        } else {
            log.info("cron.sync=DOOR sync service SKIPPED. No registered sync services.");
            if (!scheduled) {
                try {
                    ServerErrorEvent event = new ServerErrorEvent(EntityType.DOOR, null, "Door", "Door sync is disabled");
                    event.setCorrelationId(batchId);
                    getEventHub().post(event);
                } catch (ServiceException e) {
                    log.error("Unable to post disabled integration event", e);
                }
            }
        }
    }

    /**
     * Main method to trigger meta content refresh request in accordance to configured cron interval.
     */
    @Scheduled(cron = "${cameras.active.sync.interval}")
    public void syncCamerasData() {
        syncCamerasData(true, ApplicationSecurity.SYSTEM_USER, UUID.randomUUID().toString());
    }

    protected void syncCamerasData(boolean scheduled, String user, String batchId) {
        if (getCameraSyncServices() != null && isEnabledCameraSync()) {
            CameraIncrementalSyncStartEvent syncEvent = new CameraIncrementalSyncStartEvent();
            try {
                syncEvent.setCorrelationId(batchId);
                syncEvent.setPayload(new CameraIncrementalSyncStartEvent.CameraDeviceUpdatePayload(scheduled, user));
                getEventHub().post(syncEvent);
            } catch (ServiceException e) {
                log.error("Unable to post sync camera event", e);
            }

            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                for (ICameraSyncService syncService : getCameraSyncServices()) {
                    try {
                        syncService.process(syncEvent.getEventId(), batchId);
                        log.info("cron.sync=CAMERA sync service '{}' service is DONE.", syncService.friendlyName());
                    } catch (Exception e) {
                        traceError("CAMERA", syncService.friendlyName(), e);
                    }
                }
            }
            log.info("cron.sync=CAMERA sync service FINISHED.");
        } else {
            log.info("cron.sync=CAMERA sync service SKIPPED. No registered sync services.");
            if (!scheduled) {
                try {
                    ServerErrorEvent event = new ServerErrorEvent(EntityType.CAMERA, null, "Camera", "Camera sync is disabled");
                    event.setCorrelationId(batchId);
                    getEventHub().post(event);
                } catch (ServiceException e) {
                    log.error("Unable to post disabled integration event", e);
                }
            }
        }
    }

    /**
     * Main method to trigger meta content refresh request in accordance with configured cron interval.
     */
    @Scheduled(cron = "${radios.active.sync.interval}")
    public void syncRadiosData() {
        syncRadiosData(true, ApplicationSecurity.SYSTEM_USER, UUID.randomUUID().toString());
    }

    protected void syncRadiosData(boolean scheduled, String user, String batchId) {
        if (getRadioSyncServices() != null && isEnabledRadioSync()) {
            RadioIncrementalSyncStartEvent syncEvent = new RadioIncrementalSyncStartEvent();
            try {
                syncEvent.setCorrelationId(batchId);
                syncEvent.setPayload(new RadioIncrementalSyncStartEvent.RadioDeviceUpdatePayload(scheduled, user));
                getEventHub().post(syncEvent);
            } catch (ServiceException e) {
                log.error("Unable to post sync radio event", e);
            }

            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                for (IRadioSyncService syncService : getRadioSyncServices()) {
                    try {
                        syncService.process(syncEvent.getEventId(), batchId);
                        log.info("cron.sync=RADIO sync service '{}' service is DONE.", syncService.friendlyName());
                    } catch (Exception e) {
                        traceError("RADIO", syncService.friendlyName(), e);
                    }
                }
            }
            log.info("cron.sync=RADIO sync service FINISHED.");
        } else {
            log.info("cron.sync=RADIO sync service SKIPPED. No registered sync services.");
            if (!scheduled) {
                try {
                    ServerErrorEvent event = new ServerErrorEvent(EntityType.RADIO, null, "Radio", "Radio sync is disabled");
                    event.setCorrelationId(batchId);
                    getEventHub().post(event);
                } catch (ServiceException e) {
                    log.error("Unable to post disabled integration event", e);
                }
            }
        }
    }

    private void traceError(String serviceType, String serviceName, Exception e) {
        // When the exception is application-specific, we do not need to trace the details.
        if (e instanceof SisConnectionException) {
            String errorDetails;
            if (e.getCause() instanceof SocketException) {
                errorDetails = e.getCause().getMessage();
            } else {
                errorDetails = e.getMessage();
            }
            log.error("cron.sync={} sync service '{}' service is FAILED. {}", serviceType, serviceName, errorDetails);
        } else {
            // When the exception is not application-specific exception and comes from the pool itself,
            // we need to trace it for future debug.
            log.error("cron.sync={} sync service '{}' service is FAILED.", serviceType, serviceName, e);
        }
    }
}
