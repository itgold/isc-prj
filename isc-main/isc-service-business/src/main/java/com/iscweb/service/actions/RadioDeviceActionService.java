package com.iscweb.service.actions;

import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.service.RadioService;
import com.iscweb.service.entity.IRadioActionService;
import org.springframework.stereotype.Service;

@Service
public class RadioDeviceActionService extends BaseDeviceActionService<RadioDto, RadioService> implements IRadioActionService {
}
