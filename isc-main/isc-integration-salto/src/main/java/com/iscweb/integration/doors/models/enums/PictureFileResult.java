package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PictureFileResult implements ISaltoEnum {
    NONE(0, "The picture has been successfully imported into/removed from the user’s record."),
    NOT_APPLICABLE(1, "“PictureFileName” field within the “SaltoDBUser” input parameter is not mentioned."),
    UNABLE_TO_ACCESS(2, "The file does not exist or privileged permissions are required."),
    TOO_BIG(3, "content of the picture file exceeds maximum allowed."),
    NOT_SUPPORTED(4, "invalid graphic format or not supported."),
    UNKNOWN(5, "unknown error.");

    private final int code;
    private final String message;

    PictureFileResult(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @JsonCreator
    public static PictureFileResult forValue(int code) {
        PictureFileResult result = PictureFileResult.NOT_APPLICABLE;
        for (PictureFileResult type : values()) {
            if (type.code == code) {
                result = type;
            }
        }

        return result;
    }

    @JsonValue
    public int toValue() {
        return this.getCode();
    }
}
