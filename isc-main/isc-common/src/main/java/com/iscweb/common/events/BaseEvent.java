package com.iscweb.common.events;

import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Base abstract event implementation class.
 * The class contains declarations for all common event properties
 *
 * @param <P> Payload type
 */
@Data
public abstract class BaseEvent<P extends ITypedPayload> implements IEvent<P> {

    @NotBlank
    private P payload;

    private String eventId = UUID.randomUUID().toString();
    private String correlationId = UUID.randomUUID().toString();
    private String referenceId;
    private String origin;

    private ZonedDateTime eventTime;

    private ZonedDateTime receivedTime;

    public IEvent<P> payload(P payload) {
        this.payload = payload;
        return this;
    }

}
