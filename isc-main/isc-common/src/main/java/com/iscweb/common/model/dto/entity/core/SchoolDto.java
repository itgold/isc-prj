package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.dto.BaseEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.SchoolStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

/**
 * School DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SchoolDto extends BaseEntityDto {

    private String name;
    private String contactEmail;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    private SchoolStatus status;
    private SchoolDistrictDto schoolDistrict;
    private RegionDto region;

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.SCHOOL;
    }
}
