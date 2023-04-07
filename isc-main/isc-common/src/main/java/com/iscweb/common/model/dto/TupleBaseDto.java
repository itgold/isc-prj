package com.iscweb.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TupleBaseDto<K, V> implements IDto {

    @NotNull
    private K name;

    private V value;
}
