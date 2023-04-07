package com.iscweb.common.model.event;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseServerEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.util.EventUtils;
import lombok.*;

@NoArgsConstructor
@EventPath(path = ServerErrorEvent.PATH)
public class ServerErrorEvent extends BaseServerEvent<ServerErrorEvent.ServerErrorEventPayload>
        implements IServerEvent<ServerErrorEvent.ServerErrorEventPayload> {

    public static final String PATH = "serverError";

    @Getter
    @Setter
    private EntityType deviceType;

    public ServerErrorEvent(EntityType entityType, Long serverId, String serverName, String errorMessage) {
        setDeviceType(entityType);
        setPayload(new ServerErrorEventPayload(entityType, serverId, serverName, errorMessage));
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(ServerErrorEvent.PATH, super.getEventPath());
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ServerErrorEventPayload extends DeviceIncrementalUpdatePayload<DoorDto> {
        private EntityType entityType;
        private Long serverId;
        private String serverName;
        private String errorMessage;

        public ServerErrorEventPayload(EntityType entityType, Long serverId, String serverName, String errorMessage) {
            super(entityType.name(), null, "SERVER", errorMessage, null);
            this.serverId = serverId;
            this.serverName = serverName;
            this.entityType = entityType;
            this.errorMessage = errorMessage;
        }

        public String getDescription() {
            return errorMessage;
        }
    }
}
