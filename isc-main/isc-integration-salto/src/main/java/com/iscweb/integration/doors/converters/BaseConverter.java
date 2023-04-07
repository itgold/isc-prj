package com.iscweb.integration.doors.converters;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConverter<Dto, Model> {

    public abstract Model toModel(Dto dto);

    public abstract Dto toDto(Model model);

    public List<Model> toModel(List<Dto> dtoList) {
        return dtoList.stream().map(this::toModel).collect(Collectors.toList());
    }

    public List<Dto> toDto(List<Model> modelList) {
        return modelList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
