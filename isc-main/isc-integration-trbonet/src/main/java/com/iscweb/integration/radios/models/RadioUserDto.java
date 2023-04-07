package com.iscweb.integration.radios.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.common.model.dto.IDto;
import lombok.Data;

/**
 * TRBOnet radio user model.
 */
@Data
public class RadioUserDto implements IDto {

    @JsonProperty("Id")
    private long id;

    @JsonProperty("Name")
    private String fullName;

    @JsonProperty("Email")
    private String email;

}
