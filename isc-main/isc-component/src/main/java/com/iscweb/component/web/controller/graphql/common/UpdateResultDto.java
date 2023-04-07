package com.iscweb.component.web.controller.graphql.common;

import com.iscweb.common.model.dto.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResultDto implements IDto {

    public enum Status { SUCCESS, FAILURE }

    private String status;

    private String id;
}
