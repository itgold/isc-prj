package com.iscweb.persistence.model;

import com.google.common.collect.Sets;
import com.iscweb.common.model.ISchoolEntity;
import com.iscweb.persistence.model.jpa.RegionJpa;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Base class for entities which can be tracked by geo coordinates and belong to a specific school or region.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
@ToString(exclude = {"geoLocation", "description", "regions"})
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseJpaCompositeEntity extends BaseJpaTrackedEntity implements ISchoolEntity {

    /**
     * Name of composite entity.
     */
    @Setter
    protected String name;

    /**
     * Composite entity description.
     */
    @Setter
    protected String description;

    /**
     * A set of parent regions of this region.
     * We support multi-parent structure of devices and regions so any region can have multiple parents.
     */
    @Setter
    @EqualsAndHashCode.Exclude
    private Set<RegionJpa> regions = Sets.newHashSet();

    /**
     * What is the geo location of this object.
     * Represented by the geo Point type.
     */
    @Setter
    private Point geoLocation;

    /**
     * Adds a single parent region into the relationship.
     *
     * @param region region to add.
     */
    public void addRegion(RegionJpa region) {
        getRegions().add(region);
    }

    /**
     * @return name of composite entity.
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * @return entity description.
     */
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    /**
     * @see this#regions
     */
    @Override
    @BatchSize(size = 10)
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "region_joins",
               joinColumns = @JoinColumn(name = "rj_d_id", referencedColumnName = "d_id"),
               inverseJoinColumns = @JoinColumn(name = "rj_r_id", referencedColumnName = "r_id"))
    public Set<RegionJpa> getRegions() {
        return regions;
    }

    /**
     * @see this#geoLocation
     */
    @Override
    @Column(name = "geo_location")
    public Point getGeoLocation() {
        return geoLocation;
    }
}
