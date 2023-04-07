package com.iscweb.service;

import com.iscweb.common.events.integration.IExternalEntityProvider;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.service.integration.UserProvider;
import com.iscweb.service.integration.camera.CameraDeviceProvider;
import com.iscweb.service.integration.door.DoorDeviceProvider;
import com.iscweb.service.integration.drone.DroneDeviceProvider;
import com.iscweb.service.integration.radio.RadioDeviceProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExternalEntityProviderFactory implements IApplicationSecuredService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorDeviceProvider doorDeviceProvider;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CameraDeviceProvider cameraDeviceProvider;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DroneDeviceProvider droneDeviceProvider;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RadioDeviceProvider radioDeviceProvider;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UserProvider userProvider;

    /**
     * Resolves a suitable device provider by a given type of the device.
     * @see IExternalEntityProvider
     *
     * @param entityType device type to resolve device provider for.
     * @return a suitable implementation of the device provider.
     */
    public IExternalEntityProvider resolveProvider(EntityType entityType) {
        IExternalEntityProvider deviceProvider = null;

        switch (entityType) {
            case DOOR:
                deviceProvider = getDoorDeviceProvider();
                break;
            case CAMERA:
                deviceProvider = getCameraDeviceProvider();
                break;
            case DRONE:
                deviceProvider = getDroneDeviceProvider();
                break;
            case USER:
                deviceProvider = getUserProvider();
                break;
            case RADIO:
                deviceProvider = getRadioDeviceProvider();
                break;
            default:
                log.error("Unknown device type: {}", entityType.name());
        }

        return deviceProvider;
    }
}
