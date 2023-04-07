package com.iscweb.integration.radios.service.state;

import com.iscweb.common.events.integration.radio.RadioStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.integration.radios.events.TrboNetStreamEventDto;

/**
 * Generate radio device connectivity status sub-state.
 */
public class RadioConnectionStateProducer implements IStateProducer<DeviceStateItemDto, RadioDto, TrboNetStreamEventDto> {

    @Override
    public boolean hasStateData(TrboNetStreamEventDto radio) {
        return false;
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, RadioDto model, TrboNetStreamEventDto radio) {
        return DeviceStateItemDto.builder().type(RadioStateType.COMMUNICATION.name()).value("UNKNOWN").build();
    }
}
