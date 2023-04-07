package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.IUtility;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.UtilityStatus;
import com.iscweb.common.model.metadata.UtilityType;
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
 * An entity representing a system-managed utility objects like gas valves and fire alarms.
 *
 * @author arezen
 * Date: 01/08/2022
 */
@Entity
@ToString
@Table(name = "utilities")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "ut_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "ut_id")),
        @AttributeOverride(name = "name", column = @Column(name = "ut_name")),
        @AttributeOverride(name = "description", column = @Column(name = "ut_description")),
        @AttributeOverride(name = "geoLocation", column = @Column(name = "ut_geo_location")),
        @AttributeOverride(name = "created", column = @Column(name = "ut_created", columnDefinition = "timestamptz")),
        @AttributeOverride(name = "updated", column = @Column(name = "ut_updated", columnDefinition = "timestamptz")),
        @AttributeOverride(name = "guid", column = @Column(name = "ut_guid")),
})
@AssociationOverrides({@AssociationOverride(name = "regions",
        joinTable = @JoinTable(name = "utilities_region_joins",
                joinColumns = @JoinColumn(name = "utrj_u_id", referencedColumnName = "ut_id"),
                inverseJoinColumns = @JoinColumn(name = "utrj_r_id", referencedColumnName = "r_id")))})

public class UtilityJpa extends BaseJpaCompositeEntity implements IUtility {

    @Setter
    private String externalId;
    @Setter
    private UtilityType type;
    @Setter
    private UtilityStatus status;
    @Setter
    private ZonedDateTime lastSyncTime;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.UTILITY;
    }

    @Column(name = "ut_external_id")
    public String getExternalId() {
        return externalId;
    }

    @Column(name = "ut_status")
    @Enumerated(EnumType.STRING)
    public UtilityStatus getStatus() {
        return status;
    }

    @Override
    @Column(name = "ut_type")
    @Enumerated(EnumType.STRING)
    public UtilityType getType() {
        return type;
    }

    @Column(name = "ut_last_sync_time")
    public ZonedDateTime getLastSyncTime() {
        return lastSyncTime;
    }
}
