package com.iscweb.service.integration;

import com.iscweb.common.events.BaseIntegrationRawEvent;
import com.iscweb.common.events.CommonEventTypes;
import com.iscweb.common.events.UserSyncEvent;
import com.iscweb.common.events.integration.DeviceEventConverterResult;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.dto.entity.core.ExternalUserDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.service.integration.IDeviceEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

/**
 * User sync events processor implementation.
 */
@Slf4j
@Service("UserEventProcessorService")
public class UserEventProcessorService implements IDeviceEventProcessor {

    @Override
    public boolean isSupported(IEvent<ITypedPayload> event) {
        return event.getPayload() != null && (
                CommonEventTypes.USER_SYNC.code().equals(event.getPayload().getType())
        );
    }

    @Override
    public <P extends ITypedPayload, O extends IEvent<P>> List<O> parseRawEvents(BaseIntegrationRawEvent<ITypedPayload> deviceEvent) throws ServiceException {
        return null;
    }

    @Override
    public DeviceEventConverterResult process(IEvent<? extends ITypedPayload> event, @Nullable IExternalEntityDto deviceDto, @Nullable IDeviceStateDto deviceStateDto) throws ServiceException {
        DeviceEventConverterResult result = null;

        if (event.getPayload() != null && (
                CommonEventTypes.USER_SYNC.code().equals(event.getPayload().getType()))) {
            UserSyncEvent syncEvent = (UserSyncEvent) event;
            ExternalUserDto userDto = deviceDto != null ?
                    ((ExternalUserDto) deviceDto).updateFrom(syncEvent.getPayload().getUserDetails())
                    : syncEvent.getPayload().getUserDetails();
            userDto.setLastSyncTime(event.getEventTime());
            result = new DeviceEventConverterResult(userDto, null, null);
        }

        return result;
    }
}
