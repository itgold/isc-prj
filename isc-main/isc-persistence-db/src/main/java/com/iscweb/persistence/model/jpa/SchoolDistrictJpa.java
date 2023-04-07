package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.ISchoolDistrict;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.SchoolDistrictStatus;
import com.iscweb.persistence.model.BaseJpaTrackedEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@ToString
@Table(name = "school_districts")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "sd_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "sd_id")),
                     @AttributeOverride(name = "created", column = @Column(name = "sd_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "sd_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "sd_guid")),
                    })
public class SchoolDistrictJpa extends BaseJpaTrackedEntity implements ISchoolDistrict {

    private String name;
    private String contactEmail;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    @EqualsAndHashCode.Exclude
    private RegionJpa region;

    private SchoolDistrictStatus status;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.SCHOOL_DISTRICT;
    }

    @Override
    @Column(name = "sd_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @Column(name = "sd_contact_email")
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    @Column(name = "sd_address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    @Column(name = "sd_city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    @Column(name = "sd_state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    @Column(name = "sd_zip_code")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    @Column(name = "sd_country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    @ManyToOne
    @JoinColumn(name = "sd_r_id")
    public RegionJpa getRegion() {
        return region;
    }

    public void setRegion(RegionJpa region) {
        this.region = region;
    }

    @Override
    @Column(name = "sd_status")
    @Enumerated(EnumType.STRING)
    public SchoolDistrictStatus getStatus() {
        return status;
    }

    public void setStatus(SchoolDistrictStatus status) {
        this.status = status;
    }
}
