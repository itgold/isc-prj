package com.iscweb.service.actions;

import com.iscweb.common.model.dto.entity.core.SafetyDto;
import com.iscweb.service.SafetyService;
import com.iscweb.service.entity.ISafetyActionService;
import org.springframework.stereotype.Service;

@Service
public class SafetyDeviceActionService extends BaseDeviceActionService<SafetyDto, SafetyService> implements ISafetyActionService {
}
