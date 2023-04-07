package com.iscweb.integration.doors;

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
import com.iscweb.integration.doors.models.request.SaltoDbUsersRequestDto;
import com.iscweb.integration.doors.models.response.SaltoDbUsersResponseDto;
import com.iscweb.integration.doors.models.users.SaltoDbUserDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SaltoUserSyncService implements IUserSyncService {

    private static final int MAX_SYNC_SKIP = 1;

    @Getter
    private final ISaltoSisService saltoService;

    @Getter
    private final IEventHub eventHub;

    private final AtomicInteger connectionCount = new AtomicInteger(MAX_SYNC_SKIP);

    public SaltoUserSyncService(ISaltoSisService saltoService, IEventHub eventHub) {
        this.saltoService = saltoService;
        this.eventHub = eventHub;
    }

    @Override
    public String friendlyName() {
        return "SALTO_USER_SYNC_SERVICE";
    }

    @Override
    public void process(String syncId, String batchId) throws SisConnectionException {
        SaltoDbUsersResponseDto response;
        SaltoDbUsersRequestDto request = new SaltoDbUsersRequestDto();
        request.setMaxCount(100);

        ZonedDateTime syncTime = ZonedDateTime.now();
        try {
            response = getSaltoService().listUsers(request);
            while (response != null && response.getUsersList() != null && !CollectionUtils.isEmpty(response.getUsersList().getUsers())) {
                for (SaltoDbUserDto saltoUser : response.getUsersList().getUsers()) {
                    UserSyncEvent event = new UserSyncEvent();
                    event.setReferenceId(syncId);
                    event.setCorrelationId(batchId);
                    event.setExternalEntityId(saltoUser.getId());
                    event.setEventTime(syncTime);
                    event.setPayload(new UserSyncPayload(transform(saltoUser)));
                    getEventHub().post(event);
                }

                if (response.getEof() != null && !response.getEof()) {
                    List<SaltoDbUserDto> users = response.getUsersList() != null ? response.getUsersList().getUsers() : null;
                    if (!CollectionUtils.isEmpty(users)) {
                        SaltoDbUserDto lastUser = users.get(users.size() - 1);
                        request.setStartingFromExtUserId(lastUser.getId());
                        response = getSaltoService().listUsers(request);
                    } else {
                        response = null;
                    }
                } else {
                    response = null;
                }
            }
            log.info("... Salto user service executed ...");
        } catch (ServiceException e) {
            if (e.getCause() != null && (e.getCause() instanceof ConnectException || e.getCause() instanceof NoRouteToHostException)) {
                log.error("Unable to sync users: User service is not reachable");
            } else {
                log.error("Unable to sync users", e);
            }

            if (connectionCount.decrementAndGet() == 0) {
                ServerNotAvailableEvent event = new ServerNotAvailableEvent();
                event.setReferenceId(syncId);
                event.setCorrelationId(batchId);
                event.setPayload(new ServerNotAvailableEvent.ServerNotAvailablePayload(EntityType.USER, null, "Salto"));
                try {
                    getEventHub().post(event);
                    connectionCount.set(MAX_SYNC_SKIP);
                } catch (ServiceException ex) {
                    log.error("Unable to post event", ex);
                }
            }
        }
    }

    private ExternalUserDto transform(SaltoDbUserDto saltoUser) {
        ExternalUserDto user = new ExternalUserDto();
        user.setSource("Salto");
        user.setExternalId(saltoUser.getId());
        user.setTitle(saltoUser.getTitle());
        user.setFirstName(saltoUser.getFirstName());
        user.setLastName(saltoUser.getLastName());
        user.setPhoneNumber(saltoUser.getPhoneNumber());
        user.setStatus(saltoUser.getIsBanned() ? UserStatus.DEACTIVATED : UserStatus.ACTIVATED);
        user.setSchoolSite(saltoUser.getGpf1());
        user.setOfficialJobTitle(saltoUser.getGpf2());
        user.setIdFullName(saltoUser.getGpf3());
        user.setIdNumber(saltoUser.getGpf4());
        user.setOfficeClass(saltoUser.getGpf5());

        /*
            id = "dmorozov"
            firstName = "Denis"
            lastName = "Morozov"
            title = null
            office = {Boolean@23730} false
            auditOpenings = {Boolean@23716} true
            useAda = {Boolean@23730} false  extended opening time for disabilities. If true, doors accessed by the user will be kept opened longer time than usual. It defaults to 0 (false).
            useAntipassback = {Boolean@23730} false
            lockdownEnabled = {Boolean@23730} false this field allows cardholder to put doors in ‘lockdown’ mode.
            overrideLockdown = {Boolean@23730} false if enabled, cards will be able to open doors that are in ‘lockdown’ mode.
            overridePrivacy = {Boolean@23730} false
            useLockCalendar = {Boolean@23730} false
            calendarId = {Integer@23731} 1
            gpf1 = "School District"        // SchoolSite
            gpf2 = "DENIS MOROZOV"          // OfficialJobTitle
            gpf3 = "The whole district"     // IdFullName
            gpf4 = "123456789"              // IdNumber
            gpf5 = "Contractor"             // OfficeClass
            romCode = null
            currentRomCode = {SaltoDbBinaryDto@23790} "SaltoDbBinaryDto(data=[4, -67, 4, 34, -101, 102, -124])"
            userActivation = {Date@23791} "Wed Jul 29 11:51:40 UTC 2020" indicate the starting date/time of the user’s data and access permissions. It defaults to the current date.
            userExpirationEnabled = {Boolean@23730} false this field indicates whether the user may expire or may last forever.
            userExpiration = {Date@23792} "Sat Jan 01 00:00:00 UTC 2000" long-term expiration date of the user’s data and access permissions. It represents a maximum limit in that keys assigned to the user will never exceed this date.
            keyExpirationDifferentFromUserExpiration = {Boolean@23716} true this is a boolean field that
                    indicates whether or not keys assigned to the user must have the same
                    expiration as its owner (UserExpiration). If disabled (=0), the actual
                    expiration written on the key will span the whole period of the user, that is,
                    from UserActivation to UserExpiration. On the contrary, if enabled (=1), the
                    key’s valid period will be much shorter and will depend on
                    KeyRevalidation.UpdatePeriod (see next field)
            keyRevalidationUpdatePeriod = {Integer@23758} 30
            keyRevalidationUnitOfUpdatePeriod = {Integer@23736} 0
            currentKeyExists = {Boolean@23716} true
            currentKeyPeriod = {SaltoPeriodDto@23793} "SaltoPeriodDto(startDate=Sat Jan 01 00:00:00 UTC 2000, endDate=Thu Oct 01 00:00:00 UTC 2020)"
            currentKeyStatus = {KeyStatus@23738} "KEY_EXPIRED"
            pinEnabled = {Boolean@23730} false
            pinCode = null
            wiegandCode = null
            isBanned = {Boolean@23730} false
            keyIsCancellableThroughBlacklist = {Boolean@23716} true
            locationFunctionTimezoneTableId = {Integer@23736} 0
            mobileAppType = {MobileAppType@23760} "NONE"
            phoneNumber = "+19496112222"
            authorizationCodeList = {ArrayList@23795}  size = 1
            doorPermissions = null
            zonePermissions = null
            groupPermissions = null
            pictureFileName = null
            extLimitedEntryGroupId = null
        */
        return user;
    }
}
