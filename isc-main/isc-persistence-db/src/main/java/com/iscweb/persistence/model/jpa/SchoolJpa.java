package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.ISchool;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.SchoolStatus;
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
@Table(name = "schools")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "s_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "s_id")),
                     @AttributeOverride(name = "created", column = @Column(name = "s_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "s_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "s_guid")),
                    })
public class SchoolJpa extends BaseJpaTrackedEntity implements ISchool {

    private String name;
    private String contactEmail;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    private RegionJpa region;

    private SchoolStatus status;
    private SchoolDistrictJpa schoolDistrict;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.SCHOOL;
    }

    @Override
    @Column(name = "s_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @Column(name = "s_contact_email")
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    @Column(name = "s_address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    @Column(name = "s_city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    @Column(name = "s_state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    @Column(name = "s_zip_code")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    @Column(name = "s_country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    @ManyToOne
    @JoinColumn(name = "s_r_id")
    public RegionJpa getRegion() {
        return region;
    }

    public void setRegion(RegionJpa region) {
        this.region = region;
    }

    @Override
    @Column(name = "s_status")
    @Enumerated(EnumType.STRING)
    public SchoolStatus getStatus() {
        return status;
    }

    public void setStatus(SchoolStatus status) {
        this.status = status;
    }

    @Override
    @ManyToOne
    @JoinColumn(name = "s_sd_id")
    public SchoolDistrictJpa getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setSchoolDistrict(SchoolDistrictJpa schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
    }
}
