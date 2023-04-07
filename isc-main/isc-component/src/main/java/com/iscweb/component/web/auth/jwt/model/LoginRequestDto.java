package com.iscweb.component.web.auth.jwt.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.common.model.dto.IDto;
import lombok.Getter;

public class LoginRequestDto implements IDto {

    @Getter
    private String username;

    @Getter
    private String password;

    @JsonCreator
    public LoginRequestDto(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
