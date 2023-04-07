package com.iscweb.service.converter.impl;

import com.iscweb.common.model.alert.AlertTriggerDto;
import com.iscweb.common.model.alert.AlertTriggerMatcherDto;
import com.iscweb.persistence.model.jpa.AlertTriggerJpa;
import com.iscweb.persistence.model.jpa.AlertTriggerMatcherJpa;
import com.iscweb.service.converter.BaseConverter;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.SecurityLevel;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

public class AlertTriggerConverter extends BaseConverter<AlertTriggerDto, AlertTriggerJpa> {

    public AlertTriggerConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    @Override
    protected AlertTriggerJpa createJpa() {
        return new AlertTriggerJpa();
    }

    @Override
    protected AlertTriggerDto createDto() {
        return new AlertTriggerDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected AlertTriggerJpa toJpa() {
        AlertTriggerDto dto = getDto();
        AlertTriggerJpa result = super.toJpa();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (dto.isModified("name")) {
                result.setName(dto.getName());
            }
            if (dto.isModified("processorType")) {
                result.setProcessorType(dto.getProcessorType());
            }
            result.setActive(dto.isActive());
            if (dto.isModified("matchers")) {
                result.getMatchers().clear();

                if (!CollectionUtils.isEmpty(dto.getMatchers())) {
                    result.getMatchers().addAll(dto.getMatchers()
                            .stream()
                            .map(matcher -> {
                                AlertTriggerMatcherJpa matcherJpa = new AlertTriggerMatcherJpa(matcher.getType(), matcher.getBody(), matcher.getCreated(), matcher.getUpdated());
                                matcherJpa.setCreated(ZonedDateTime.now());
                                matcherJpa.setUpdated(ZonedDateTime.now());
                                return matcherJpa;
                            })
                            .collect(Collectors.toSet()));
                }
            }
        }

        return result;
    }

    /**
     * @see super#toDto()
     */
    @Override
    protected AlertTriggerDto toDto() {
        AlertTriggerJpa jpa = getJpa();
        AlertTriggerDto result = super.toDto();

        result.setActive(jpa.isActive());
        result.setName(jpa.getName());
        result.setProcessorType(jpa.getProcessorType());

        if (!CollectionUtils.isEmpty(jpa.getMatchers())) {
            result.setMatchers(jpa.getMatchers()
                    .stream()
                    .map(matcher -> new AlertTriggerMatcherDto(matcher.getType(), matcher.getBody(), matcher.getCreated(), matcher.getUpdated()))
                    .collect(Collectors.toList()));
        }

        return result;
    }
}
