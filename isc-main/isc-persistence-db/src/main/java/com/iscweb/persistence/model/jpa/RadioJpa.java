package com.iscweb.persistence.model.jpa;

import com.google.common.collect.Sets;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.RadioConnectionStatus;
import com.iscweb.common.model.metadata.RadioStatus;
import com.iscweb.persistence.model.BaseJpaCompositeEntity;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * An entity representing a system-managed radio device.
 *
 * @author dmorozov
 * Date: 2/15/22
 */
@Entity
@ToString(callSuper = true)
@Table(name = "radios")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "ra_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "ra_id")),
                     @AttributeOverride(name = "name", column = @Column(name = "ra_name")),
                     @AttributeOverride(name = "description", column = @Column(name = "ra_description")),
                     @AttributeOverride(name = "geoLocation", column = @Column(name = "ra_geo_location")),
                     @AttributeOverride(name = "created", column = @Column(name = "ra_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "ra_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "ra_guid")),
                    })
@AssociationOverrides({@AssociationOverride(name = "regions",
                                            joinTable = @JoinTable(name = "radio_region_joins",
                                                                   joinColumns = @JoinColumn(name = "rarj_ra_id", referencedColumnName = "ra_id"),
                                                                   inverseJoinColumns = @JoinColumn(name = "rarj_r_id", referencedColumnName = "r_id")))})
public class RadioJpa extends BaseJpaCompositeEntity {

    @Setter
    private String externalId;
    @Setter
    private RadioStatus status;
    @Setter
    private String type;

    @Setter
    private RadioConnectionStatus connectionStatus;
    @Setter
    private Integer deviceState; // combination of RadioStateFlags
    @Setter
    private String radioUserId;
    @Setter
    private Double gpsAltitude;
    @Setter
    private Integer gpsDirection;
    @Setter
    private ZonedDateTime gpsUpdateTime;
    @Setter
    private Integer batteryLevel;
    @Setter
    private Set<DeviceStateItemJpa> state = Sets.newHashSet();
    @Setter
    private ZonedDateTime lastSyncTime;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.RADIO;
    }

    @Column(name = "ra_external_id")
    public String getExternalId() {
        return externalId;
    }

    @Column(name = "ra_status")
    @Enumerated(EnumType.STRING)
    public RadioStatus getStatus() {
        return status;
    }

    @Column(name = "ra_type")
    public String getType() {
        return type;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "radios_state", joinColumns = @JoinColumn(name = "ras_radio_id"))
    @OrderColumn(name = "name")
    @BatchSize(size = 20)
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "ras_name")),
            @AttributeOverride(name = "value", column = @Column(name = "ras_value")),
            @AttributeOverride(name = "updated", column = @Column(name = "ras_updated"))
    })
    public Set<DeviceStateItemJpa> getState() {
        return state;
    }

    @Column(name = "ra_conn_status")
    @Enumerated(EnumType.STRING)
    public RadioConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    @Column(name = "ra_battery_level")
    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    @Column(name = "ra_last_sync_time")
    public ZonedDateTime getLastSyncTime() {
        return lastSyncTime;
    }

    @Column(name = "ra_device_state")
    public Integer getDeviceState() {
        return deviceState;
    }

    @Column(name = "ra_radio_user_id")
    public String getRadioUserId() {
        return radioUserId;
    }

    @Column(name = "ra_gps_altitude")
    public Double getGpsAltitude() {
        return gpsAltitude;
    }

    @Column(name = "ra_gps_direction")
    public Integer getGpsDirection() {
        return gpsDirection;
    }

    @Column(name = "ra_gps_update_time")
    public ZonedDateTime getGpsUpdateTime() {
        return gpsUpdateTime;
    }
}
