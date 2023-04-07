package com.iscweb.simulator.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.iscweb.common.model.IApplicationComponent;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.service.IEventHub;
import com.iscweb.integration.cameras.mip.events.MipCameraDeviceEvent;
import com.iscweb.integration.cameras.mip.services.streaming.dto.LiveStatusItem;
import com.iscweb.integration.cameras.mip.services.streaming.events.MipCameraStatusEventDto;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.CameraEntityService;
import com.iscweb.service.entity.DoorEntityService;
import com.iscweb.simulator.dto.ErrorResponseDto;
import com.iscweb.simulator.dto.SimMipEventDto;
import com.iscweb.simulator.dto.SimSaltoEventDto;
import com.iscweb.simulator.dto.SimSaltoSyncEventDto;
import com.iscweb.simulator.exception.DeviceNotFoundException;
import com.iscweb.simulator.exception.OperationNotFoundException;
import com.iscweb.simulator.exception.OperatorNotFoundException;
import com.iscweb.simulator.service.SimulatorService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Salto Simulator
 *
 * This controller will generate Salto events
 * and publish them to the eventHub
 *
 * Events will be generated using salto Operations enum
 * Device will be looked up using device GUUID property
 *
 * @author alex@iscweb.io
 * Date: 3/15/21
 */
@Slf4j
@RestController
@RequestMapping("/simulate")
public class SimulationController implements IApplicationComponent {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorEntityService doorService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CameraEntityService cameraService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SimulatorService simulatorService;

    /**
     * Simulate Single Event
     *
     * This method will generate an event string that represents a single Salto Event.
     * Single event is still wrapped in [] as API documentations requires.
     */
    @RequestMapping(value = "/salto/single", method = RequestMethod.POST)
    public String ping(@RequestBody SimSaltoEventDto simRawSaltoEvent) {
        getSimulatorService().postDoorEvent(simRawSaltoEvent);
        return simRawSaltoEvent.toString();
    }

    /**
     * Simulate Camera Event
     * <p>
     * This method will generate an event that represents a single Camera Event.
     */
    @RequestMapping(value = "/mip/single", method = RequestMethod.POST)
    public SimMipEventDto ping(@RequestBody SimMipEventDto simEvent) {

        if (simEvent.getMipOperation() == null) {
            throw new OperationNotFoundException("Can not find MIP operation!");
        }

        CameraDto cameraDto = Convert.my(getCameraService().findByGuid(simEvent.getDeviceId())).scope(Scope.ALL).boom();
        if (cameraDto == null) {
            throw new DeviceNotFoundException(simEvent.getDeviceId());
        }

        simEvent.setCameraDto(cameraDto);

        MipCameraStatusEventDto cameraStatusEvent = new MipCameraStatusEventDto();
        cameraStatusEvent.setStatus(Lists.newArrayList());
        cameraStatusEvent.getStatus().add(LiveStatusItem.fromType(simEvent.getMipOperation()));
        IEvent deviceEvent = new MipCameraDeviceEvent(cameraDto.getExternalId()).payload(cameraStatusEvent);

        try {
            eventHub.post(deviceEvent);
        } catch (Exception e) {
            throw new RuntimeException("Can not post to eventHub");
        }
        return simEvent;
    }

    /**
     * Simulate Single Sync Event
     * <p>
     * This method will generate an event that represents a single Salto Event.
     */
    @RequestMapping(value = "/salto/sync/single", method = RequestMethod.POST)
    public SimSaltoSyncEventDto ping(@RequestBody SimSaltoSyncEventDto simEvent) {
        getSimulatorService().postDoorSyncEvent(simEvent);
        return simEvent;
    }

    @ExceptionHandler({RuntimeException.class, DeviceNotFoundException.class, OperationNotFoundException.class, OperatorNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleError(HttpServletRequest req, Exception ex) throws JsonProcessingException {
        return new ErrorResponseDto( ex.getMessage() );
    }

    @CrossOrigin()
    @RequestMapping(value = "/salto/random", method = RequestMethod.POST)
    @ResponseBody
    public Map saltoSimulateRandom(@RequestBody List<String> deviceIds) throws InterruptedException {

        Map rtn = new HashMap();

        int numberOfEvents = getSimulatorService().getNumberOfEvents();
        int sleepInterval = getSimulatorService().getSleepInterval();

        List<String> accepted = new ArrayList<>();
        List<String> rejected = new ArrayList<>();

        for (String deviceId : deviceIds) {
            DoorDto door = getDoorService().getDoorDtoByGuid(deviceId);
            if (door == null) {
                rejected.add(deviceId);
            } else {
                accepted.add(deviceId);
            }
        }
        rtn.put("accepted", accepted);
        rtn.put("rejected", rejected);
        rtn.put("repeat", numberOfEvents);
        rtn.put("sleepMs", sleepInterval);
        rtn.put("timestamp", System.currentTimeMillis());
        rtn.put("eta", (System.currentTimeMillis() + (accepted.toArray().length * sleepInterval * numberOfEvents)));
        rtn.put("text", "Simulation has started");

        getSimulatorService().simulateRandomEvents(accepted);
        log.info(rtn.toString());

        Thread.sleep(100);
        return rtn;
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
