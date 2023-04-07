package com.iscweb.common.model;

import java.time.ZonedDateTime;

/**
 * Common device DTO interface.
 */
public interface IExternalEntityDto {

    EntityType getEntityType();
    String getEntityId();

    ZonedDateTime getLastSyncTime();
}
