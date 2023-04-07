package com.iscweb.persistence.model.jpa;

import com.google.common.collect.Sets;
import com.iscweb.common.model.entity.IDrone;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.DroneStatus;
import com.iscweb.common.model.metadata.DroneType;
import com.iscweb.persistence.model.BaseJpaCompositeEntity;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.locationtech.jts.geom.Point;

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
 * An entity representing a system-managed drone.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
@Entity
@ToString
@Table(name = "drones")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "dr_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "dr_id")),
                     @AttributeOverride(name = "name", column = @Column(name = "dr_name")),
                     @AttributeOverride(name = "description", column = @Column(name = "dr_description")),
                     @AttributeOverride(name = "geoLocation", column = @Column(name = "dr_geo_location")),
                     @AttributeOverride(name = "created", column = @Column(name = "dr_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "dr_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "dr_guid")),
                    })
@AssociationOverrides({@AssociationOverride(name = "regions",
                                            joinTable = @JoinTable(name = "drone_region_joins",
                                                                   joinColumns = @JoinColumn(name = "drrj_dr_id", referencedColumnName = "dr_id"),
                                                                   inverseJoinColumns = @JoinColumn(name = "drrj_r_id", referencedColumnName = "r_id")))})
public class DroneJpa extends BaseJpaCompositeEntity implements IDrone {

    @Setter
    private String externalId;
    @Setter
    private DroneStatus status;
    @Setter
    private DroneType type;
    @Setter
    private Set<DeviceStateItemJpa> state = Sets.newHashSet();

    @Setter
    private Point currentLocation;
    @Setter
    private ZonedDateTime lastSyncTime;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.DRONE;
    }

    @Override
    @Column(name = "dr_external_id")
    public String getExternalId() {
        return externalId;
    }

    @Override
    @Column(name = "dr_status")
    @Enumerated(EnumType.STRING)
    public DroneStatus getStatus() {
        return status;
    }

    @Override
    @Column(name = "dr_type")
    @Enumerated(EnumType.STRING)
    public DroneType getType() {
        return type;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "drones_state", joinColumns = @JoinColumn(name = "drs_drone_id"))
    @OrderColumn(name = "name")
    @BatchSize(size = 20)
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "drs_name")),
            @AttributeOverride(name = "value", column = @Column(name = "drs_value")),
            @AttributeOverride(name = "updated", column = @Column(name = "drs_updated"))
    })
    public Set<DeviceStateItemJpa> getState() {
        return state;
    }

    @Override
    @Column(name = "dr_geo_curr_location")
    public Point getCurrentLocation() {
        return currentLocation;
    }

    @Column(name = "dr_last_sync_time")
    public ZonedDateTime getLastSyncTime() {
        return lastSyncTime;
    }
}
