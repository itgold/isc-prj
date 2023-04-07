package com.iscweb.common.model.dto.entity.core;

import com.iscweb.common.model.dto.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO object representing a point on a map.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoPointDto implements IDto {
    private double x;
    private double y;
}
