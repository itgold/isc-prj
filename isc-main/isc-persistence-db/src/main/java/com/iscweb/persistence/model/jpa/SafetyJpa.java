package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.ISafety;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.SafetyStatus;
import com.iscweb.common.model.metadata.SafetyType;
import com.iscweb.persistence.model.BaseJpaCompositeEntity;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.ZonedDateTime;

/**
 * An entity representing a system-managed safety objects like gas valves and fire alarms.
 *
 * @author arezen
 * Date: 01/08/2022
 */
@Entity
@ToString
@Table(name = "safeties")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "sf_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "sf_id")),
        @AttributeOverride(name = "name", column = @Column(name = "sf_name")),
        @AttributeOverride(name = "description", column = @Column(name = "sf_description")),
        @AttributeOverride(name = "geoLocation", column = @Column(name = "sf_geo_location")),
        @AttributeOverride(name = "created", column = @Column(name = "sf_created", columnDefinition = "timestamptz")),
        @AttributeOverride(name = "updated", column = @Column(name = "sf_updated", columnDefinition = "timestamptz")),
        @AttributeOverride(name = "guid", column = @Column(name = "sf_guid")),
})
@AssociationOverrides({@AssociationOverride(name = "regions",
        joinTable = @JoinTable(name = "safeties_region_joins",
                joinColumns = @JoinColumn(name = "sfrj_s_id", referencedColumnName = "sf_id"),
                inverseJoinColumns = @JoinColumn(name = "sfrj_r_id", referencedColumnName = "r_id")))})
public class SafetyJpa extends BaseJpaCompositeEntity implements ISafety {

    @Setter
    private String externalId;
    @Setter
    private SafetyType type;
    @Setter
    private SafetyStatus status;
    @Setter
    private ZonedDateTime lastSyncTime;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.SAFETY;
    }

    @Column(name = "sf_external_id")
    public String getExternalId() {
        return externalId;
    }

    @Column(name = "sf_status")
    @Enumerated(EnumType.STRING)
    public SafetyStatus getStatus() {
        return status;
    }

    @Override
    @Column(name = "sf_type")
    @Enumerated(EnumType.STRING)
    public SafetyType getType() {
        return type;
    }

    @Column(name = "sf_last_sync_time")
    public ZonedDateTime getLastSyncTime() {
        return lastSyncTime;
    }
}
