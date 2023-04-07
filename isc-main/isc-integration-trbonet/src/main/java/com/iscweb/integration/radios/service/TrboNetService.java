package com.iscweb.integration.radios.service;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.integration.doors.IEventStreamListener;
import com.iscweb.integration.radios.models.RadioDeviceDto;
import com.iscweb.integration.radios.models.RadioUserDto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

/**
 * TRBOnet system API service.
 */
@Slf4j
@Service
public class TrboNetService implements IApplicationSecuredService {

    @Getter
    @Setter(onMethod = @__({@Autowired, @Qualifier("TrboNetEventListener")}))
    private IEventStreamListener listener;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TrboNetRestService trboNetRestService;

    // integration.radios.trbonet.server.connectionTimeout

    @PostConstruct
    public void init() {
        try {
            getListener().startListener();
        } catch (SocketException e) {
            log.error("Unable to start TRBOnet events listener", e);
        }
    }

    public List<RadioDeviceDto> listDevices() throws ServiceException {
        RadioDeviceDto[] radios = getTrboNetRestService().getJson("/api/devices/listDevices", RadioDeviceDto[].class);
        return Arrays.asList(radios);
    }

    public List<RadioUserDto> listUsers() throws ServiceException {
        RadioUserDto[] users = getTrboNetRestService().getJson("/api/devices/listUsers", RadioUserDto[].class);
        return Arrays.asList(users);
    }
}
