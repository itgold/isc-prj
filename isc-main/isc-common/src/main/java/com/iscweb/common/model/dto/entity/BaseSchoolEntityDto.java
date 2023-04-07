package com.iscweb.common.model.dto.entity;

import com.google.common.collect.Sets;
import com.iscweb.common.model.dto.BaseEntityDto;
import com.iscweb.common.model.dto.entity.core.GeoPointDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * Abstract class for DTO objects which belong to a school and have a specific location.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseSchoolEntityDto extends BaseEntityDto {

    private String name;

    private GeoPointDto geoLocation;

    private Set<String> parentIds = Sets.newHashSet();

    /**
     * Used to append parents one by one.
     *
     * @param regionGuid to add to the region's guid to add as a parent.
     */
    public void addParent(String regionGuid) {
        getParentIds().add(regionGuid);
    }

    public void removeParent(String regionGuid) {
        getParentIds().remove(regionGuid);
    }

}
