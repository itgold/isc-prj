package com.iscweb.service.actions;

import com.iscweb.common.model.dto.entity.core.UtilityDto;
import com.iscweb.service.UtilityService;
import com.iscweb.service.entity.IUtilityActionService;
import org.springframework.stereotype.Service;

@Service
public class UtilityDeviceActionService extends BaseDeviceActionService<UtilityDto, UtilityService> implements IUtilityActionService {
}
