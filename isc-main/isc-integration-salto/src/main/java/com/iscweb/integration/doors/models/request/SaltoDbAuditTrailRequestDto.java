package com.iscweb.integration.doors.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.enums.ShowDoorAs;
import com.iscweb.integration.doors.models.enums.ShowUserIdAs;
import lombok.Data;

@Data
public class SaltoDbAuditTrailRequestDto implements ISaltoDto {

    public static final int MAX_COUNT = 100;

    @JsonProperty("MaxCount")
    private Integer maxCount = SaltoDbAuditTrailRequestDto.MAX_COUNT;

    @JsonProperty("StartingFromEventID")
    private Integer startingFromEventId;

    /**
     * Example: Example: (DoorID = “MainEntrance”) OR (DoorID = ‘R+D Department’)
     * Example: ((SubjectID = “Mr A. Brown”) OR (SubjectID = “Mrs B. Alistair”)) AND (SubjectType = 0) AND (EventDateTime >= ‘2008-1-1’)
     * Example (only events other than “low battery level” (operation code = 115)
     *  and “daylight saving time” (operation code = 114)): (Operation <> 115) AND (Operation != 114)
     */
    @JsonProperty("FilterExpression")
    private String filterExpression;

    @JsonProperty("ShowDoorIDAs")
    private ShowDoorAs showDoorIdAs = ShowDoorAs.EXT_DOOR_ID;

    @JsonProperty("ShowUserIDAs")
    private ShowUserIdAs showUserIdAs = ShowUserIdAs.EXT_USER_ID;

    @JsonProperty("DescendingOrder")
    private Boolean descendingOrder = Boolean.FALSE;

    @JsonProperty("ShowTimeInUTC")
    private Boolean showTimeInUtc = Boolean.TRUE;
}
