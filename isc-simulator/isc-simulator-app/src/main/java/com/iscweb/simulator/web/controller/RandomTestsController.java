package com.iscweb.simulator.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.IApplicationComponent;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.service.IEventHub;
import com.iscweb.integration.doors.events.DoorSyncEvent;
import com.iscweb.integration.doors.events.DoorSyncPayload;
import com.iscweb.integration.doors.events.SaltoDoorDeviceEvent;
import com.iscweb.integration.doors.models.SaltoStreamEventDto;
import com.iscweb.integration.doors.models.doors.OnlineDoorStatusDto;
import com.iscweb.integration.doors.models.doors.SaltoDbDoorDto;
import com.iscweb.integration.doors.models.enums.BatteryStatus;
import com.iscweb.integration.doors.models.enums.CommStatus;
import com.iscweb.integration.doors.models.enums.DoorOpeningMode;
import com.iscweb.integration.doors.models.enums.DoorStatus;
import com.iscweb.integration.doors.models.enums.DoorType;
import com.iscweb.integration.doors.models.enums.Operation;
import com.iscweb.integration.doors.models.enums.TamperStatus;
import com.iscweb.integration.doors.models.enums.UserType;
import com.iscweb.service.entity.DoorEntityService;
import com.iscweb.simulator.dto.SimSaltoEventDto;
import com.iscweb.simulator.dto.SimSaltoSyncEventDto;
import com.iscweb.simulator.exception.DeviceNotFoundException;
import com.iscweb.simulator.service.SimulatorService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/simulate")
public class RandomTestsController implements IApplicationComponent {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorEntityService doorService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SimulatorService simulatorService;

