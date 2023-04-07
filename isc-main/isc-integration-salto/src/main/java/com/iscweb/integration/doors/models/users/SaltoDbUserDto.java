package com.iscweb.integration.doors.models.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.SaltoConstants;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.SaltoDbBinaryDto;
import com.iscweb.integration.doors.models.SaltoPeriodDto;
import com.iscweb.integration.doors.models.enums.KeyStatus;
import com.iscweb.integration.doors.models.enums.MobileAppType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonRootName("SaltoDBUser")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaltoDbUserDto implements ISaltoDto {

    @JsonProperty("ExtUserID")
    private String id;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Office")
    private Boolean office;

    @JsonProperty("AuditOpenings")
    private Boolean auditOpenings;

    @JsonProperty("UseADA")
    private Boolean useAda;

    @JsonProperty("UseAntipassback")
    private Boolean useAntipassback;

    @JsonProperty("LockdownEnabled")
    private Boolean lockdownEnabled;

    @JsonProperty("OverrideLockdown")
    private Boolean overrideLockdown;

    @JsonProperty("OverridePrivacy")
    private Boolean overridePrivacy;

    @JsonProperty("UseLockCalendar")
    private Boolean useLockCalendar;

    @JsonProperty("CalendarID")
    private Integer calendarId;

    @JsonProperty("GPF1")
    private String gpf1;

    @JsonProperty("GPF2")
    private String gpf2;

    @JsonProperty("GPF3")
    private String gpf3;

    @JsonProperty("GPF4")
    private String gpf4;

    @JsonProperty("GPF5")
    private String gpf5;

    @JsonProperty("AutoKeyEdit.ROMCode")
    private SaltoDbBinaryDto romCode;

    @JsonProperty("CurrentROMCode")
    private SaltoDbBinaryDto currentRomCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SaltoConstants.FORMAT_DATE_TIME)
    @JsonProperty("UserActivation")
    private Date userActivation;

    @JsonProperty("UserExpiration.Enabled")
    private Boolean userExpirationEnabled;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SaltoConstants.FORMAT_DATE_TIME)
    @JsonProperty("UserExpiration")
    private Date userExpiration;

    @JsonProperty("KeyExpirationDifferentFromUserExpiration")
    private Boolean keyExpirationDifferentFromUserExpiration;

    @JsonProperty("KeyRevalidation.UpdatePeriod")
    private Integer keyRevalidationUpdatePeriod;

    @JsonProperty("KeyRevalidation.UnitOfUpdatePeriod")
    private Integer keyRevalidationUnitOfUpdatePeriod;

    @JsonProperty("CurrentKeyExists")
    private Boolean currentKeyExists;

    @JsonProperty("CurrentKeyPeriod")
    private SaltoPeriodDto currentKeyPeriod;

    @JsonProperty("CurrentKeyStatus")
    private KeyStatus currentKeyStatus;

    @JsonProperty("PINEnabled")
    private Boolean pinEnabled;

    @JsonProperty("PINCode")
    private String pinCode;

    @JsonProperty("WiegandCode")
    private String wiegandCode;

    @JsonProperty("IsBanned")
    private Boolean isBanned;

    @JsonProperty("KeyIsCancellableThroughBlacklist")
    private Boolean keyIsCancellableThroughBlacklist;

    @JsonProperty("LocationFunctionTimezoneTableID")
    private Integer locationFunctionTimezoneTableId;

    @JsonProperty("MobileAppType")
    private MobileAppType mobileAppType;

    @JsonProperty("PhoneNumber")
    private String phoneNumber;

    @JsonProperty("AuthorizationCodeList")
    private List<String> authorizationCodeList;

    @JsonProperty("SaltoDB.AccessPermissionList.User_Door")
    private SaltoDbAccessPermissionListUserDoorDto doorPermissions;

    @JsonProperty("SaltoDB.AccessPermissionList.User_Zone")
    private SaltoDbAccessPermissionListUserZoneDto zonePermissions;

    @JsonProperty("SaltoDB.MembershipList.User_Group")
    private SaltoDbMembershipListUserGroupDto groupPermissions;

    // @JsonProperty("SaltoDB.AccessPermissionList.User_Location")
    // private List<AccessPermission.User_Location> locationPermissions;

    // @JsonProperty("SaltoDB.AccessPermissionList.User_Function")
    // private List<AccessPermission.User_Function> functionPermissions;

    // @JsonProperty("SaltoDB.AccessPermissionList.User_Output")
    // private List<AccessPermission.User_Output> outputPermissions;

    // @JsonProperty("SaltoDB.AccessPermissionList.User_RoomReservation")
    // private List<AccessPermission.User_RoomReservation> roomPermissions;

    @JsonProperty("PictureFileName")
    private String pictureFileName;

    @JsonProperty("ExtLimitedEntryGroupID")
    private String extLimitedEntryGroupId;

}
