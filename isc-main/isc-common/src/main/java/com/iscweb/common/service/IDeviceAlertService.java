package com.iscweb.common.service;

import com.iscweb.common.events.BaseApplicationAlert;
import com.iscweb.common.model.event.ITypedPayload;

import java.util.List;

public interface IDeviceAlertService extends IApplicationSecuredService {
    List<BaseApplicationAlert<? extends ITypedPayload>> findAlarmsByDeviceId(String deviceId);
}
