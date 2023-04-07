package com.iscweb.simulator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iscweb.common.model.dto.IDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.integration.doors.models.enums.Operation;
import lombok.Data;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Data
public class SimSaltoEventDto implements IDto {

    private static final String INPUT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private SaltoOperation saltoOperation;
    private String deviceId;
    private String operatorId;

    private DoorDto door;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = INPUT_DATE_PATTERN, timezone = "UTC")
    private ZonedDateTime eventDateTime = ZonedDateTime.now();

    private enum SaltoActor {

        KEYCARD(0, "Key generated events"),
        DOOR(1, "Door generated events"),
        SERVER(2, "PPD or Server generated event");
        private final Integer code;
        private final String description;

        SaltoActor(Integer code, String description) {
            this.code = code;
            this.description = description;
        }
    }

    public enum SaltoOperation {

        ALARM_DURESS(SaltoActor.DOOR, Operation.DURESS_ALARM),
        ALARM_INTRUSION_END(SaltoActor.DOOR, Operation.END_OF_INTRUSION),
        ALARM_INTRUSION_START(SaltoActor.SERVER, Operation.ALARM_INTRUSION_ONLINE),
        ALARM_TAMPER_END(SaltoActor.DOOR, Operation.END_OF_TAMPER),
        ALARM_TAMPER_START(SaltoActor.SERVER, Operation.ALARM_TAMPER_ONLINE),

        DLO_END(SaltoActor.DOOR, Operation.END_OF_DLO_DOOR_LEFT_OPENED),
        DLO_START(SaltoActor.DOOR, Operation.DOOR_LEFT_OPENED_DLO),

        DOOR_CLOSED_KEY(SaltoActor.KEYCARD, Operation.DOOR_CLOSED_KEY),
        DOOR_CLOSED_KEYBOARD(SaltoActor.DOOR, Operation.DOOR_CLOSED_KEYBOARD),
        DOOR_CLOSED_KEY_AND_KEYBOARD(SaltoActor.KEYCARD, Operation.DOOR_CLOSED_KEY_AND_KEYBOARD),
        DOOR_CLOSED_SWITCH(SaltoActor.DOOR, Operation.DOOR_CLOSED_SWITCH),
        DOOR_COMM_LOST(SaltoActor.DOOR, Operation.COMMUNICATION_WITH_SALTO_SOFTWARE_LOST),
        DOOR_COMM_REGAIN(SaltoActor.DOOR, Operation.COMMUNICATION_WITH_SALTO_SOFTWARE_ESTABLISHED),
        DOOR_CONFIG_SPARE_KEY(SaltoActor.DOOR, Operation.DOOR_PROGRAMMED_WITH_SPARE_KEY),
        DOOR_OPENED_INSIDE_HANDLE(SaltoActor.DOOR, Operation.DOOR_OPENED_INSIDE_HANDLE),

        DOOR_OPENED_KEY(SaltoActor.KEYCARD, Operation.DOOR_OPENED_KEY),
        DOOR_OPENED_KEYBOARD(SaltoActor.DOOR, Operation.DOOR_OPENED_KEYBOARD),
        DOOR_OPENED_KEY_AND_KEYBOARD(SaltoActor.KEYCARD, Operation.DOOR_OPENED_KEY_AND_KEYBOARD),
        DOOR_OPENED_MECHANICAL_KEY(SaltoActor.DOOR, Operation.DOOR_OPENED_MECHANICAL_KEY),
        DOOR_OPENED_MULTIPLE_GUEST_KEY(SaltoActor.KEYCARD, Operation.DOOR_OPENED_MULTI_GUEST_KEY),
        DOOR_OPENED_ONLINE(SaltoActor.SERVER, Operation.DOOR_OPENED_ONLINE_COMMAND),
        DOOR_OPENED_PPD(SaltoActor.SERVER, Operation.DOOR_OPENED_PPD),
        DOOR_OPENED_PROBABLY(SaltoActor.DOOR, Operation.DOOR_MOST_PROBABLY_OPENED_KEY_AND_PIN),
        DOOR_OPENED_SPARE_CARD(SaltoActor.KEYCARD, Operation.DOOR_OPENED_SPARE_CARD),
        DOOR_OPENED_SWITCH(SaltoActor.DOOR, Operation.DOOR_OPENED_SWITCH),
        DOOR_OPENED_UNIQUE(SaltoActor.DOOR, Operation.DOOR_OPENED_UNIQUE),

        FORCE_CLOSING_END(SaltoActor.DOOR, Operation.END_OF_FORCED_CLOSING_ONLINE),
        FORCE_CLOSING_START(SaltoActor.DOOR, Operation.START_OF_FORCED_CLOSING_ONLINE),
        FORCE_OPENING_END(SaltoActor.DOOR, Operation.END_OF_FORCE_OPEN_ONLINE),
        FORCE_OPENING_START(SaltoActor.DOOR, Operation.START_OF_FORCE_OPEN_ONLINE),
        GUEST_CANCELLED(SaltoActor.DOOR, Operation.HOTEL_GUEST_CANCELLED),
        GUEST_KEY_COPY(SaltoActor.DOOR, Operation.GUEST_COPY_KEY),
        GUEST_KEY_NEW(SaltoActor.DOOR, Operation.GUEST_NEW_KEY),
        GUEST_KEY_NEW_HOTEL(SaltoActor.DOOR, Operation.NEW_HOTEL_GUEST_KEY),

        KEY_DELETED(SaltoActor.SERVER, Operation.KEY_DELETED_ONLINE),
        KEY_INSERTED(SaltoActor.DOOR, Operation.KEY_INSERTED),
        KEY_REMOVED(SaltoActor.DOOR, Operation.KEY_REMOVED),
        KEY_UPDATED_OFFSITE(SaltoActor.SERVER, Operation.KEY_UPDATED_IN_OUT_OF_SITE_MODE_ONLINE),
        KEY_UPDATED_ONLINE(SaltoActor.SERVER, Operation.KEY_UPDATED_ONLINE),

        NEW_RENOVATION_CODE(SaltoActor.SERVER, Operation.NEW_RENOVATION_CODE),
        NEW_RENOVATION_CODE_ONLINE(SaltoActor.SERVER, Operation.NEW_RENOVATION_CODE_FOR_KEY_ONLINE),
        NOT_ALLOWED_ANTIPASSBACK(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_ANTIPASSBACK),
        NOT_ALLOWED_CLOSING_DOOR_IN_EMERGENCY(SaltoActor.DOOR, Operation.CLOSING_NOT_ALLOWED_DOOR_IN_EMERGENCY_STATE),
        NOT_ALLOWED_DENIED_BY_HOST(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_DENIED_BY_HOST),
        NOT_ALLOWED_DOOR_IN_EMERGENCY(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_DOOR_IN_EMERGENCY_STATE),
        NOT_ALLOWED_DOOR_IN_PRIVACY(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_KEY_DOES_NOT_OVERRIDE_PRIVACY),
        NOT_ALLOWED_HOTEL_GUEST_CANCELLED(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_HOTEL_GUEST_KEY_CANCELLED),
        NOT_ALLOWED_INVALID_PIN(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_INVALID_PIN),
        NOT_ALLOWED_KEY_AUDIT_FAILED(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_UNABLE_TO_AUDIT_ON_THE_KEY),
        NOT_ALLOWED_KEY_CANCELLED(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_KEY_CANCELLED),
        NOT_ALLOWED_KEY_EXPIRED(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_KEY_EXPIRED),
        NOT_ALLOWED_KEY_INACTIVE(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_KEY_NO_ACTIVATED),
        NOT_ALLOWED_KEY_IN_BLACKLIST(SaltoActor.DOOR, Operation.BLACKLISTED_KEY_DELETED),
        NOT_ALLOWED_KEY_MANIPULATED(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_KEY_WITH_DATA_MANIPULATED),
        NOT_ALLOWED_KEY_OLD_NUMBER(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_KEY_WITH_OLD_RENOVATION_NUMBER),
        NOT_ALLOWED_KEY_OUTDATED(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_KEY_OUT_OF_DATE),
        NOT_ALLOWED_KEY_UNIQUE_VIOLATION(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_UNIQUE_OPENING_KEY_ALREADY_USED),
        NOT_ALLOWED_LOCKER_TIMEOUT(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_LOCKER_OCCUPANCY_TIMEOUT),
        NOT_ALLOWED_NO_AUTH(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_NO_ASSOCIATED_AUTHORIZATION),
        NOT_ALLOWED_NO_BATTERY_LEFT(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_RUN_OUT_OF_BATTERY),
        NOT_ALLOWED_OLD_GUEST_KEY(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_OLD_HOTEL_GUEST_KEY),
        NOT_ALLOWED_OUT_OF_TIME(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_OUT_OF_TIME),
        NOT_ALLOWED_THIS_DOOR(SaltoActor.DOOR, Operation.OPENING_NOT_ALLOWED_KEY_NOT_ALLOWED_IN_THIS_DOOR),
        OFFICE_MODE_END(SaltoActor.DOOR, Operation.END_OF_OFFICE_MODE),
        OFFICE_MODE_END_KEYPAD(SaltoActor.DOOR, Operation.END_OF_OFFICE_MODE_KEYPAD),
        OFFICE_MODE_END_ONLINE(SaltoActor.SERVER, Operation.END_OF_OFFICE_MODE_ONLINE),
        OFFICE_MODE_START(SaltoActor.DOOR, Operation.START_OF_OFFICE_MODE),
        OFFICE_MODE_START_ONLINE(SaltoActor.DOOR, Operation.START_OF_OFFICE_MODE_ONLINE),
        PRIVACY_END(SaltoActor.DOOR, Operation.END_OF_PRIVACY),
        PRIVACY_START(SaltoActor.DOOR, Operation.START_OF_PRIVACY),
        READER_OFFLINE(SaltoActor.DOOR, Operation.COMMUNICATION_WITH_THE_READER_LOST),
        READER_ONLINE(SaltoActor.DOOR, Operation.COMMUNICATION_WITH_THE_READER_REESTABLISHED),
        ROOM_PREPARED(SaltoActor.DOOR, Operation.ROOM_PREPARED),
        SALTO_COMM_LOST(SaltoActor.SERVER, Operation.COMMUNICATION_LOST),
        SALTO_COMM_REGAIN(SaltoActor.SERVER, Operation.COMMUNICATION_REESTABLISHED),
        SYSTEM_AUTO_CHANGE(SaltoActor.DOOR, Operation.AUTOMATIC_CHANGE),
        SYSTEM_EXPIRATION_EXTENDED(SaltoActor.DOOR, Operation.EXPIRATION_AUTOMATICALLY_EXTENDED_OFFLINE),
        SYSTEM_INVALID_CLOCK(SaltoActor.DOOR, Operation.INCORRECT_CLOCK_VALUE),
        SYSTEM_KEY_UPDATE_NOT_COMPLETE(SaltoActor.DOOR, Operation.KEY_HAS_NOT_BEEN_COMPLETELY_UPDATED_ONLINE),
        SYSTEM_LOCK_DATETIME_UPDATED(SaltoActor.DOOR, Operation.RF_LOCK_DATE_AND_TIME_UPDATED),
        SYSTEM_LOCK_RESTART(SaltoActor.DOOR, Operation.RF_LOCK_RESTARTED),
        SYSTEM_LOCK_RF_UPDATED(SaltoActor.DOOR, Operation.RF_LOCK_UPDATED),
        SYSTEM_PDD_CONNECTED(SaltoActor.DOOR, Operation.PPD_CONNECTION),
        SYSTEM_PERIPHERAL_UPDATED_ONLINE(SaltoActor.SERVER, Operation.ONLINE_PERIPHERAL_UPDATED),
        SYSTEM_TIME_MODIFIED(SaltoActor.SERVER, Operation.TIME_MODIFIED_DAYLIGHT_SAVING_TIME);

        private SaltoActor saltoActor;
        private Operation operation;

        SaltoOperation(SaltoActor saltoActor, Operation operation) {
            this.saltoActor = saltoActor;
            this.operation = operation;
        }
    }

    public SaltoOperation getNormalSaltoOperation(int probability) { // 0 - 100
        Random rnd = new Random();
        int x = rnd.nextInt(100);
        return x >= probability ? SaltoOperation.DLO_START : SaltoOperation.DOOR_OPENED_KEY;
    }

    private String eventDateTimeBlock() {
        return "    \"EventDateTime\": \"" + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this.eventDateTime) + "\",\n" +
                "    \"EventDateTimeUTC\": \"" + DateTimeFormatter.ISO_INSTANT.format(this.eventDateTime) + "\",\n";
    }

    private String operationsBlock() {
        return "    \"OperationID\": " + this.saltoOperation.operation.getCode() + ",\n" +
                "    \"OperationDescription\": \"" + this.saltoOperation.operation.getMessage() + "\",\n" +
                "    \"UserType\": " + this.saltoOperation.saltoActor.code + ",\n";
    }

    private String userCardBlock() {

        Map<String, String> map = new HashMap<String, String>();

        switch (this.saltoOperation.saltoActor) {
            case KEYCARD:
                map.put("UserName", "Robert De Niro");
                map.put("UserExtID", "rniro");
                map.put("UserCardSerialNumber", "04BD-0422-9B6-684");
                map.put("UserCardID", "04BD04229B6684");
                break;
            case SERVER:
                map.put("UserName", "Operator");
                map.put("UserExtID", "admin");
                map.put("UserCardSerialNumber", "");
                map.put("UserCardID", "");
                break;
            default:
                map.put("UserName", "");
                map.put("UserExtID", "");
                map.put("UserCardSerialNumber", "");
                map.put("UserCardID", "");
        }

        return "    \"UserName\": \"" + map.get("UserName") + "\",\n" +
               "    \"UserExtID\": \"" + map.get("UserExtID") + "\",\n" +
               "    \"UserCardSerialNumber\": \"" + map.get("UserCardSerialNumber") + "\",\n" +
               "    \"UserCardID\": \"" + map.get("UserCardID") + "\",\n";
    }

    private String deviceBlock() {
        String doorName = this.getDoor() != null ? this.getDoor().getName() : "Sample Door";
        String doorExternalId = this.getDoor() != null ? this.getDoor().getExternalId() : "00000000-0000-0000-0000-0000000000";

        return "    \"DoorName\": \"" + doorName + "\",\n" +
               "    \"DoorExtID\": \""+ doorExternalId +"\"\n";
    }

    @Override
    public String toString() {

        String out = "[\n" +
                "{\n" +
                eventDateTimeBlock() +
                operationsBlock() +
                userCardBlock() +
                deviceBlock() +
                "}\n" +
                "]";

        return out;
    }
}

