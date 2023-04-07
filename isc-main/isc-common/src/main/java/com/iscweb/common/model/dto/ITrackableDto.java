package com.iscweb.common.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;

/**
 * Marker interface to inject tracking for updated fields
 *
 * Important: Do not override <code>isModified</code> method if you are not implementing whole field change tracking yourself.
 *
 * Note: The automatic change tracking is implemented only for DTOs in GraphQL queries and mutations by now.
 * Please <code>TrackableDtoProxy</code> and <code>GraphQlObjectMapperDecorator</code> for reference.
 * @see com.iscweb.common.util.TrackableDtoProxy
 * @see com.iscweb.component.web.controller.graphql.common.GraphQlObjectMapperDecorator
 */
public interface ITrackableDto {

    /**
     * Check if setter for the field of the object was called before.
     * Please note that for that method to work corresponding Object must be proxied with TrackableDtoProxy class.
     *
     * @see com.iscweb.common.util.TrackableDtoProxy
     * @param propertyName Field's name
     * @return <code>true</code> if the field was modified and <code>false</code> if opposite
     */
    @Transient
    @JsonIgnore
    default boolean isModified(String propertyName) {
        return true;
    }
}
