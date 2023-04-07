package com.iscweb.service.search;

import com.iscweb.common.events.BaseApplicationAlert;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.service.IDeviceAlertService;
import com.iscweb.search.repositories.ApplicationEventSearchRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class DeviceAlertService implements IDeviceAlertService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ApplicationEventSearchRepository eventRepository;

    @Override
    public List<BaseApplicationAlert<? extends ITypedPayload>> findAlarmsByDeviceId(String deviceId) {
        return Collections.emptyList();
    }
}
