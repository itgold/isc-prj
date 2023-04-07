package com.iscweb.persistence.model.jpa;

import com.google.common.collect.Sets;
import com.iscweb.common.model.entity.IDoor;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.DoorBatteryStatus;
import com.iscweb.common.model.metadata.DoorConnectionStatus;
import com.iscweb.common.model.metadata.DoorOnlineStatus;
import com.iscweb.common.model.metadata.DoorOpeningMode;
import com.iscweb.common.model.metadata.DoorStatus;
import com.iscweb.common.model.metadata.DoorTamperStatus;
import com.iscweb.common.model.metadata.DoorType;
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
 * An entity representing a system-managed door.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
@Entity
@ToString(callSuper = true)
@Table(name = "doors")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "d_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "d_id")),
                     @AttributeOverride(name = "name", column = @Column(name = "d_name")),
                     @AttributeOverride(name = "description", column = @Column(name = "d_description")),
                     @AttributeOverride(name = "geoLocation", column = @Column(name = "d_geo_location")),
                     @AttributeOverride(name = "created", column = @Column(name = "d_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "d_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "d_guid")),
                    })
@AssociationOverrides({@AssociationOverride(name = "regions",
                                            joinTable = @JoinTable(name = "door_region_joins",
                                                                   joinColumns = @JoinColumn(name = "drj_d_id", referencedColumnName = "d_id"),
                                                                   inverseJoinColumns = @JoinColumn(name = "drj_r_id", referencedColumnName = "r_id")))})
public class DoorJpa extends BaseJpaCompositeEntity implements IDoor {

    @Setter
    private String externalId;
    @Setter
    private DoorStatus status;
    @Setter
    private DoorType type;
    @Setter
    private Set<DeviceStateItemJpa> state = Sets.newHashSet();

    @Setter
    private DoorConnectionStatus connectionStatus;
    @Setter
    private DoorOnlineStatus onlineStatus;
    @Setter
    private DoorBatteryStatus batteryStatus;
    @Setter
    private DoorTamperStatus tamperStatus;
    @Setter
    private DoorOpeningMode openingMode;
    @Setter
    private Integer batteryLevel;
    @Setter
    private Boolean updateRequired;
    @Setter
    private ZonedDateTime lastSyncTime;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.DOOR;
    }

    @Override
    @Column(name = "d_external_id")
    public String getExternalId() {
        return externalId;
    }

    @Override
    @Column(name = "d_status")
    @Enumerated(EnumType.STRING)
    public DoorStatus getStatus() {
        return status;
    }

    @Override
    @Column(name = "d_type")
    @Enumerated(EnumType.STRING)
    public DoorType getType() {
        return type;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "doors_state", joinColumns = @JoinColumn(name = "ds_door_id"))
    @OrderColumn(name = "name")
    @BatchSize(size = 20)
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "ds_name")),
            @AttributeOverride(name = "value", column = @Column(name = "ds_value")),
            @AttributeOverride(name = "updated", column = @Column(name = "ds_updated"))
    })
    public Set<DeviceStateItemJpa> getState() {
        return state;
    }

    @Override
    @Column(name = "d_conn_status")
    @Enumerated(EnumType.STRING)
    public DoorConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    @Override
    @Column(name = "d_online_status")
    @Enumerated(EnumType.STRING)
    public DoorOnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    @Override
    @Column(name = "d_battery_status")
    @Enumerated(EnumType.STRING)
    public DoorBatteryStatus getBatteryStatus() {
        return batteryStatus;
    }

    @Override
    @Column(name = "d_tamper_status")
    @Enumerated(EnumType.STRING)
    public DoorTamperStatus getTamperStatus() {
        return tamperStatus;
    }

    @Override
    @Column(name = "d_opening_mode")
    @Enumerated(EnumType.STRING)
    public DoorOpeningMode getOpeningMode() {
        return openingMode;
    }

    @Override
    @Column(name = "d_battery_level")
    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    @Override
    @Column(name = "d_require_update")
    public Boolean isUpdateRequired() {
        return updateRequired;
    }

    @Column(name = "d_last_sync_time")
    public ZonedDateTime getLastSyncTime() {
        return lastSyncTime;
    }
}
