package com.iscweb.integration.cameras.mock.services;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.integration.camera.ICameraSyncService;
import com.iscweb.common.sis.exceptions.SisConnectionException;
import com.iscweb.integration.cameras.mock.dto.CameraInfoDto;
import com.iscweb.integration.cameras.mock.events.CameraSyncEvent;
import com.iscweb.integration.cameras.mock.events.CameraSyncPayload;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service("MIPCameraSyncService")
public class MockCameraSyncService implements ICameraSyncService {

    @Getter
    @Setter(onMethod = @__({@Value("${cameras.active.sync.enabled:#{true}}")}))
    private boolean enabled;
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private MockCameraService cameraMipService;
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
            } catch (ServiceException e) {
                throw new SisConnectionException("Unable to process camera subsystem sync", e);
            }
        } else {
            log.info("... MIP camera service skipped ...");
        }
    }
}
