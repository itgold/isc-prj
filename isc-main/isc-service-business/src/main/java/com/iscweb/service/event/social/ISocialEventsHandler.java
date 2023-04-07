package com.iscweb.service.event.social;

import com.fasterxml.jackson.databind.JsonNode;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;

/**
 * Common interface for a crating application.
 * events out of various social streams
 */
public interface ISocialEventsHandler {
    void handle(JsonNode data) throws ServiceException;
}
