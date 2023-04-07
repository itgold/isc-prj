package com.iscweb.service.actions;

import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.service.CameraService;
import com.iscweb.service.entity.ICameraActionService;
import org.springframework.stereotype.Service;

@Service
public class CameraDeviceActionService extends BaseDeviceActionService<CameraDto, CameraService> implements ICameraActionService {
}
