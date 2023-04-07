package com.iscweb.integration.doors.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.enums.PictureFileResult;
import lombok.Data;

@Data
public class SaltoDbUserInsertUpdateResponseDto implements ISaltoDto {

    @JsonProperty("ExtUserID")
    private String extUserId;

    @JsonProperty("ExtUserID")
    private PictureFileResult pictureFileResult;
}
