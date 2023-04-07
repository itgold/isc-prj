package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.IRegion;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.RegionStatus;
import com.iscweb.common.model.metadata.RegionType;
import com.iscweb.persistence.model.BaseJpaCompositeEntity;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.locationtech.jts.geom.Polygon;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Map;

/**
 * Region entity.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
@Entity
@ToString(exclude = {"geoBoundaries", "geoZoom", "geoRotation", "props"}, callSuper = true)
@Table(name = "regions")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "r_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "r_id")),
                     @AttributeOverride(name = "name", column = @Column(name = "r_name")),
                     @AttributeOverride(name = "description", column = @Column(name = "r_description")),
                     @AttributeOverride(name = "geoLocation", column = @Column(name = "r_geo_location")),
                     @AttributeOverride(name = "created", column = @Column(name = "r_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "r_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "r_guid")),
})
@AssociationOverrides({@AssociationOverride(name = "regions",
                                            joinTable = @JoinTable(name = "region_region_joins",
                                                                   joinColumns = @JoinColumn(name = "rrj_r_id_c", referencedColumnName = "r_id"),
                                                                   inverseJoinColumns = @JoinColumn(name = "rrj_r_id_p", referencedColumnName = "r_id")))})
public class RegionJpa extends BaseJpaCompositeEntity implements IRegion {

    @Setter
    private RegionStatus status = RegionStatus.ACTIVATED;
    @Setter
    private RegionType type;

    @Setter
    private String subType;
    @Setter
    private Polygon geoBoundaries;
    @Setter
    private Float geoZoom;
    @Setter
    private Float geoRotation;
    @Setter
    @EqualsAndHashCode.Exclude
    private Map<String, String> props;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.REGION;
    }

    @Override
    @Column(name = "r_status")
    @Enumerated(EnumType.STRING)
    public RegionStatus getStatus() {
        return status;
    }

    @Override
    @Column(name = "r_type")
    @Enumerated(EnumType.STRING)
    public RegionType getType() {
        return type;
    }

    @Column(name = "r_subtype")
    public String getSubType() {
        return subType;
    }

    @Override
    @Column(name = "r_geo_boundaries")
    public Polygon getGeoBoundaries() {
        return geoBoundaries;
    }

    @Override
    @Column(name = "r_geo_zoom")
    public Float getGeoZoom() {
        return geoZoom;
    }

    @Override
    @Column(name = "r_geo_rotation")
    public Float getGeoRotation() {
        return geoRotation;
    }

    @ElementCollection
    @CollectionTable(name = "region_props", joinColumns = { @JoinColumn(name = "rp_region_id") })
    @MapKeyColumn(name = "rp_name")
    @Column(name = "rp_value")
    @BatchSize(size = 100)
    public Map<String, String> getProps() {
        return props;
    }
}

