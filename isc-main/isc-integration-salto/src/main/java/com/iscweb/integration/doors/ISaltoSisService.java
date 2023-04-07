package com.iscweb.integration.doors;

import com.iscweb.common.sis.exceptions.SisConnectionException;
import com.iscweb.integration.doors.models.SaltoDbAuditTrailDto;
import com.iscweb.integration.doors.models.SaltoGetInfoDto;
import com.iscweb.integration.doors.models.request.SaltoDbAuditTrailRequestDto;
import com.iscweb.integration.doors.models.request.SaltoDbDoorsRequestDto;
import com.iscweb.integration.doors.models.request.SaltoDbUserDeleteRequestDto;
import com.iscweb.integration.doors.models.request.SaltoDbUserInsertUpdateRequestDto;
import com.iscweb.integration.doors.models.request.SaltoDbUsersRequestDto;
import com.iscweb.integration.doors.models.request.SaltoEmergencyOpenRequestDto;
import com.iscweb.common.sis.ISisService;
import com.iscweb.common.sis.annotations.SisServiceMethod;
import com.iscweb.integration.doors.models.response.OnlineDoorStatusListResponseDto;
import com.iscweb.integration.doors.models.response.OnlineIpDoorStatusListResponseDto;
import com.iscweb.integration.doors.models.response.SaltoDbDoorsResponseDto;
import com.iscweb.integration.doors.models.response.SaltoDbUserInsertUpdateResponseDto;
import com.iscweb.integration.doors.models.response.SaltoDbUsersResponseDto;
import com.iscweb.integration.doors.models.response.SaltoOnlineDoorActionResponseDto;

public interface ISaltoSisService extends ISisService {

    @SisServiceMethod("GetInfo")
    SaltoGetInfoDto getInfo() throws SisConnectionException;

    @SisServiceMethod("SaltoDBDoorList.Read")
    SaltoDbDoorsResponseDto listDoors(SaltoDbDoorsRequestDto request) throws SisConnectionException;

    @SisServiceMethod("OnlineIPDoor.GetOnlineStatusList")
    OnlineIpDoorStatusListResponseDto onlineIpDoorStatus(SaltoEmergencyOpenRequestDto request) throws SisConnectionException;

    @SisServiceMethod("OnlineDoor.GetOnlineStatusList")
    OnlineDoorStatusListResponseDto onlineDoorStatus(SaltoEmergencyOpenRequestDto request) throws SisConnectionException;

    @SisServiceMethod("OnlineDoor.EmergencyOpen")
    SaltoOnlineDoorActionResponseDto emergencyOpenDoor(SaltoEmergencyOpenRequestDto request) throws SisConnectionException;

    @SisServiceMethod("OnlineDoor.EmergencyClose")
    SaltoOnlineDoorActionResponseDto emergencyCloseDoor(SaltoEmergencyOpenRequestDto request) throws SisConnectionException;

    @SisServiceMethod("OnlineDoor.EndOfEmergency")
    SaltoOnlineDoorActionResponseDto emergencyEnd(SaltoEmergencyOpenRequestDto request) throws SisConnectionException;

    @SisServiceMethod("OnlineDoor.Open")
    SaltoOnlineDoorActionResponseDto openDoor(SaltoEmergencyOpenRequestDto request) throws SisConnectionException;

    @SisServiceMethod("SaltoDBAuditTrail.Read")
    SaltoDbAuditTrailDto getAuditTrail(SaltoDbAuditTrailRequestDto request) throws SisConnectionException;

    // Functions related to users:
    @SisServiceMethod("SaltoDBUserList.Read")
    SaltoDbUsersResponseDto listUsers(SaltoDbUsersRequestDto request) throws SisConnectionException;

    @SisServiceMethod("SaltoDBUser.Insert")
    SaltoDbUserInsertUpdateResponseDto insertUser(SaltoDbUserInsertUpdateRequestDto request) throws SisConnectionException;

    @SisServiceMethod("SaltoDBUser.Update")
    SaltoDbUserInsertUpdateResponseDto updateUser(SaltoDbUserInsertUpdateRequestDto request) throws SisConnectionException;

    @SisServiceMethod("SaltoDBUser.InsertOrUpdate")
    SaltoDbUserInsertUpdateResponseDto insertUpdateUser(SaltoDbUserInsertUpdateRequestDto request) throws SisConnectionException;

    @SisServiceMethod("SaltoDBUser.Delete")
    SaltoDbUserInsertUpdateResponseDto deleteUser(SaltoDbUserDeleteRequestDto request) throws SisConnectionException;
}
