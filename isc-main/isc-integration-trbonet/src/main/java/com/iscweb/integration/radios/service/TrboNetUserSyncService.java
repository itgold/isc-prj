package com.iscweb.integration.radios.service;

import com.iscweb.common.events.UserSyncEvent;
import com.iscweb.common.events.integration.ServerNotAvailableEvent;
import com.iscweb.common.events.payload.UserSyncPayload;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.ExternalUserDto;
import com.iscweb.common.model.metadata.UserStatus;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.integration.IUserSyncService;
import com.iscweb.common.sis.exceptions.SisConnectionException;
import com.iscweb.integration.radios.models.RadioUserDto;
import com.iscweb.integration.radios.utils.TrboNetConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Sync service implementation for radio users backed up by TRBOnet system.
 */
@Slf4j
public class TrboNetUserSyncService implements IUserSyncService {

    private static final int MAX_SYNC_SKIP = 1;

    @Getter
    private final TrboNetService trboNetService;

    @Getter
    private final IEventHub eventHub;

    private final AtomicInteger connectionCount = new AtomicInteger(MAX_SYNC_SKIP);

    public TrboNetUserSyncService(TrboNetService trboNetService, IEventHub eventHub) {
        this.trboNetService = trboNetService;
        this.eventHub = eventHub;
    }

    @Override
    public String friendlyName() {
        return "TRBONET_USER_SYNC_SERVICE";
    }

    @Override
    public void process(String syncId, String batchId) throws SisConnectionException {
        ZonedDateTime syncTime = ZonedDateTime.now();
        try {
            List<RadioUserDto> response = getTrboNetService().listUsers();
            if (!CollectionUtils.isEmpty(response)) {
                for (RadioUserDto user : response) {
                    UserSyncEvent event = new UserSyncEvent();
                    event.setReferenceId(syncId);
                    event.setCorrelationId(batchId);
                    event.setExternalEntityId(String.valueOf(user.getId()));
                    event.setEventTime(syncTime);
                    event.setPayload(new UserSyncPayload(transform(user)));
                    getEventHub().post(event);
                }
            }
            log.info("... TRBOnet user service executed ...");
        } catch (ServiceException e) {
            if (e.getCause() != null && (e.getCause() instanceof ConnectException
                    || e.getCause() instanceof NoRouteToHostException
                    || e.getCause() instanceof ResourceAccessException)) {
                log.error("Unable to sync users: User service is not reachable");
            } else {
                log.error("Unable to sync users", e);
            }

            if (connectionCount.decrementAndGet() == 0) {
                ServerNotAvailableEvent event = new ServerNotAvailableEvent();
                event.setReferenceId(syncId);
                event.setCorrelationId(batchId);
                event.setPayload(new ServerNotAvailableEvent.ServerNotAvailablePayload(EntityType.USER, null, TrboNetConstants.INTEGRATION_NAME));
                try {
                    getEventHub().post(event);
                    connectionCount.set(MAX_SYNC_SKIP);
                } catch (ServiceException ex) {
                    log.error("Unable to post event", ex);
                }
            }
        }
    }

    private ExternalUserDto transform(RadioUserDto radioUser) {
        ExternalUserDto user = new ExternalUserDto();
        user.setSource(TrboNetConstants.INTEGRATION_NAME);
        user.setExternalId(String.valueOf(radioUser.getId()));
        user.setStatus(UserStatus.ACTIVATED);

        String fullName = radioUser.getFullName();
        if (fullName != null) {
            String[] names = fullName.split(" ");
            user.setFirstName(names[0]);
            user.setLastName(names.length > 0 ? StringUtils.join(Arrays.copyOfRange(names, 1, names.length), " ") : StringUtils.EMPTY);
        }

        user.setIdNumber(radioUser.getEmail());
        return user;
    }
}
