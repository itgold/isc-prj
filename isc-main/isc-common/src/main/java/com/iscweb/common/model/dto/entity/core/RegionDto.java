package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.dto.entity.BaseSchoolEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.RegionStatus;
import com.iscweb.common.model.metadata.RegionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Region DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegionDto extends BaseSchoolEntityDto implements Comparable<RegionDto> {

    private static final String PROP_FLOOR_NUMBER = "floorNumber";

    private String description;
    private RegionType type;
    private String subType;
    private RegionStatus status = RegionStatus.ACTIVATED;
    @ToString.Exclude
    private GeoPolygonDto geoBoundaries;
    @ToString.Exclude
    private Float geoZoom;
    @ToString.Exclude
    private Float geoRotation;
    @ToString.Exclude
    private List<RegionPropDto> props = Lists.newArrayList();

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.REGION;
    }

    /**
     * Orders regions by type and by name alphanumerically.
     *
     * @param that region dto to make a comparison.
     * @return int value in accordance with Comparable contract.
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(@Nonnull RegionDto that) {
        int result = 1;

        RegionType thisType = this.getType();
        RegionType thatType = that.getType();

        if (thisType != null && thatType != null) {
            if (thisType.lt(thatType)) {
                result = -1;
            } else if (thisType.eq(thatType)) {
                if (thisType == RegionType.FLOOR) {
                    int floorNumber1 = this.getProps().stream().filter(prop -> PROP_FLOOR_NUMBER.equals(prop.getKey())).findFirst().map(prop -> Integer.parseInt(prop.getValue())).orElse(99);
                    int floorNumber2 = that.getProps().stream().filter(prop -> PROP_FLOOR_NUMBER.equals(prop.getKey())).findFirst().map(prop -> Integer.parseInt(prop.getValue())).orElse(99);
                    result = Integer.compare(floorNumber1, floorNumber2);
                    if (result == 0) {
                        result = this.getName().compareTo(that.getName());
                    }
                } else {
                    result = this.getName().compareTo(that.getName());
                }
            }
        }

        return result;
    }
}
