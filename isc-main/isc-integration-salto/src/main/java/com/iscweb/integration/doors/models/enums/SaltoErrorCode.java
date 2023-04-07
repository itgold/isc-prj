package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SaltoErrorCode implements ISaltoEnum {
    // General errors:
    NO_ERROR(0,
            "The requested operation has been successfully processed."),
    DB_ERROR(1,
            "This error occurs when there is a problem concerning Salto DB (e.g., DB server is out of service, insufficient security permissions, etc ...)"),
    GENERAL_ERROR(4,
            "Any other error different from those enumerated in this table."),
    PROCESS_ABORTED(5,
            "The requested operation has been cancelled or aborted."),
    UNKNOWN_PROCESS(6,
            "Returned by the “QueryProcess” command when the specified process ID is not correct or unknown."),
    BUSINESS_RULE_VIOLATION(7,
            "The requested operation has failed due to a business rule violation (most probably, some of the specified input parameters are not correct)."),
    ELEMENT_ALREADY_EXISTS(10,
            "The specified entity cannot be inserted in the Salto DB because an entity with the same ID already exists."),
    ELEMENT_NOT_FOUND(11,
            "The requested entity cannot be processed because it does not exist."),
    INVALID_INPUT_PARAMETER(12,
            "Some on the specified input parameters for the requested operation is not valid."),
    LICENSE_FEATURE_NOT_SUPPORTED(13,
            "The license does not support the feature"),
    LICENSE_CONSTRAINT_VIOLATED(14,
            "One of the license constraint has been violated"),

    // Door-related errors:
    DOOR_NOT_FOUND(100,
            "The requested door cannot be processed because it does not exist."),
    DOOR_RECORD_IS_LOCKED(101,
            "The requested operation cannot be performed on the specified door or room (for example, hotel room check-in) because another process is still working on the same room entity."),
    DOOR_ALREADY_EXISTS(102,
            "The specified door cannot be inserted in the Salto DB because another door with the same ID already exists."),

    // User-related errors:
    USER_NOT_FOUND(200,
            "The requested user (cardholder) cannot be processed because it does not exist."),
    USER_BEING_EDITED(201,
            "The requested operation cannot be performed on the specified user (for example, encoding a card) because another process is still working on the same user entity."),
    USER_ALREADY_EXISTS(202,
            "The specified user (cardholder) cannot be inserted in the Salto DB because another user with the same ID already exists."),
    CARD_SERIAL_NUMBER_ALREADY_IN_USED(203,
            "The specified card serial number (ROM code) cannot be assigned to the user because it is already in use by another user. See the SaltoDBUser entity model."),
    USER_CANNOT_BELONG_TO_THE_SPECIFIED_ACCESS_LEVELS(204,
            "This occurs when the specified access levels to which the user must belong are not valid (for example, they belong to a different department). See the SaltoDBUser entity model."),
    THE_CHANGE_IS_NOT_ALLOWED_ALREADY_ASSIGNED(205,
            "If the user has already got a key encoded then it is not allowed to modify the NewKeyIsCancellableThroughBL field."),
    KEY_EXPIRATION_EXCEEDS_MAX_ALLOWED_FOR_NON_CANCELLABLE_KEY(206,
            "This error occurs when the KeyIsCancellableThroughBL is disabled and the key’s new expiration exceeds the maximum allowed for a non-cancellable  key."),

    // Key related-errors:
    NO_KEY(300,
            "The operation cannot be performed because there is no key assigned to the specified user."),
    KEY_RESOURCE_RUN_OUT(301,
            "It is not possible to encode new keys because the maximum number of issued keys has been reached. This error is very unlikely to occur."),
    UNKNOWN_KEY_FORMAT(302,
            "The key presented on the reader is not valid (unknown technology, data corrupted, etc...)"),
    KEY_INCORRECTLY_PLACED(303,
            "The key has been presented on the reader is an incorrect way."),
    KEY_MEMORY_OVERFLOW(304,
            "Data to be written exceeds capacity of the card."),
    KEY_ALREADY_EXISTS(305,
            "The requested operation (specially, encoding of new key) cannot be performed because the specified user has already got another key assigned."),
    INVALID_KEY_STRUCTURE(306,
            "The requested operation (get binary image of the requested card) cannot be performed because structure of the key is not valid."),

    // Peripheral-related errors:
    UNKNOWN_PERIPHERAL(400,
            "The specified peripheral (e.g., encoder) does not exist."),
    NO_PERIPHERAL_COMMUNICATION(401,
            "There is no communication between the Salto software and the peripheral on which to perform the operation."),
    PERIPHERAL_IS_BUSY(402,
            "The requested operation cannot be performed because the specified peripheral is busy."),
    PERIPHERAL_TIMEOUT(403,
            "The specified peripheral has waited too long for the card to be inserted."),
    PERIPHERAL_PROCESS_ERROR(404,
            "An error has been produced while executing the requested operation in the specified peripheral."),
    PERIPHERAL_PROCESS_CANCELLED(405,
            "The requested operation has been aborted."),
    PERIPHERAL_NOT_INITIALIZED(406,
            "The specified peripheral needs being initialized."),
    PERIPHERAL_INCORRECTLY_INITIALIZED(407,
            "The specified peripheral has been incorrectly initialized."),

    // XML parser-related errors:
    INVALID_XML_FORMAT(500,
            "The XML message of the requested operation is not correctly formed."),
    INCORRECT_SYNTAX(501,
            "The requested operation contains invalid or unexpected XML nodes."),
    UNKNOWN_COMMAND_OR_FUNCTION(502,
            "The requested operation is not known."),
    INVALID_DATA_TYPE(503,
            "Some of the XML fields contain invalid data (for example, integer expected but alphanumeric found)."),

    // Zone-related errors:
    ZONE_TYPE_CANNOT_BE_MODIFIED(600,
            "Existing low zone cannot be modified to high zone, and vice versa."),
    MAXIMUM_NUMBER_OF_LOW_ZONES_EXCEEDED(601,
            "The requested zone cannot be performed because maximum number of low zones exceeded: 96.")
    ;

    private final int errorCode;
    private final String errorText;

    SaltoErrorCode(final int errorCode, final String errorText) {
        this.errorCode = errorCode;
        this.errorText = errorText;
    }

    @Override
    public int getCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorText;
    }

    @JsonCreator
    public static SaltoErrorCode forValue(int code) {
        SaltoErrorCode result = SaltoErrorCode.GENERAL_ERROR;
        for (SaltoErrorCode value : values()) {
            if (value.errorCode == code) {
                result = value;
            }
        }

        return result;
    }

    @JsonValue
    public int toValue() {
        return this.getCode();
    }
}
