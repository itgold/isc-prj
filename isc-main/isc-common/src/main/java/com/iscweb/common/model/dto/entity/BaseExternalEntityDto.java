package com.iscweb.common.model.dto.entity;

import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IExternalEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nonnull;
import java.time.ZonedDateTime;

/**
 * Base class for dto objects that are managed externally those have external identifier
 * and some other common properties like name and a description.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseExternalEntityDto extends BaseSchoolEntityDto implements IExternalEntityDto, Comparable<BaseExternalEntityDto> {

    private String description;
    private String externalId;
    private ZonedDateTime lastSyncTime;

    public abstract EntityType getEntityType();

    /**
     * Orders entities by type and by name alphanumerically.
     *
     * @param that region dto to make a comparison.
     * @return int value in accordance with Comparable contract.
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(@Nonnull BaseExternalEntityDto that) {
        int result = 1;

        EntityType thisType = this.getEntityType();
        EntityType thatType = that.getEntityType();

        if (thisType != null && thatType != null) {
            if (thisType.lt(thatType)) {
                result = -1;
            } else if (thisType.eq(thatType)) {
                result = this.getName().compareTo(that.getName());
            }
        }

        return result;
    }
}
