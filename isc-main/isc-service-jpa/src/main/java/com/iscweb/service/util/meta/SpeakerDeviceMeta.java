package com.iscweb.service.util.meta;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * Device naming meta information.
 * It contains parsed data extracted from the device name.
 */
public class SpeakerDeviceMeta extends BaseDeviceMeta {

    @Getter
    @Setter
    private String index = null;

    /**
     * Parses device name and constructs a DeviceMeta object containing extracted information.
     *
     * @param deviceName device name to parse.
     * @return parsed result in a form of DeviceMeta object.
     */
    public SpeakerDeviceMeta valueOf(String deviceName) {
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
                        if (part.startsWith("EXT")) {
                            break;
                        }
                        setFloor(part);
                        break;
                    case 3: //room
                        if (part.startsWith("HAL")) {
                            break;
                        }
                        if (!part.startsWith("#")) {
                            setRoom(part);
                            break;
                        } //else fall through the index
                    case 4: //index
                        if (part.startsWith("#")) {
                            part = part.substring(1);
                            setIndex(part);
                            break;
                        } //else fall through the index
                    case 5: //direction
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
