package com.iscweb.common.events.payload;

import com.iscweb.common.events.CommonEventTypes;
import com.iscweb.common.events.UserSyncEvent;
import com.iscweb.common.model.dto.entity.core.ExternalUserDto;
import com.iscweb.common.model.event.ITypedPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * External user sync event payload to carry external user changes.
 *
 * @see UserSyncEvent
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSyncPayload implements ITypedPayload {

    private ExternalUserDto userDetails;

    @Override
    public String getType() {
        return CommonEventTypes.USER_SYNC.code();
    }
}
