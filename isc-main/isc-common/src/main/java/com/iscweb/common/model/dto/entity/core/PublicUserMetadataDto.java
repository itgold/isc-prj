package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.common.model.dto.IDto;
import lombok.Data;

/**
 * A DTO whose purpose is to expose public user metadata to the frontend. It was initially created to
 * expose user aliases on the frontend so that the chat could display user aliases based on the ID that
 * comes over the wire through PubNub.
 */
@Data
public class PublicUserMetadataDto implements IDto {
    private String guid;
    private String alias;
    private boolean admin;

    public static PublicUserMetadataDto valueOf(String guid, String alias, boolean admin) {
        PublicUserMetadataDto result = new PublicUserMetadataDto();
        result.setGuid(guid);
        result.setAlias(alias);
        result.setAdmin(admin);
        return result;
    }

    /**
     * An explicit property value mapping for the frontend. When stringified, an instance is expected
     * to have an `isAdmin` property, not an `admin` property. This is a JS convention.
     */
    @JsonProperty(value = "isAdmin")
    public boolean isAdmin() {
        return admin;
    }
}
