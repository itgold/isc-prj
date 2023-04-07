package com.iscweb.simulator.dto;

import com.iscweb.common.model.dto.IDto;

public class ErrorResponseDto implements IDto {

    private String errorCode;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
