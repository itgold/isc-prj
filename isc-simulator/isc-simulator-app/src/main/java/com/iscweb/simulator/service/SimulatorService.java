package com.iscweb.simulator.service;

import com.iscweb.common.events.payload.StringPayload;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.service.IApplicationService;
import com.iscweb.common.service.IEventHub;
import com.iscweb.integration.doors.events.DoorSyncEvent;
import com.iscweb.integration.doors.events.DoorSyncPayload;
import com.iscweb.integration.doors.events.SaltoRawEvent;
import com.iscweb.integration.doors.models.doors.OnlineDoorStatusDto;
import com.iscweb.integration.doors.models.doors.SaltoDbDoorDto;
import com.iscweb.service.entity.DoorEntityService;
import com.iscweb.simulator.dto.SimSaltoEventDto;
import com.iscweb.simulator.dto.SimSaltoSyncEventDto;
import com.iscweb.simulator.exception.DeviceNotFoundException;
import com.iscweb.simulator.exception.OperationNotFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class SimulatorService implements IApplicationService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorEntityService doorService;

    @Getter
    @Value("${isc.simulator.numberOfEvents:20}")
    private int numberOfEvents;

    @Getter
    @Value("${isc.simulator.sleepInterval:6000}")
    private int sleepInterval;

    @Getter
    @Value("${isc.simulator.probability:50}")
    private int probability;

    public void postDoorEvent(SimSaltoEventDto simRawSaltoEvent) {
        if (simRawSaltoEvent.getSaltoOperation() == null) {
            throw new OperationNotFoundException("Can not find SALTO operation!");
        }

        if (simRawSaltoEvent.getDoor() == null) {
            DoorDto door = getDoorService().getDoorDtoByGuid(simRawSaltoEvent.getDeviceId());
            if (door == null) {
                throw new DeviceNotFoundException(simRawSaltoEvent.getDeviceId());
            }

            simRawSaltoEvent.setDoor(door);
        }

        SaltoRawEvent deviceEvent = new SaltoRawEvent();
        deviceEvent.setEventTime(simRawSaltoEvent.getEventDateTime());
        deviceEvent.setPayload(new StringPayload(SaltoRawEvent.PATH, simRawSaltoEvent.toString()));

        try {
            getEventHub().post(deviceEvent);
        } catch (Exception e) {
            throw new RuntimeException("Can not post to eventHub");
        }
    }

    public void postDoorSyncEvent(SimSaltoSyncEventDto simEvent) {
        DoorDto door = simEvent.getDoor();
        if (door == null) {
            door = getDoorService().getDoorDtoByGuid(simEvent.getDeviceId());
            if (door == null) {
                throw new DeviceNotFoundException(simEvent.getDeviceId());
            }
            simEvent.setDoor(door);
        }

        DoorSyncEvent deviceEvent = new DoorSyncEvent();
        deviceEvent.setExternalEntityId(door.getExternalId());
        deviceEvent.setEventTime(simEvent.getEventDateTime());

        DoorSyncPayload payload = new DoorSyncPayload();

        String doorName = simEvent.isNameSet() ? simEvent.getDeviceName() : door.getName();
        String doorDescription = simEvent.isDescriptionSet() ? simEvent.getDeviceDescription() : door.getDescription();
        simEvent.setDeviceName(doorName);
        simEvent.setDeviceDescription(doorDescription);

        SaltoDbDoorDto data = new SaltoDbDoorDto();
        payload.setData(data);
        data.setId(door.getExternalId());
        data.setName(simEvent.getDeviceName());
        data.setDescription(simEvent.getDeviceDescription());
        data.setOpeningMode(simEvent.getDeviceOpeningMode());
        data.setBatteryStatus(simEvent.getBatteryLevel());
        data.setUpdateRequired(true);

        OnlineDoorStatusDto status = new OnlineDoorStatusDto();
        payload.setStatus(status);
        status.setDoorId(door.getExternalId());
        status.setDoorStatus(simEvent.getDoorStatus());
        status.setCommStatus(simEvent.getCommStatus());
        status.setBatteryStatus(simEvent.getBatteryStatus());
        status.setTamperStatus(simEvent.getTamperStatus());

        deviceEvent.setPayload(payload);

        try {
            getEventHub().post(deviceEvent);
        } catch (Exception e) {
            log.error("Unable to generate test event", e);
        }
    }

    @Async
    public void simulateRandomEvents(List<String> deviceIds) throws InterruptedException {
        for (int i = 0; i < this.numberOfEvents; i++) {
            for (String deviceId : deviceIds) {
                SimSaltoEventDto simRawSaltoEvent = new SimSaltoEventDto();
                simRawSaltoEvent.setDeviceId(deviceId);
                SimSaltoEventDto.SaltoOperation randomSaltoOperation = simRawSaltoEvent.getNormalSaltoOperation(this.probability);
                simRawSaltoEvent.setSaltoOperation(randomSaltoOperation);
                try {
                    postDoorEvent(simRawSaltoEvent);
                    log.info("Simulated an event: " + simRawSaltoEvent);
                    Thread.sleep(500);
                } catch (Exception e) {
                    log.error("Unable to post event", e);
                }
            }
            Thread.sleep(this.sleepInterval);
        }
    }
}
