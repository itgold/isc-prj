package com.iscweb.common.model.dto.entity.core;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZoneDto extends RegionDto {

    private Set<String> childIds = Sets.newHashSet();

    public ZoneDto() {}

    public ZoneDto(RegionDto region) {
        setId(region.getId());
        setRowId(region.getRowId());
        setCreated(region.getCreated());
        setUpdated(region.getUpdated());
        setParentIds(region.getParentIds());
        setName(region.getName());
        setDescription(region.getDescription());
        setType(region.getType());
        setStatus(region.getStatus());
        setProps(region.getProps());
        setSubType(region.getSubType());
    }
}
