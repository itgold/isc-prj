package com.iscweb.service.actions;

import com.iscweb.common.model.dto.entity.core.DroneDto;
import com.iscweb.service.DroneService;
import com.iscweb.service.entity.IDroneActionService;
import org.springframework.stereotype.Service;

@Service
public class DroneDeviceActionService extends BaseDeviceActionService<DroneDto, DroneService> implements IDroneActionService {
}
