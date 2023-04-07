package com.iscweb.persistence.model.jpa;

import com.google.common.collect.Sets;
import com.iscweb.common.model.entity.ISpeaker;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.SpeakerStatus;
import com.iscweb.common.model.metadata.SpeakerType;
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

@Entity
@ToString
@Table(name = "speakers")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "sp_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "sp_id")),
                     @AttributeOverride(name = "name", column = @Column(name = "sp_name")),
                     @AttributeOverride(name = "description", column = @Column(name = "sp_description")),
                     @AttributeOverride(name = "geoLocation", column = @Column(name = "sp_geo_location")),
                     @AttributeOverride(name = "created", column = @Column(name = "sp_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "sp_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "sp_guid")),
                    })
@AssociationOverrides({@AssociationOverride(name = "regions",
                                            joinTable = @JoinTable(name = "speaker_region_joins",
                                                                   joinColumns = @JoinColumn(name = "srj_sp_id", referencedColumnName = "sp_id"),
                                                                   inverseJoinColumns = @JoinColumn(name = "srj_r_id", referencedColumnName = "r_id")))})
public class SpeakerJpa extends BaseJpaCompositeEntity implements ISpeaker {

    @Setter
    private String externalId;
    @Setter
    private SpeakerStatus status;
    @Setter
    private SpeakerType type;
    @Setter
    private Set<DeviceStateItemJpa> state = Sets.newHashSet();
    @Setter
    private ZonedDateTime lastSyncTime;

    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.SPEAKER;
    }

    @Override
    @Column(name = "sp_external_id")
    public String getExternalId() {
        return externalId;
    }

    @Override
    @Column(name = "sp_status")
    @Enumerated(EnumType.STRING)
    public SpeakerStatus getStatus() {
        return status;
    }

    @Override
    @Column(name = "sp_type")
    @Enumerated(EnumType.STRING)
    public SpeakerType getType() {
        return type;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "speakers_state", joinColumns = @JoinColumn(name = "sps_speaker_id"))
    @OrderColumn(name = "name")
    @BatchSize(size = 20)
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "sps_name")),
            @AttributeOverride(name = "value", column = @Column(name = "sps_value")),
            @AttributeOverride(name = "updated", column = @Column(name = "sps_updated"))
    })
    public Set<DeviceStateItemJpa> getState() {
        return state;
    }

    @Column(name = "sp_last_sync_time")
    public ZonedDateTime getLastSyncTime() {
        return lastSyncTime;
    }
}