    @RequestMapping(value = "/salto/outOfOrderSync", method = RequestMethod.POST)
    public String saltoOutOfOrderSync(@RequestBody SimSaltoEventDto simRawSaltoEvent) throws InterruptedException {
        ZonedDateTime eventDateTime = ZonedDateTime.now();

        DoorDto door = getDoorService().getDoorDtoByGuid(simRawSaltoEvent.getDeviceId());
        if (door == null) {
            throw new DeviceNotFoundException(simRawSaltoEvent.getDeviceId());
        }

        boolean isOpened = door.getState() != null ? door.getState().stream().anyMatch(state -> {
            return "OPENED".equalsIgnoreCase(state.getValue())
                    || "LEFT_OPENED".equalsIgnoreCase(state.getValue())
                    || "EMERGENCY_OPEN".equalsIgnoreCase(state.getValue());
        }) : false;

        simRawSaltoEvent.setDoor(door);
        simRawSaltoEvent.setEventDateTime(eventDateTime);
        simRawSaltoEvent.setSaltoOperation(isOpened ? SimSaltoEventDto.SaltoOperation.DOOR_CLOSED_KEY : SimSaltoEventDto.SaltoOperation.DOOR_OPENED_KEY);
        log.warn("Publish event. Event time: {}", simRawSaltoEvent.getEventDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss Z").withZone(ZoneId.systemDefault())));
        getSimulatorService().postDoorEvent(simRawSaltoEvent);

        SimSaltoSyncEventDto simEvent = new SimSaltoSyncEventDto();
        simEvent.setEventDateTime(eventDateTime.minus(1, ChronoUnit.SECONDS));
        simEvent.setDoor(door);
        simEvent.setDeviceOpeningMode(DoorOpeningMode.STANDARD);
        simEvent.setBatteryLevel(50);
        simEvent.setDoorStatus(isOpened ? DoorStatus.OPENED : DoorStatus.CLOSED);
        simEvent.setCommStatus(CommStatus.ONLINE);
        simEvent.setTamperStatus(TamperStatus.NORMAL);
        log.warn("Publish event. Event time: {}", simEvent.getEventDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss Z").withZone(ZoneId.systemDefault())));
        getSimulatorService().postDoorSyncEvent(simEvent);
        Thread.sleep(500);

        simEvent.setEventDateTime(eventDateTime.plus(1, ChronoUnit.SECONDS));
        simEvent.setDoorStatus(isOpened ? DoorStatus.OPENED : DoorStatus.CLOSED);
        log.warn("Publish event. Event time: {}", simEvent.getEventDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss Z").withZone(ZoneId.systemDefault())));
        getSimulatorService().postDoorSyncEvent(simEvent);

        return simRawSaltoEvent.toString();
    }

    @RequestMapping(value = "/salto/dlo/{step}", method = RequestMethod.POST)
    public String dloTesting(@RequestBody SimSaltoEventDto simRawSaltoEvent, @PathVariable(value = "step") int step) throws InterruptedException, ServiceException {
        ZonedDateTime eventDateTime = ZonedDateTime.now();
        DoorDto door = getDoorService().getDoorDtoByGuid(simRawSaltoEvent.getDeviceId());
        if (door == null) {
            throw new DeviceNotFoundException(simRawSaltoEvent.getDeviceId());
        }

        IEvent event = null;
        switch(step) {
            case 0: {
                door.setState(Sets.newHashSet());
                getDoorService().update(door, Lists.newArrayList());
                break;
            }
            case 1: event = createCloseDoorSyncEvent(door, eventDateTime); break;
            case 2: event = createDLOSaltoStreamEvent(door, eventDateTime); break;
            case 3:
                event = createLeftOpenedDoorSyncEvent(door, eventDateTime); break;
        }

        if (event != null) {
            getEventHub().post(event);
            return "SUCCESS";
        }

        return step == 0 ? "SUCCESS" : "Invalid step!";
    }

    private IEvent createLeftOpenedDoorSyncEvent(DoorDto door, ZonedDateTime eventDateTime) {
        SaltoDbDoorDto data = new SaltoDbDoorDto();
        data.setId(door.getExternalId());
        data.setName("HS-B2-1-122-W");
        data.setDescription("Assistant Principal 1");
        data.setGpf1("Wireless A9650 Full-Esc w/ Priv");
        data.setGpf2("ZONE 22");
        data.setOpenTime(6);
        data.setOpenTimeAda(20);
        data.setOpeningMode(DoorOpeningMode.STANDARD);
        data.setTimedPeriodsTableId(6);
        data.setAutomaticChangesTableId(1);
        data.setKeypadCode((byte) 0);
        data.setAuditOnKeys(Boolean.FALSE);
        data.setAntipassbackEnabled(Boolean.FALSE);
        data.setOutwardAntipassback(Boolean.FALSE);
        data.setUpdateRequired(Boolean.FALSE);
        data.setBatteryStatus(100);

        OnlineDoorStatusDto status = new OnlineDoorStatusDto();
        status.setDoorId(door.getExternalId());
        status.setDoorType(DoorType.RF_WIRELESS);
        status.setDoorStatus(DoorStatus.LEFT_OPENED);
        status.setCommStatus(CommStatus.ONLINE);
        status.setBatteryStatus(BatteryStatus.NORMAL);
        status.setTamperStatus(TamperStatus.NORMAL);

        DoorSyncEvent event = new DoorSyncEvent();
        event.setExternalEntityId(door.getExternalId());
        event.setEventTime(eventDateTime);
        event.setReceivedTime(eventDateTime);
        event.setPayload(new DoorSyncPayload(data, status));
        event.setReferenceId(UUID.randomUUID().toString());
        event.setCorrelationId(UUID.randomUUID().toString());
        return event;
    }

    private IEvent createDLOSaltoStreamEvent(DoorDto door, ZonedDateTime eventDateTime) {
        SaltoStreamEventDto payload = new SaltoStreamEventDto();
        payload.setEventDateTime(Date.from(eventDateTime.toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()));
        payload.setEventDateTimeUtc(Date.from(eventDateTime.toLocalDateTime().atZone(ZoneOffset.UTC).toInstant()));
        payload.setEventTime(payload.getEventDateTime());
        payload.setEventTimeUtc(payload.getEventDateTimeUtc());
        payload.setOperation(Operation.DOOR_LEFT_OPENED_DLO);
        payload.setDescription("Door left open");
        payload.setIsExit(Boolean.FALSE);
        payload.setUserType(UserType.DOOR);
        payload.setUserName("");
        payload.setUserExtId("");
        payload.setUserGpf1("");
        payload.setUserGpf2("");
        payload.setUserGpf3("");
        payload.setUserGpf4("");
        payload.setUserGpf5("");
        payload.setUserCardSerialNumber("");
        payload.setUserCardId("");
        payload.setDoorName("HS-B2-1-122-W");
        payload.setDoorExtId(door.getExternalId());
        payload.setDoorGpf1("Wireless A9650 Full-Esc w/ Priv");
        payload.setDoorGpf2("ZONE 22");

        SaltoDoorDeviceEvent event = new SaltoDoorDeviceEvent();
        event.setExternalEntityId(door.getExternalId());
        event.setEventTime(eventDateTime);
        event.setReceivedTime(eventDateTime);
        event.setPayload(payload);
        event.setReferenceId(UUID.randomUUID().toString());
        event.setCorrelationId(UUID.randomUUID().toString());
        return event;
    }

    private IEvent createCloseDoorSyncEvent(DoorDto door, ZonedDateTime eventDateTime) {
        SaltoDbDoorDto data = new SaltoDbDoorDto();
        data.setId(door.getExternalId());
        data.setName("HS-B2-1-122-W");
        data.setDescription("Assistant Principal 1");
        data.setGpf1("Wireless A9650 Full-Esc w/ Priv");
        data.setGpf2("ZONE 22");
        data.setOpenTime(6);
        data.setOpenTimeAda(20);
        data.setOpeningMode(DoorOpeningMode.STANDARD);
        data.setTimedPeriodsTableId(6);
        data.setAutomaticChangesTableId(1);
        data.setKeypadCode((byte) 0);
        data.setAuditOnKeys(Boolean.FALSE);
        data.setAntipassbackEnabled(Boolean.FALSE);
        data.setOutwardAntipassback(Boolean.FALSE);
        data.setUpdateRequired(Boolean.FALSE);
        data.setBatteryStatus(100);

        OnlineDoorStatusDto status = new OnlineDoorStatusDto();
        status.setDoorId(door.getExternalId());
        status.setDoorType(DoorType.RF_WIRELESS);
        status.setDoorStatus(DoorStatus.CLOSED);
        status.setCommStatus(CommStatus.ONLINE);
        status.setBatteryStatus(BatteryStatus.NORMAL);
        status.setTamperStatus(TamperStatus.NORMAL);

        DoorSyncEvent event = new DoorSyncEvent();
        event.setExternalEntityId(door.getExternalId());
        event.setEventTime(eventDateTime);
        event.setReceivedTime(eventDateTime);
        event.setPayload(new DoorSyncPayload(data, status));
        event.setReferenceId(UUID.randomUUID().toString());
        event.setCorrelationId(UUID.randomUUID().toString());
        return event;
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
