package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.dto.BaseEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import javax.annotation.Nonnull;
import java.time.ZonedDateTime;

/**
 * External User DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExternalUserDto extends BaseEntityDto implements IExternalEntityDto, Comparable<ExternalUserDto> {

    private String title;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String externalId;
    private UserStatus status;
    private String schoolSite;
    private String officialJobTitle;
    private String idFullName;
    private String idNumber;
    private String officeClass;
    private String source;
    private ZonedDateTime lastSyncTime;

    public ExternalUserDto updateFrom(ExternalUserDto user) {
        this.setTitle(user.getTitle());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setPhoneNumber(user.getPhoneNumber());
        this.setStatus(user.getStatus() != null ? user.getStatus() : this.getStatus());
        this.setSchoolSite(user.getSchoolSite());
        this.setOfficialJobTitle(user.getOfficialJobTitle());
        this.setIdFullName(user.getIdFullName());
        this.setIdNumber(user.getIdNumber());
        this.setOfficeClass(user.getOfficeClass());
        this.setSource(user.getSource());

        return this;
    }

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.EXTERNAL_USER;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.USER;
    }

    @Override
    public String getEntityId() {
        return getId();
    }

    @Transient
    public String getName() {
        String fullName = idFullName;
        if (fullName == null) {
            fullName = firstName + " " + lastName;
        }

        return fullName;
    }

    /**
     * Orders entities by type and by name alphanumerically.
     *
     * @param that region dto to make a comparison.
     * @return int value in accordance with Comparable contract.
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(@Nonnull ExternalUserDto that) {
        int result = 1;

        EntityType thisType = this.getEntityType();
        EntityType thatType = that.getEntityType();

        if (thisType != null && thatType != null) {
            if (thisType.lt(thatType)) {
                result = -1;
            } else if (thisType.eq(thatType)) {
                result = this.getName().compareTo(that.getName());
            }
        }

        return result;
    }
}
