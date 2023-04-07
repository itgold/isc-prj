package com.iscweb.common.model.alert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.events.alerts.AlertMatchingContext;
import com.iscweb.common.model.dto.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertTriggerMatcherDto implements IDto {

    private AlertTriggerMatcherType type;

    private String body;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    @Transient
    @JsonIgnore
    public boolean isMatching(AlertMatchingContext context) {
        return false;
    }
}
