package com.iscweb.search.model;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.util.List;

@Getter
@TypeAlias(StateEventVo.TYPE)
public class StateEventVo extends ApplicationEventVo {

    public static final String TYPE = "state";
    private final List<ApplicationEventVo> events = Lists.newArrayList();

    public StateEventVo(ApplicationEventVo event) {
        super(StateEventVo.TYPE);

        this.setEventId(event.getEventId());
        this.setDeviceId(event.getDeviceId());
        this.setType(event.getType());
        this.setTime(event.getTime());
        this.setCorrelationId(event.getCorrelationId());
        this.setReferenceId(event.getReferenceId());
        this.setPayload(event.getPayload());
    }

    public void addEvent(ApplicationEventVo event) {
        events.add(event);
    }
}
