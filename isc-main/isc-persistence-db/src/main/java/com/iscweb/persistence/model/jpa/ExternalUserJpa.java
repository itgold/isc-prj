package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.IExternalUser;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.UserStatus;
import com.iscweb.persistence.model.BaseJpaTrackedEntity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.ZonedDateTime;

import static com.iscweb.common.model.metadata.UserStatus.ACTIVATED;

/**
 * A User exported from external systems. It is not supposed to be able to login into the system.
 * Right now we have only one system we are importing users from: Salto.
 */
@Entity
@ToString
@NoArgsConstructor
@Table(name = "external_users")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "eu_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "eu_id")),
        @AttributeOverride(name = "created", column = @Column(name = "eu_created", columnDefinition = "timestamptz")),
        @AttributeOverride(name = "updated", column = @Column(name = "eu_updated", columnDefinition = "timestamptz")),
        @AttributeOverride(name = "guid", column = @Column(name = "eu_guid")),
})
public class ExternalUserJpa extends BaseJpaTrackedEntity implements IExternalUser {

    @Setter
    private String externalId;
    @Setter
    private String source;
    @Setter
    private String title;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String phoneNumber;
    @Setter
    private UserStatus status = UserStatus.REGISTERED;
    @Setter
    private String schoolSite;
    @Setter
    private String officialJobTitle;
    @Setter
    private String idFullName;
    @Setter
    private String idNumber;
    @Setter
    private String officeClass;
    @Setter
    private ZonedDateTime lastSyncTime;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.EXTERNAL_USER;
    }

    @Override
    @Column(name = "eu_external_id")
    public String getExternalId() {
        return externalId;
    }

    @Override
    @Column(name = "eu_source")
    public String getSource() {
        return source;
    }

    @Override
    @Column(name = "eu_title")
    public String getTitle() {
        return title;
    }

    @Override
    @Column(name = "eu_phone")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Transient
    public boolean isActive() {
        return getStatus().equals(ACTIVATED);
    }

    @Override
    @Column(name = "eu_first_name")
    public String getFirstName() {
        return firstName;
    }

    @Override
    @Column(name = "eu_last_name")
    public String getLastName() {
        return lastName;
    }

    @Override
    @Column(name = "eu_status")
    @Enumerated(EnumType.STRING)
    public UserStatus getStatus() {
        return status;
    }

    @Override
    @Column(name = "eu_school_site")
    public String getSchoolSite() {
        return schoolSite;
    }

    @Override
    @Column(name = "eu_job_title")
    public String getOfficialJobTitle() {
        return officialJobTitle;
    }

    @Override
    @Column(name = "eu_id_full_name")
    public String getIdFullName() {
        return idFullName;
    }

    @Override
    @Column(name = "eu_id_number")
    public String getIdNumber() {
        return idNumber;
    }

    @Override
    @Column(name = "eu_office_class")
    public String getOfficeClass() {
        return officeClass;
    }

    @Column(name = "eu_last_sync_time")
    public ZonedDateTime getLastSyncTime() {
        return lastSyncTime;
    }
}
