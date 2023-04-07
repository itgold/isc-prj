package com.iscweb.common.events.integration;

import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.event.ITypedPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnDemandDeviceSyncPayload implements ITypedPayload {

    private String type;

    private String triggeredByUser;

    private List<EntityType> entityTypes;

    private String jobId;
}
