package com.iscweb.component.web.controller.rest;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseEvent;
import com.iscweb.common.events.BaseIntegrationRawEvent;
import com.iscweb.common.events.CommonEventTypes;
import com.iscweb.common.events.integration.door.DoorStateType;
import com.iscweb.common.events.payload.StringPayload;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationComponent;
import com.iscweb.common.model.alert.AlertTriggerDto;
import com.iscweb.common.model.alert.AlertTriggerMatcherDto;
import com.iscweb.common.model.alert.AlertTriggerMatcherType;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.model.dto.response.StringResponseDto;
import com.iscweb.common.model.event.camera.CameraEventItem;
import com.iscweb.common.model.event.camera.CameraEventPayload;
import com.iscweb.common.model.event.camera.CameraStateEvent;
import com.iscweb.common.model.event.camera.CameraStatusEvent;
import com.iscweb.common.model.event.door.DoorStateEvent;
import com.iscweb.common.model.metadata.RegionStatus;
import com.iscweb.common.model.metadata.RegionType;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.common.security.ISystemUser;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.integration.camera.ICameraService;
import com.iscweb.common.util.EventUtils;
import com.iscweb.service.AlertTriggerService;
import com.iscweb.service.DoorService;
import com.iscweb.service.RegionService;
import com.iscweb.service.composite.CompositeService;
import com.iscweb.service.event.matchers.DoorDloAlertProcessor;
import com.iscweb.service.integration.door.DoorDeviceProvider;
import com.iscweb.service.search.EventsHistorySearchService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController implements IApplicationComponent {

    private static final List<String> TEST_DEVICE_IDS = Lists.newArrayList(UUID.randomUUID().toString(),
                                                                           UUID.randomUUID().toString(),
                                                                           UUID.randomUUID().toString());

    private static final List<Callable<BaseEvent<?>>> DUMMY_EVENT_GENERATORS = Lists.newArrayList(new DoorStateEventGenerator(),
                                                                                                             new CameraStateEventGenerator(),
                                                                                                             new CameraStatusEventGenerator());

    private static final Random rand = new Random(System.currentTimeMillis());

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorService doorService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ICameraService cameraService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private EventsHistorySearchService eventsHistorySearchService;

    @Getter
    @Setter(onMethod = @__({@Autowired, @Qualifier("mainAsyncTaskScheduler")}))
    private TaskScheduler taskScheduler;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorDeviceProvider provider;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionService regionService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CompositeService compositeService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertTriggerService alertTriggerService;

    @RequestMapping(value = "/resolve-parent/{doorName}", method = RequestMethod.GET)
    public String resolveParent(@PathVariable(value = "doorName") String doorName) {
        String result; //region guid

        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            result = getDoorService().resolveParentRegion(EntityType.DOOR, doorName);
        }

        return result;
    }

    // Example: curl -X POST http://localhost:9090/test/generate/events/1000
    @RequestMapping(value = "/generate/events/{numberOfEvents}", method = RequestMethod.POST)
    public StringResponseDto<Void> generateTestEvent(@PathVariable(value = "numberOfEvents") int numberOfEvents) {

        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            for (int i = 0; i < numberOfEvents; i++) {
                try {
                    eventHub.post(generateEvent());
                } catch (Exception e) {
                    log.error("Unable to generate test event", e);
                }
            }
        }

        return StringResponseDto.of("Generated " + numberOfEvents + " events!");
    }
    @RequestMapping(value = "/region/update", method = RequestMethod.GET)
    public RegionDto regionUpdateByName() throws ServiceException {
        RegionDto result;

        RegionDto regionDto = new RegionDto();
        regionDto.setName("test");
        regionDto.setStatus(RegionStatus.ACTIVATED);
        regionDto.setType(RegionType.ROOM);
        regionDto.setParentIds(Sets.newHashSet("682e288f-5161-4ce2-964c-df5339c087d8"));

        RegionDto regionByName = getCompositeService().matchByName(regionDto);
        result = regionByName != null ? getRegionService().update(regionByName) : getRegionService().create(regionDto);

        return result;
    }

    // Example: curl -X GET http://localhost:9090/test/search/events
    @RequestMapping(value = "/search/events", method = RequestMethod.GET)
    public ResponseEntity<List<?>> searchTestEvent() {
        ResponseEntity<List<?>> response = ResponseEntity.badRequest().body(null);

        List<String> deviceIds = Lists.newArrayList(TEST_DEVICE_IDS);
        // todo(dmorozov): remove test hardcodeConfig01Main.java
        deviceIds.add("7c7f4f27-3d7e-4182-9bc5-6258ba95e464");
        deviceIds.add("c40154f4-f3df-4199-88bb-c9d7f44b5d75");

        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            // List<?> results = getEventsHistorySearchService().findCameraStatusEvents(deviceIds, PageRequest.of(0, 20));
            List<?> results = getEventsHistorySearchService().findDeviceStateEvents(deviceIds);
            response = ResponseEntity.ok(results);
        } catch (Exception e) {
            log.error("Unable to query ES", e);
        }

        return response;
    }

    // Example: curl -X POST http://localhost:9090/test/generate/doorEvents/open/keypad1
    @RequestMapping(value = "/generate/doorEvents/open/{doorExternalId}", method = RequestMethod.POST)
    public StringResponseDto<Void> generateDoorOpenEvent(@PathVariable(value = "doorExternalId") String externalId) {
        String rawOpenPayload = String.format("[\n" +
                "            {\n" +
                "                \"EventDateTime\": \"2021-01-22T15:59:00\",\n" +
                "                    \"EventDateTimeUTC\": \"2021-01-22T23:59:00Z\",\n" +
                "                    \"OperationID\": 17,\n" +
                "                    \"OperationDescription\": \"Door opened: key.\",\n" +
                "                    \"UserType\": 0,\n" +
                "                    \"UserName\": \"John Doe\",\n" +
                "                    \"UserExtID\": \"jdoe\",\n" +
                "                    \"UserCardSerialNumber\": \"04BD04229B6684\",\n" +
                "                    \"UserCardID\": \"04BD04229B6684\",\n" +
                "                    \"DoorName\": \"Black keypad\",\n" +
                "                    \"DoorExtID\": \"%s\"\n" +
                "            }\n" +
                "        ]", externalId);

        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            try {
                RawSaltoEvent deviceEvent = new RawSaltoEvent();
                deviceEvent.setEventTime(ZonedDateTime.now());
                deviceEvent.setPayload(new StringPayload(RawSaltoEvent.PATH, rawOpenPayload));
                eventHub.post(deviceEvent);

            } catch (Exception e) {
                log.error("Unable to generate test event", e);
            }
        }

        /*
            getTaskScheduler().schedule(() -> {
                generateDoorCloseEvent(externalId);
            }, new Date(System.currentTimeMillis() + 2000));
        */

        return StringResponseDto.of("Event Generated!");
    }

    @RequestMapping(value = "/generate/doorEvents/close/{doorExternalId}", method = RequestMethod.POST)
    public StringResponseDto<Void> generateDoorCloseEvent(@PathVariable(value = "doorExternalId") String externalId) {
        String rawClosePayload = String.format("[\n" +
                "            {\n" +
                "                \"EventDateTime\": \"2021-01-22T15:59:00\",\n" +
                "                    \"EventDateTimeUTC\": \"2021-01-22T23:59:00Z\",\n" +
                "                    \"OperationID\": 33,\n" +
                "                    \"OperationDescription\": \"Door closed: key.\",\n" +
                "                    \"UserType\": 0,\n" +
                "                    \"UserName\": \"John Doe\",\n" +
                "                    \"UserExtID\": \"jdoe\",\n" +
                "                    \"UserCardSerialNumber\": \"04BD04229B6684\",\n" +
                "                    \"UserCardID\": \"04BD04229B6684\",\n" +
                "                    \"DoorName\": \"Black keypad\",\n" +
                "                    \"DoorExtID\": \"%s\"\n" +
                "            }\n" +
                "        ]", externalId);

        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            try {
                RawSaltoEvent deviceCloseEvent = new RawSaltoEvent();
                deviceCloseEvent.setEventTime(ZonedDateTime.now());
                deviceCloseEvent.setPayload(new StringPayload(RawSaltoEvent.PATH, rawClosePayload));
                eventHub.post(deviceCloseEvent);
            } catch (Exception e) {
                log.error("Unable to generate test event", e);
            }
        }

        return StringResponseDto.of("Event Generated!");
    }

    // Example: curl -X POST http://localhost:9090/test/generate/doorEvents/reject/keypad1
    @RequestMapping(value = "/generate/doorEvents/reject/{doorExternalId}", method = RequestMethod.POST)
    public StringResponseDto<Void> generateDoorRejectEvent(@PathVariable(value = "doorExternalId") String externalId) {

        String rawOpenPayload = String.format("[\n" +
                "            {\n" +
                "                \"EventDateTime\": \"2021-01-22T15:59:00\",\n" +
                "                    \"EventDateTimeUTC\": \"2021-01-22T23:59:00Z\",\n" +
                "                    \"OperationID\": 84,\n" +
                "                    \"OperationDescription\": \"Opening not allowed: invalid key\",\n" +
                "                    \"UserType\": 0,\n" +
                "                    \"UserName\": \"John Doe\",\n" +
                "                    \"UserExtID\": \"jdoe\",\n" +
                "                    \"UserCardSerialNumber\": \"04BD04229B6684\",\n" +
                "                    \"UserCardID\": \"04BD04229B6684\",\n" +
                "                    \"DoorName\": \"Black keypad\",\n" +
                "                    \"DoorExtID\": \"%s\"\n" +
                "            }\n" +
                "        ]", externalId);

        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            try {
                RawSaltoEvent deviceEvent = new RawSaltoEvent();
                deviceEvent.setEventTime(ZonedDateTime.now());
                deviceEvent.setPayload(new StringPayload(RawSaltoEvent.PATH, rawOpenPayload));
                eventHub.post(deviceEvent);

            } catch (Exception e) {
                log.error("Unable to generate test event", e);
            }
        }

        return StringResponseDto.of("Event Generated!");
    }

    // Example: curl -X POST http://localhost:9090/test/generate/doorEvents/intrusion/keypad1
    @RequestMapping(value = "/generate/doorEvents/intrusion/{doorExternalId}", method = RequestMethod.POST)
    public StringResponseDto<Void> generateDoorIntrusionEvent(@PathVariable(value = "doorExternalId") String externalId) {

        String rawOpenPayload = String.format("[\n" +
                "            {\n" +
                "                \"EventDateTime\": \"2021-01-22T15:59:00\",\n" +
                "                    \"EventDateTimeUTC\": \"2021-01-22T23:59:00Z\",\n" +
                "                    \"OperationID\": 60,\n" +
                "                    \"OperationDescription\": \"Alarm: intrusion (online).\",\n" +
                "                    \"UserType\": 0,\n" +
                "                    \"UserName\": \"John Doe\",\n" +
                "                    \"UserExtID\": \"jdoe\",\n" +
                "                    \"UserCardSerialNumber\": \"04BD04229B6684\",\n" +
                "                    \"UserCardID\": \"04BD04229B6684\",\n" +
                "                    \"DoorName\": \"Black keypad\",\n" +
                "                    \"DoorExtID\": \"%s\"\n" +
                "            }\n" +
                "        ]", externalId);

        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            try {
                RawSaltoEvent deviceEvent = new RawSaltoEvent();
                deviceEvent.setEventTime(ZonedDateTime.now());
                deviceEvent.setPayload(new StringPayload(RawSaltoEvent.PATH, rawOpenPayload));
                eventHub.post(deviceEvent);

            } catch (Exception e) {
                log.error("Unable to generate test event", e);
            }
        }

        return StringResponseDto.of("Event Generated!");
    }

    // Example: curl -X POST http://localhost:9090/test/generate/doorEvents/dlo/test_door_1
    @RequestMapping(value = "/generate/doorEvents/dlo/{doorExternalId}", method = RequestMethod.POST)
    public StringResponseDto<Void> generateDLOEvent(@PathVariable(value = "doorExternalId") String externalId) {

        String rawOpenPayload = String.format("[\n" +
                "            {\n" +
                "                \"EventDateTime\": \"2021-01-22T15:59:00\",\n" +
                "                    \"EventDateTimeUTC\": \"2021-01-22T23:59:00Z\",\n" +
                "                    \"OperationID\": 62,\n" +
                "                    \"OperationDescription\": \"Door left opened (DLO).\",\n" +
                "                    \"UserType\": 0,\n" +
                "                    \"UserName\": \"John Doe\",\n" +
                "                    \"UserExtID\": \"jdoe\",\n" +
                "                    \"UserCardSerialNumber\": \"04BD04229B6684\",\n" +
                "                    \"UserCardID\": \"04BD04229B6684\",\n" +
                "                    \"DoorName\": \"Black keypad\",\n" +
                "                    \"DoorExtID\": \"%s\"\n" +
                "            }\n" +
                "        ]", externalId);

        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            try {
                RawSaltoEvent deviceEvent = new RawSaltoEvent();
                deviceEvent.setEventTime(ZonedDateTime.now());
                deviceEvent.setPayload(new StringPayload(RawSaltoEvent.PATH, rawOpenPayload));
                eventHub.post(deviceEvent);

            } catch (Exception e) {
                log.error("Unable to generate test event", e);
            }
        }

        return StringResponseDto.of("Event Generated!");
    }

    // Example: curl -X POST http://localhost:9090/test/generate/doorEvents/sync/keypad1
    @RequestMapping(value = "/generate/doorEvents/sync/{doorExternalId}", method = RequestMethod.POST)
    public StringResponseDto<Void> generateDoorSyncEvent(@PathVariable(value = "doorExternalId") String externalId) {
        /*
        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            try {
                DoorSyncEvent deviceEvent = new DoorSyncEvent();
                deviceEvent.setDeviceId(externalId);
                deviceEvent.setEventTime(ZonedDateTime.now());

                DoorSyncPayload payload = new DoorSyncPayload();
                SaltoDbDoorDto data = new SaltoDbDoorDto();
                payload.setData(data);
                data.setId(externalId);
                data.setName("Black keypad");
                data.setDescription("Black keypad");
                data.setOpeningMode(DoorOpeningMode.OFFICE);
                data.setBatteryStatus(15);
                data.setUpdateRequired(true);

                OnlineDoorStatusDto status = new OnlineDoorStatusDto();
                payload.setStatus(status);
                status.setDoorId(externalId);
                status.setDoorStatus(DoorStatus.INTRUSION);
                status.setCommStatus(CommStatus.ONLINE);
                status.setBatteryStatus(BatteryStatus.VERY_LOW);
                status.setTamperStatus(TamperStatus.ALARMED);

                deviceEvent.setPayload(payload);
                eventHub.post(deviceEvent);

            } catch (Exception e) {
                log.error("Unable to generate test event", e);
            }
        }
        */

        return StringResponseDto.of("Event Generated!");
    }

    // Example: curl -X POST http://localhost:9090/test/generate/cameraEvents/disconnect/virtual_camera_2
    @RequestMapping(value = "/generate/cameraEvents/disconnect/{cameraExternalId}", method = RequestMethod.POST)
    public StringResponseDto<Void> generateCameraDisconnectEvent(@PathVariable(value = "cameraExternalId") String externalId) {
        /*
        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            try {
                MipCameraStatusEventDto cameraStatusEvent = new MipCameraStatusEventDto();
                cameraStatusEvent.setStatus(Lists.newArrayList());
                cameraStatusEvent.getStatus().add(LiveStatusItem.fromType(LiveStatusItem.StatusType.ConnectionLost));
                IEvent<?> deviceEvent = new MipCameraDeviceEvent(externalId).payload(cameraStatusEvent);
                eventHub.post(deviceEvent);

            } catch (Exception e) {
                log.error("Unable to generate test event", e);
            }
        }
        */

        return StringResponseDto.of("Event Generated!");
    }

    // Example: curl -X POST http://localhost:9090/test/generate/cameraEvents/connect/virtual_camera_2
    @RequestMapping(value = "/generate/cameraEvents/connect/{cameraExternalId}", method = RequestMethod.POST)
    public StringResponseDto<Void> generateComeraConnectEvent(@PathVariable(value = "cameraExternalId") String externalId) {
        /*
        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            try {
                MipCameraStatusEventDto cameraStatusEvent = new MipCameraStatusEventDto();
                cameraStatusEvent.setStatus(Lists.newArrayList());
                cameraStatusEvent.getStatus().add(LiveStatusItem.fromType(LiveStatusItem.StatusType.ConnectionRestored));
                cameraStatusEvent.getStatus().add(LiveStatusItem.fromType(LiveStatusItem.StatusType.FeedWithMotion));
                cameraStatusEvent.getStatus().add(LiveStatusItem.fromType(LiveStatusItem.StatusType.FeedRecorded));
                IEvent<?> deviceEvent = new MipCameraDeviceEvent(externalId).payload(cameraStatusEvent);
                eventHub.post(deviceEvent);

            } catch (Exception e) {
                log.error("Unable to generate test event", e);
            }
        }
        */

        return StringResponseDto.of("Event Generated!");
    }

    // curl -X GET http://localhost:9090/test/cameraInfo
    @RequestMapping(value = "/cameraInfo", method = RequestMethod.GET)
    public StringResponseDto<Void> cameraInfo() {
        return StringResponseDto.of(getCameraService().serviceInfo());
    }

    // curl -X POST http://localhost:9090/test/generate/alertRules
    @RequestMapping(value = "/generate/alertRules", method = RequestMethod.POST)
    public StringResponseDto<Void> generateAlertRules() throws ServiceException {
        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            AlertTriggerDto config = getAlertTriggerService().findByName("TestConfig1");
            if (config == null) {
                config = new AlertTriggerDto();
                config.setName("TestConfig1");
                config.setProcessorType(DoorDloAlertProcessor.ALERT_TYPE);
                config.setActive(true);
                AlertTriggerMatcherDto matcher = new AlertTriggerMatcherDto();
                matcher.setType(AlertTriggerMatcherType.DATE_TIME);
                matcher.setBody("{\"and\": { \"items\": [ { \"property\": \"dateTime.date\", \"operator\": \">\", \"value\": \"2021-05-01\" },{ \"property\": \"dateTime.date\", \"operator\": \"<\", \"value\": \"2025-05-30\" } ]}}");
                config.setMatchers(Lists.newArrayList(matcher));

                getAlertTriggerService().create(config);
            }
        }

        return StringResponseDto.of("Event Generated!");
    }

    private BaseEvent<?> generateEvent() throws Exception {
        int idx = rand.nextInt(DUMMY_EVENT_GENERATORS.size());
        return DUMMY_EVENT_GENERATORS.get(idx).call();
    }

    private static String generateDeviceId() {
        int idx = rand.nextInt(TEST_DEVICE_IDS.size());
        return TEST_DEVICE_IDS.get(idx);
    }

    private static class DoorStateEventGenerator implements Callable<BaseEvent<?>> {
        @Override
        public BaseEvent<?> call() {
            Set<DeviceStateItemDto> state = Sets.newHashSet(
                    DeviceStateItemDto.builder().type(DoorStateType.COMMUNICATION.name()).value("ONLINE").build(),
                    DeviceStateItemDto.builder().type(DoorStateType.HARDWARE.name()).value("DOOR_OPENED_PPD").build(),
                    DeviceStateItemDto.builder().type(DoorStateType.MODE.name()).value("OFFICE").build(),
                    DeviceStateItemDto.builder().type(DoorStateType.STATUS.name()).value("OPENED").build(),
                    DeviceStateItemDto.builder().type(DoorStateType.INTRUSION.name()).value("ALARM_TAMPER_ONLINE").build(),
                    DeviceStateItemDto.builder().type(DoorStateType.BATTERY.name()).value("LOW_BATTERY_LEVEL").build()
            );
            return new DoorStateEvent(generateDeviceId(), state);
        }
    }
    private static class CameraStateEventGenerator implements Callable<BaseEvent<?>> {
        @Override
        public BaseEvent<?> call() {
            Set<DeviceStateItemDto> state = Sets.newHashSet(
                    DeviceStateItemDto.builder().type("batteryStatus").value("NORMAL").build(),
                    DeviceStateItemDto.builder().type("commStatus").value("ONLINE").build()
            );
            return new CameraStateEvent(generateDeviceId(), state);
        }
    }
    private static class CameraStatusEventGenerator implements Callable<BaseEvent<?>> {
        @Override
        public BaseEvent<?> call() {
            CameraStatusEvent event = new CameraStatusEvent(generateDeviceId());
            List<CameraEventItem> events = Lists.newArrayList();
            event.setPayload(new CameraEventPayload(generateDeviceId(), null, CommonEventTypes.CAMERA_STATUS.code(), System.currentTimeMillis(), events));
            return event;
        }
    }

    @EventPath(path = RawSaltoEvent.PATH)
    public static class RawSaltoEvent extends BaseIntegrationRawEvent<StringPayload> {

        public static final String PATH = "door.salto";

        @Override
        public String getEventPath() {
            return EventUtils.generatePath(RawSaltoEvent.PATH, super.getEventPath());
        }
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
