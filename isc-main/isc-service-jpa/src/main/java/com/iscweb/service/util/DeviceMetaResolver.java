package com.iscweb.service.util;

import com.iscweb.common.model.EntityType;
import com.iscweb.service.util.meta.BaseDeviceMeta;
import com.iscweb.service.util.meta.DoorDeviceMeta;
import com.iscweb.service.util.meta.SpeakerDeviceMeta;

/**
 * Device meta-class resolver object. Act as a factory to resolve a specific device meta by provided entity type.
 * Please note that resolved object needs to be initialized with a specific device name for meta extraction.
 * @author skurenkov
 */
public class DeviceMetaResolver {

    /**
     * Primary method for device meta-class resolution.
     * @param entityType type of entity for meta-class construction.
     * @param <T> type of meta-class expected.
     * @return a new instance of device meta-class.
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseDeviceMeta> T resolve(EntityType entityType) {
        T result;
        switch (entityType) {
            case DOOR:
                result = (T) new DoorDeviceMeta();
                break;
            case SPEAKER:
                result = (T) new SpeakerDeviceMeta();
                break;
            default:
                throw new IllegalArgumentException("Entity type not supported");
        }

        return result;
    }
}
