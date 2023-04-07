package com.iscweb.common.model.dto.api;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.iscweb.common.model.dto.IDto;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class BaseIndicatorsCorrelationResultDto implements IDto {

    private Map<String, Collection<String>> indicators = Maps.newHashMap();

    private Set<String> openSourceCorrelations = Sets.newHashSet();

    public Map<String, Collection<String>> getIndicators() {
        return indicators;
    }

    public void setIndicators(Map<String, Collection<String>> indicators) {
        this.indicators = indicators;
    }

    public Set<String> getOpenSourceCorrelations() {
        return openSourceCorrelations;
    }

    public void setOpenSourceCorrelations(Set<String> openSourceCorrelations) {
        this.openSourceCorrelations = openSourceCorrelations;
    }
}
