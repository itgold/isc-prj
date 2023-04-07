package com.iscweb.service.util.meta;

import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Device naming meta information.
 * It contains parsed data extracted from the device name.
 */
@Data
public abstract class BaseDeviceMeta {

    /**
     * Holds mappings from geojson/object names to DB value of those objects.
     * Originally provided object name is used for the lookup if it is not in the mapping.
     */
    public static final Map<String, String> REGIONS_MAPPING = Maps.newHashMap();
    static {
        REGIONS_MAPPING.put("USD", "Unified School District");
        REGIONS_MAPPING.put("HS", "High School");
    }

    private String district = null;
    private String school = null;
    private String building = null;
    private String floor = null;
    private String room = null;
    private String direction = null;

    public abstract BaseDeviceMeta valueOf(String deviceName);

    public void setDistrict(String district) {
        if (!StringUtils.isEmpty(district)) {
            this.district = getFromMapping(district);
        }
    }

    public void setSchool(String school) {
        if (!StringUtils.isEmpty(school)) {
            this.school = getFromMapping(school);
        }
    }

    public void setBuilding(String building) {
        if (!StringUtils.isEmpty(building)) {
            this.building = getFromMapping(building);
        }
    }

    public void setFloor(String floor) {
        if (!StringUtils.isEmpty(floor)) {
            this.floor = getFromMapping(floor);
        }
    }

    private String getFromMapping(String defaultValue) {
        String result = defaultValue;
        if (REGIONS_MAPPING.containsKey(defaultValue)) {
            result = REGIONS_MAPPING.get(defaultValue);
        }
        return result;
    }
}
