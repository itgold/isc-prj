package com.iscweb.integration.cameras.mip.services;

import com.iscweb.common.events.integration.ServerNotAvailableEvent;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.integration.camera.ICameraSyncService;
import com.iscweb.common.sis.exceptions.SisConnectionException;
import com.iscweb.integration.cameras.mip.dto.CameraInfoDto;
import com.iscweb.integration.cameras.mip.events.CameraSyncEvent;
import com.iscweb.integration.cameras.mip.events.CameraSyncPayload;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.transport.http.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.ws.WebServiceException;
import java.net.NoRouteToHostException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service("MIPCameraSyncService")
public class MipCameraSyncService implements ICameraSyncService {

    private static final int MAX_SYNC_SKIP = 10;
    private final AtomicInteger connectionCount = new AtomicInteger(MAX_SYNC_SKIP);
    @Getter
    @Setter(onMethod = @__({@Value("${cameras.active.sync.enabled:#{true}}")}))
    private boolean enabled;
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private MipCameraService cameraMipService;
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Override
    public String friendlyName() {
        return "MIP_CAMERA_SYNC_SERVICE";
    }

    @Override
    public void process(String syncId, String batchId) throws SisConnectionException {
        if (this.isEnabled()) {
            try {
                ZonedDateTime syncTime = ZonedDateTime.now();
                List<CameraInfoDto> cameras = getCameraMipService().syncCameras();
                for (CameraInfoDto camera : cameras) {
                    log.info("Sync camera {} ...", camera.getCameraId());

                    CameraSyncEvent event = new CameraSyncEvent();
                    event.setReferenceId(syncId);
                    event.setCorrelationId(batchId);
                    event.setExternalEntityId(camera.getCameraId());
                    event.setEventTime(syncTime);
                    event.setPayload(new CameraSyncPayload(camera));
                    getEventHub().post(event);
                }

                log.info("... MIP camera service executed ...");
            } catch (WebServiceException | ServiceException e) {
                Throwable cause = e.getCause();

                if (cause != null && (
                        cause instanceof NoRouteToHostException
                                || cause instanceof HTTPException && ((HTTPException) cause).getResponseCode() == 404
                                || cause.getCause() != null && cause.getCause() instanceof HTTPException && ((HTTPException) cause.getCause()).getResponseCode() == 404)
                ) {
                    log.error("Unable to sync cameras: {}", e.getMessage());

                    if (connectionCount.decrementAndGet() == 0) {
                        ServerNotAvailableEvent event = new ServerNotAvailableEvent();
                        event.setReferenceId(syncId);
                        event.setCorrelationId(batchId);
                        event.setPayload(new ServerNotAvailableEvent.ServerNotAvailablePayload(EntityType.CAMERA, null, "MIP"));
                        try {
                            getEventHub().post(event);
                            connectionCount.set(MAX_SYNC_SKIP);
                        } catch (ServiceException ex) {
                            log.error("Unable to post event", ex);
                        }
                    }
                } else {
                    throw new SisConnectionException("Unable to process camera subsystem sync", e);
                }
            }
        } else {
            log.info("... MIP camera service skipped ...");
        }
    }
}
