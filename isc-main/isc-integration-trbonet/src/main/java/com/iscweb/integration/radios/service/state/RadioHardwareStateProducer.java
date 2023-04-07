package com.iscweb.integration.radios.service.state;

import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.integration.radios.events.TrboNetStreamEventDto;

/**
 * Generate radio device hardware flags sub-state.
 */
public class RadioHardwareStateProducer implements IStateProducer<DeviceStateItemDto, RadioDto, TrboNetStreamEventDto> {

    @Override
    public boolean hasStateData(TrboNetStreamEventDto radio) {
        return false;
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, RadioDto model, TrboNetStreamEventDto radio) {
        return null;
    }
}
