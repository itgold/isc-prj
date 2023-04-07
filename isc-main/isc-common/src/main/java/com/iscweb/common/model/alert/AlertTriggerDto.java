package com.iscweb.common.model.alert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.events.alerts.AlertMatchingContext;
import com.iscweb.common.model.dto.BaseEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlertTriggerDto extends BaseEntityDto {

    private String name;

    private boolean active;

    private String processorType;

    private List<AlertTriggerMatcherDto> matchers;

    @Transient
    @JsonIgnore
    public boolean isMatching(AlertMatchingContext context) {
        boolean result = false;

        for (AlertTriggerMatcherDto matcher : getMatchers()) {
            if (matcher.isMatching(context)) {
                result = true;
                break;
            }
        }

        return result;
    }

    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.ALERT_TRIGGER;
    }
}
