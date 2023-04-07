package com.iscweb.common.model.dto.entity.core;

import com.iscweb.common.model.dto.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO object representing an enclosed area.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoPolygonDto implements IDto {
    private List<GeoPointDto> points;
}
