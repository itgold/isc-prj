package com.iscweb.common.model.dto.composite;

import com.google.common.collect.Sets;
import com.iscweb.common.model.EntityType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * A filter object used to parametrize composite requests and provide
 * data filtration meta information.
 *
 * @author skurenkov
 */
@Data
@Builder
@EqualsAndHashCode
public class CompositeFilter {

    /**
     * Region guid.
     */
    private String regionGuid;

    /**
     * Filters target entities by device type.
     */
    private Set<EntityType> entityTypes;

    /**
     * Filters target entities by composite type.
     */
    private Set<RegionCompositeType> compositeTypes;

    /**
     * Factory method for default composite filter instance.
     * @return a new empty filter object.
     */
    public static CompositeFilter build() {
        return CompositeFilter.builder()
                              .entityTypes(Sets.newHashSet())
                              .compositeTypes(Sets.newHashSet())
                              .build();
    }

    /**
     * Builds a new instance and initialize it by a given device type.
     *
     * @see this#build()
     */
    public static CompositeFilter buildBy(EntityType type) {
        return build().and(type);
    }

    /**
     * Builds a new instance and initialize it by a given composite type.
     *
     * @see this#build()
     */
    public static CompositeFilter buildBy(RegionCompositeType type) {
        return build().and(type);
    }

    /**
     * Region guid initialization factory method.
     * @param regionGuid region guid to set.
     * @return new instance of composite filter initialized with region guid.
     */
    public static CompositeFilter buildBy(String regionGuid) {
        return CompositeFilter.builder().regionGuid(regionGuid).build();
    }

    /**
     * Checks if current composite is empty.
     * @return true if filtering criteria was not provided.
     */
    public boolean isEmpty() {
        return getCompositeTypes().isEmpty() && getEntityTypes().isEmpty();
    }

    /**
     * Filter initialization method by device type.
     * @param type device type for filtering.
     * @return this instance.
     */
    public CompositeFilter and(EntityType type) {
        getEntityTypes().add(type);
        getCompositeTypes().add(RegionCompositeType.LEAF);
        return this;
    }

    /**
     * Filter initialization method by composite type.
     * @param type composite type for filtering.
     * @return this instance.
     */
    public CompositeFilter and(RegionCompositeType type) {
        getCompositeTypes().add(type);
        return this;
    }

    /**
     * Returns true if given device type is enabled by this filter instance.
     * @param type the type of device.
     * @return true if this device needs to be filtered.
     */
    public boolean enabled(EntityType type) {
        return getEntityTypes().isEmpty() || getEntityTypes().contains(type);
    }

    /**
     * Returns true if given composite type is enabled by this filter instance.
     * @param type the type of composite.
     * @return true if this composite type needs to be filtered.
     */
    public boolean enabled(RegionCompositeType type) {
        return getEntityTypes().isEmpty() || getCompositeTypes().contains(type);
    }
}
