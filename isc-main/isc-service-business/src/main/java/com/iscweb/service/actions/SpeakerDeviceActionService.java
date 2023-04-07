package com.iscweb.service.actions;

import com.iscweb.common.model.dto.entity.core.SpeakerDto;
import com.iscweb.service.SpeakerService;
import com.iscweb.service.entity.ISpeakerActionService;
import org.springframework.stereotype.Service;

@Service
public class SpeakerDeviceActionService extends BaseDeviceActionService<SpeakerDto, SpeakerService> implements ISpeakerActionService {
}
