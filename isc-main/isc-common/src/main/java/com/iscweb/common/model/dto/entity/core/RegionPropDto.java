package com.iscweb.common.model.dto.entity.core;

import com.iscweb.common.model.dto.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionPropDto implements IDto {
    private String key;

    private String value;
}
