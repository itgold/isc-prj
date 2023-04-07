package com.iscweb.common.events.integration;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseServerEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.event.DeviceIncrementalUpdatePayload;
import com.iscweb.common.model.event.IServerEvent;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EventPath(path = ServerNotAvailableEvent.PATH)
public class ServerNotAvailableEvent extends BaseServerEvent<ServerNotAvailableEvent.ServerNotAvailablePayload>
        implements IServerEvent<ServerNotAvailableEvent.ServerNotAvailablePayload> {
    public static final String PATH = "serverUnavailable";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(ServerNotAvailableEvent.PATH, super.getEventPath());
    }

    @Override
    public EntityType getDeviceType() {
        return getPayload() != null ? getPayload().getEntityType() : null;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ServerNotAvailablePayload extends DeviceIncrementalUpdatePayload<DoorDto> {
        private EntityType entityType;
        private Long serverId;
        private String serverName;

        public ServerNotAvailablePayload(EntityType entityType, Long serverId, String serverName) {
            super(entityType.name(), null, "SERVER", entityType.name() + " server '" + serverName + "' is not reachable.", null);
            this.serverId = serverId;
            this.serverName = serverName;
            this.entityType = entityType;
        }

        public String getDescription() {
            return entityType.name() + " server '" + serverName + "' is not reachable.";
        }
    }
}
