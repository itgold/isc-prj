package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.entity.IAlert;
import com.iscweb.common.model.metadata.AlertSeverity;
import com.iscweb.common.model.metadata.AlertStatus;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.persistence.model.BaseJpaTrackedEntity;
import lombok.EqualsAndHashCode;
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

/**
 * Alert entity.
 *
 * @author dmorozov
 * Date: 5/10/22
 */
@Entity
@ToString
@Table(name = "alerts")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "a_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "a_id")),
                     @AttributeOverride(name = "created", column = @Column(name = "a_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "a_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "a_guid")),
                    })
public class AlertJpa extends BaseJpaTrackedEntity implements IAlert {

    private String triggerId;

    private String deviceId;

    private EntityType deviceType;
    private AlertSeverity severity = AlertSeverity.MINOR;

    private int count;

    private AlertStatus status;

    private String eventId;

    private String schoolId;

    private String districtId;

    private String code;

    private String description;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.ALERT;
    }

    @Column(name = "a_at_guid")
    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    @Override
    @Column(name = "a_device_guid")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    @Column(name = "a_device_type")
    @Enumerated(EnumType.STRING)
    public EntityType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(EntityType deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    @Column(name = "a_severity")
    @Enumerated(EnumType.STRING)
    public AlertSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlertSeverity severity) {
        this.severity = severity;
    }

    @Override
    @Column(name = "a_count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    @Column(name = "a_status")
    @Enumerated(EnumType.STRING)
    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    @Override
    @Column(name = "a_event_id")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    @Column(name = "a_school_id")
    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    @Column(name = "a_district_id")
    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    @Override
    @Column(name = "a_code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    @Column(name = "a_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
