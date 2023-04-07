package com.iscweb.integration.doors;

import com.iscweb.common.events.integration.ServerNotAvailableEvent;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.integration.ISyncService;
import com.iscweb.common.service.integration.door.IDoorSyncService;
import com.iscweb.common.sis.exceptions.SisConnectionException;
import com.iscweb.integration.doors.events.DoorSyncEvent;
import com.iscweb.integration.doors.events.DoorSyncPayload;
import com.iscweb.integration.doors.models.doors.OnlineDoorStatusDto;
import com.iscweb.integration.doors.models.doors.SaltoDbDoorDto;
import com.iscweb.integration.doors.models.doors.SaltoExtDoorIdListDto;
import com.iscweb.integration.doors.models.request.SaltoDbDoorsRequestDto;
import com.iscweb.integration.doors.models.request.SaltoEmergencyOpenRequestDto;
import com.iscweb.integration.doors.models.response.OnlineDoorStatusListResponseDto;
import com.iscweb.integration.doors.models.response.SaltoDbDoorsResponseDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class SaltoDoorSyncService implements IDoorSyncService {

    private static final int MAX_SYNC_SKIP = 1;

    @Getter
    private final ISaltoSisService saltoService;

    @Getter
    private final IEventHub eventHub;

    private final AtomicInteger connectionCount = new AtomicInteger(MAX_SYNC_SKIP);

    public SaltoDoorSyncService(ISaltoSisService saltoService, IEventHub eventHub) {
        this.saltoService = saltoService;
        this.eventHub = eventHub;
    }

    /**
     * @see ISyncService#friendlyName()
     */
    @Override
    public String friendlyName() {
        return "SALTO_DOOR_SYNC_SERVICE";
    }

    /**
     * @see ISyncService#process(String syncId, String batchId)
     */
    @Override
    public void process(String syncId, String batchId) throws SisConnectionException {
        SaltoDbDoorsResponseDto response;
        SaltoDbDoorsRequestDto request = new SaltoDbDoorsRequestDto();
        request.setMaxCount(100);

        try {
            ZonedDateTime syncTime = ZonedDateTime.now();
            response = getSaltoService().listDoors(request);
            while (response != null && response.getDoorsList() != null && !CollectionUtils.isEmpty(response.getDoorsList().getDoors())) {
                List<String> doorIds = response.getDoorsList().getDoors().stream().map(SaltoDbDoorDto::getId).collect(Collectors.toList());
                Map<String, OnlineDoorStatusDto> statuses = collectStatus(doorIds);
                for (SaltoDbDoorDto saltoDoor : response.getDoorsList().getDoors()) {
                    OnlineDoorStatusDto status = statuses.get(saltoDoor.getId());
                    DoorSyncEvent event = new DoorSyncEvent();
                    event.setReferenceId(syncId);
                    event.setCorrelationId(batchId);
                    event.setExternalEntityId(saltoDoor.getId());
                    event.setEventTime(syncTime);
                    event.setPayload(new DoorSyncPayload(saltoDoor, status));
                    getEventHub().post(event);
                }

                if (response.getEof() != null && !response.getEof()) {
                    List<SaltoDbDoorDto> doors = response.getDoorsList() != null ? response.getDoorsList().getDoors() : null;
                    if (!CollectionUtils.isEmpty(doors)) {
                        SaltoDbDoorDto lastDoor = doors.get(doors.size() - 1);
                        request.setStartingFromExtDoorId(lastDoor.getId());
                        syncTime = ZonedDateTime.now();
                        response = getSaltoService().listDoors(request);
                    } else {
                        response = null;
                    }
                } else {
                    response = null;
                }
            }
            log.info("... Salto door service executed ...");
        } catch (ServiceException e) {
            if (e.getCause() != null && (e.getCause() instanceof ConnectException || e.getCause() instanceof NoRouteToHostException)) {
                log.error("Unable to sync doors: Door service is not reachable");
            } else {
                log.error("Unable to sync doors", e);
            }

            if (connectionCount.decrementAndGet() == 0) {
                ServerNotAvailableEvent event = new ServerNotAvailableEvent();
                event.setReferenceId(syncId);
                event.setCorrelationId(batchId);
                event.setPayload(new ServerNotAvailableEvent.ServerNotAvailablePayload(EntityType.DOOR, null, "Salto"));
                try {
                    getEventHub().post(event);
                    connectionCount.set(MAX_SYNC_SKIP);
                } catch (ServiceException ex) {
                    log.error("Unable to post event", ex);
                }
            }
        }
    }

    private Map<String, OnlineDoorStatusDto> collectStatus(List<String> doorIds) throws SisConnectionException {
        SaltoEmergencyOpenRequestDto request = new SaltoEmergencyOpenRequestDto();
        request.setDoorIds(new SaltoExtDoorIdListDto(doorIds));
        OnlineDoorStatusListResponseDto statuses = getSaltoService().onlineDoorStatus(request);
        return statuses.getDoorsList().getDoors()
                .stream()
                .collect(Collectors.toMap(OnlineDoorStatusDto::getDoorId, status -> status));
    }
}
