package com.iscweb.common.model.dto;

import com.iscweb.common.model.IApplicationModel;

import java.time.ZonedDateTime;

/**
 * An interface for all application entity DTO objects.
 */
public interface IEntityDto extends IDto, IApplicationModel {

    ZonedDateTime getCreated();

    void setCreated(ZonedDateTime created);

    ZonedDateTime getUpdated();

    void setUpdated(ZonedDateTime updated);

    Long getRowId();

    void setRowId(Long rowId);

    String getId();

    void setId(String id);
}
