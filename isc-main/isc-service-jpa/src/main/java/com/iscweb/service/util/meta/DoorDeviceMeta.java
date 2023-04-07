package com.iscweb.service.util.meta;

import org.apache.commons.lang3.StringUtils;

/**
 * Device naming meta information.
 * It contains parsed data extracted from the device name.
 */
public class DoorDeviceMeta extends BaseDeviceMeta {

    /**
     * Parses device name and constructs a DeviceMeta object containing extracted information.
     *
     * @param deviceName device name to parse.
     * @return parsed result in a form of DeviceMeta object.
     */
    public DoorDeviceMeta valueOf(String deviceName) {
        if (!StringUtils.isEmpty(deviceName)) {
            setDistrict("USD"); //setting default district
            String[] parts = deviceName.split("-");

            for (int partIndex = 0; partIndex < parts.length; partIndex++) {
                String part = parts[partIndex];
                switch (partIndex) {
                    case 0: //school
                        setSchool(part);
                        break;
                    case 1: //building
                        setBuilding(part);
                        break;
                    case 2: //floor
                        setFloor(part);
                        break;
                    case 3: //room
                        setRoom(part);
                        break;
                    case 4: //direction
                        setDirection(part);
                        break;
                    default:
                        //ignore the rest
                        break;
                }
            }
        }

        return this;
    }
}
