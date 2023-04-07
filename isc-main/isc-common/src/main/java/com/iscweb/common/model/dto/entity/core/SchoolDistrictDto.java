package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.dto.BaseEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.SchoolDistrictStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

/**
 * District DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SchoolDistrictDto extends BaseEntityDto {

    private String name;
    private String contactEmail;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private RegionDto region;

    private SchoolDistrictStatus status;

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.SCHOOL_DISTRICT;
    }
}
