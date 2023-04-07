package com.iscweb.common.service.integration;

import com.iscweb.common.events.BaseIntegrationRawEvent;
import com.iscweb.common.events.integration.DeviceEventConverterResult;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.service.IApplicationService;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Base interface for integration specific device events processor.
 * <p>
 * Objectives: Each integration should provide implementation for that interface to be able to parse and extract
 * data from raw device data and convert it into common models.
 */
public interface IDeviceEventProcessor extends IApplicationService {

    /**
     * Check if the provided device event supported by the specific processor implementation.
     *
     * @param event <code>true</code> id the event supported
     * @return true if provided event is supported by this implementation.
     */
    boolean isSupported(IEvent<ITypedPayload> event);

    /**
     * Parse raw device event into Java object. Extract device id to load correspondent information from DB.
     * Single device event can contains updates for multiple devices.
     *
     * @param deviceEvent Raw device event
     * @return List of multiple parsing context one per device.
     * @throws ServiceException if operation failed.
     * @param <P> typed payload for output application events.
     * @param <O> output application event type.
     */
    <P extends ITypedPayload, O extends IEvent<P>> List<O> parseRawEvents(BaseIntegrationRawEvent<ITypedPayload> deviceEvent) throws ServiceException;

    /**
     * Process each device update and generate updated device record and device state record. Optionally generate new application scoped event.
     *
     * @param event          Single Device event to process
     * @param deviceDto      Existing Device record from DB
     * @param deviceStateDto Current Device state
     * @return the result of device event conversion.
     * @throws ServiceException if operation failed.
     */
    DeviceEventConverterResult process(IEvent<? extends ITypedPayload> event, @Nullable IExternalEntityDto deviceDto, @Nullable IDeviceStateDto deviceStateDto) throws ServiceException;

}

