package com.iscweb.integration.cameras.mock.services.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class LiveStatusItem {

    public enum StatusType {
        LiveFeedStopped("1", "0", "Camera live feed stopped"),
        LiveFeedStarted("1", "1", "Camera live feed started"),
        FeedNoMotion("2", "0", "Live feed doesn't contains motion"),
        FeedWithMotion("2", "1", "Live feed contains motion"),
        FeedNotRecorded("3", "0", "Live feed is not recorded"),
        FeedRecorded("3", "1", "Live feed is being recorded"),
        NoEvents("4", "0", "There were no event notifications during the live feed"),
        SomeEvents("4", "1", "There was an event notification during the live feed"),
        ConnectionRestored("5", "0", "Connection between the camera and the server is restored"),
        ConnectionLost("5", "1", "Connection between the camera and the server is lost"),
        DBAccessRestored("6", "0", "Accessing the database is restored"),
        DBAccessLost("6", "1", "Accessing the database failed"),
        DiskSpaceOk("7", "0", "The server disk space is okay"),
        DiskSpaceOut("7", "1", "The server is running out of disk space"),
        FeedRestored("100", "0", "Live feed to client is restored"),
        FeedLost("100", "1", "Live feed to client is stopped"),
        CameraMaintenance("991", "0", "Camera maintenance"),
        LicenseIssue("992", "0", "License issue"),
        OverflowIssue("993", "0", "Overflow issue"),
        WritingGopIssue("994", "0", "Writing GOP issue"),
        Unknown("", "", "Unknown"),
        Sync("-1", "0", "Synchronization");

        private final String code;
        private final String value;
        private final String description;

        StatusType(String code, String value, String description) {
            this.code = code;
            this.value = value;
            this.description = description;
        }

        public static StatusType findByCode(String code, String value) {
            StatusType status;
            switch (code) {
                case "1": status = value.equals("1") ? StatusType.LiveFeedStarted : StatusType.LiveFeedStopped; break;
                case "2": status = value.equals("1") ? StatusType.FeedWithMotion : StatusType.FeedNoMotion; break;
                case "3": status = value.equals("1") ? StatusType.FeedRecorded : StatusType.FeedNotRecorded; break;
                case "4": status = value.equals("1") ? StatusType.SomeEvents : StatusType.NoEvents; break;
                case "5": status = value.equals("1") ? StatusType.ConnectionLost : StatusType.ConnectionRestored; break;
                case "6": status = value.equals("1") ? StatusType.DBAccessLost : StatusType.DBAccessRestored; break;
                case "7": status = value.equals("1") ? StatusType.DiskSpaceOut : StatusType.DiskSpaceOk; break;
                case "100": status = value.equals("1") ? StatusType.FeedLost : StatusType.FeedRestored; break;
                case "991": status = CameraMaintenance; break;
                case "992": status = LicenseIssue; break;
                case "993": status = OverflowIssue; break;
                case "994": status = WritingGopIssue; break;
                default: status = StatusType.Unknown;
            }

            return status;
        }

        public String getDescription() {
            return description;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String value;

    public StatusType statusType() {
        return StatusType.findByCode(id, value);
    }

    @Override
    public String toString() {
        return StatusType.findByCode(id, value).description;
    }

    public static LiveStatusItem fromType(StatusType statusType) {
        return fromType(statusType, statusType.getValue());
    }

    public static LiveStatusItem fromType(StatusType statusType, String value) {
        LiveStatusItem item = new LiveStatusItem();
        item.setId(statusType.getCode());
        item.setValue(value);
        return item;
    }
}
