package com.iscweb.persistence.model.jpa;

import com.google.common.collect.Sets;
import com.iscweb.common.model.entity.ICamera;
import com.iscweb.common.model.metadata.CameraStatus;
import com.iscweb.common.model.metadata.CameraType;
import com.iscweb.common.model.metadata.ConverterType;
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
 * An entity representing a single school surveillance camera.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
@Entity
@ToString
@Table(name = "cameras")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "c_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "c_id")),
                     @AttributeOverride(name = "name", column = @Column(name = "c_name")),
                     @AttributeOverride(name = "description", column = @Column(name = "c_description")),
                     @AttributeOverride(name = "geoLocation", column = @Column(name = "c_geo_location")),
                     @AttributeOverride(name = "created", column = @Column(name = "c_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "c_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "c_guid")),
                    })
@AssociationOverrides({@AssociationOverride(name = "regions",
                                            joinTable = @JoinTable(name = "camera_region_joins",
                                                                   joinColumns = @JoinColumn(name = "crj_c_id", referencedColumnName = "c_id"),
                                                                   inverseJoinColumns = @JoinColumn(name = "crj_r_id", referencedColumnName = "r_id")))})
public class CameraJpa extends BaseJpaCompositeEntity implements ICamera {

    @Setter
    private String externalId;
    @Setter
    private CameraStatus status;
    @Setter
    private CameraType type;
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
        return ConverterType.CAMERA;
    }

    @Override
    @Column(name = "c_name")
    public String getName() {
        return name;
    }

    @Override
    @Column(name = "c_description")
    public String getDescription() {
        return description;
    }

    @Override
    @Column(name = "c_external_id")
    public String getExternalId() {
        return externalId;
    }

    @Override
    @Column(name = "c_status")
    @Enumerated(EnumType.STRING)
    public CameraStatus getStatus() {
        return status;
    }

    @Override
    @Column(name = "c_type")
    @Enumerated(EnumType.STRING)
    public CameraType getType() {
        return type;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cameras_state", joinColumns = @JoinColumn(name = "cs_camera_id"))
    @OrderColumn(name = "name")
    @BatchSize(size = 20)
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "cs_name")),
            @AttributeOverride(name = "value", column = @Column(name = "cs_value")),
            @AttributeOverride(name = "updated", column = @Column(name = "cs_updated"))
    })
    public Set<DeviceStateItemJpa> getState() {
        return state;
    }

    @Column(name = "c_last_sync_time")
    public ZonedDateTime getLastSyncTime() {
        return lastSyncTime;
    }
}
