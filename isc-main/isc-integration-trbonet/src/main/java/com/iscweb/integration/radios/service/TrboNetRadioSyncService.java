package com.iscweb.integration.radios.service;

import com.iscweb.common.events.integration.ServerNotAvailableEvent;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.integration.ISyncService;
import com.iscweb.common.service.integration.radio.IRadioSyncService;
import com.iscweb.common.sis.exceptions.SisConnectionException;
import com.iscweb.integration.radios.events.RadioSyncEvent;
import com.iscweb.integration.radios.events.RadioSyncPayload;
import com.iscweb.integration.radios.models.RadioDeviceDto;
import com.iscweb.integration.radios.utils.TrboNetConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Sync service implementation for radio devices backed up by TRBOnet system.
 */
@Slf4j
public class TrboNetRadioSyncService implements IRadioSyncService {

    private static final int MAX_SYNC_SKIP = 1;

    @Getter
    private final TrboNetService trboNetService;

    @Getter
    private final IEventHub eventHub;

    private final AtomicInteger connectionCount = new AtomicInteger(MAX_SYNC_SKIP);

    public TrboNetRadioSyncService(TrboNetService trboNetService, IEventHub eventHub) {
        this.trboNetService = trboNetService;
        this.eventHub = eventHub;
    }

    /**
     * @see ISyncService#friendlyName()
     */
    @Override
    public String friendlyName() {
        return "TRBONET_RADIO_SYNC_SERVICE";
    }

    /**
     * @see ISyncService#process(String syncId, String batchId)
     */
    @Override
    public void process(String syncId, String batchId) throws SisConnectionException {
        try {
            ZonedDateTime syncTime = ZonedDateTime.now();
            List<RadioDeviceDto> response = getTrboNetService().listDevices();
            if (!CollectionUtils.isEmpty(response)) {
                for (RadioDeviceDto radio : response) {
                    RadioSyncEvent event = new RadioSyncEvent();
                    event.setReferenceId(syncId);
                    event.setCorrelationId(batchId);
                    event.setExternalEntityId(String.valueOf(radio.getId()));
                    event.setEventTime(syncTime);
                    event.setPayload(new RadioSyncPayload(radio));
                    getEventHub().post(event);
                }
            }
            log.info("... TRBOnet radio service executed ...");
        } catch (ServiceException e) {
            if (e.getCause() != null && (e.getCause() instanceof ConnectException
                    || e.getCause() instanceof NoRouteToHostException
                    || e.getCause() instanceof ResourceAccessException)) {

                log.error("Unable to sync radios: Radio service is not reachable");
            } else {
                log.error("Unable to sync radios", e);
            }

            if (connectionCount.decrementAndGet() == 0) {
                ServerNotAvailableEvent event = new ServerNotAvailableEvent();
                event.setReferenceId(syncId);
                event.setCorrelationId(batchId);
                event.setPayload(new ServerNotAvailableEvent.ServerNotAvailablePayload(EntityType.RADIO, null, TrboNetConstants.INTEGRATION_NAME));
                try {
                    getEventHub().post(event);
                    connectionCount.set(MAX_SYNC_SKIP);
                } catch (ServiceException ex) {
                    log.error("Unable to post event", ex);
                }
            }
        }
    }
}
