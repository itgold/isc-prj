package com.iscweb.common.model.dto.composite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.iscweb.common.model.dto.BaseDto;
import com.iscweb.common.model.dto.entity.BaseSchoolEntityDto;
import com.iscweb.common.service.IApplicationSecuredService;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for the regions composite structure. Holds common logic for classes in the regions/devices tree.
 *
 * @param <D> the type of entity dto this composite is working with.
 * @param <S> service, that executes this composite's business logic.
 */
public abstract class BaseRegionComposite<D extends BaseSchoolEntityDto, S extends IApplicationSecuredService> extends BaseDto implements IRegionComposite {

    /**
     * Composite dto with the current object type properties.
     */
    @Getter
    @Setter
    @JsonUnwrapped
    private D entityDto;

    /**
     * A service that is responsible for executing this composite's business logic.
     */
    @Getter
    @Setter
    @JsonIgnore
    private S service;

    /**
     * @see IRegionComposite#getCompositeType()
     */
    public abstract RegionCompositeType getCompositeType();

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        String result;

        if (entityDto != null) {
            result = "Node{" + getEntityDto().getConverterType() + ":" + getEntityDto().getName() + '}';
        } else {
            result = super.toString();
        }
        return result;
    }
}
