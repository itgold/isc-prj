package com.iscweb.component.web.auth.jwt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.common.model.dto.response.BaseResponseDto;
import com.iscweb.component.web.auth.SecurityConstants;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class JwtTokenResponseDto extends BaseResponseDto<Void, SecurityConstants.SecurityErrorCode> {

    public static final String TOKEN_TYPE = "Bearer";

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private List<String> roles;

    @Getter
    @JsonProperty("token_type")
    private final String tokenType = TOKEN_TYPE;

    @Getter
    @Setter
    @JsonProperty("access_token")
    private String accessToken;

    @Getter
    @Setter
    @JsonProperty("expires_in")
    private int expiration;

    @Getter
    @Setter
    @JsonProperty("refresh_token")
    private String refreshToken;

    public JwtTokenResponseDto() {
        super(null);
    }

    public JwtTokenResponseDto(SecurityConstants.SecurityErrorCode error, String message) {
        super(error, message);
    }
}
