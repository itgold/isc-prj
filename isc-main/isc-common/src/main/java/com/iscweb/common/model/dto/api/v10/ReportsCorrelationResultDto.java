package com.iscweb.common.model.dto.api.v10;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.iscweb.common.model.dto.BaseDto;

import java.util.Map;
import java.util.Set;

public class ReportsCorrelationResultDto extends BaseDto {

    private Map<String, Set<String>> files = Maps.newHashMap();

    private Set<String> externalIntelligence = Sets.newHashSet();

    public void addInternal(String correlationType, String correlationId) {
        Set<String> correlations = getFiles().computeIfAbsent(correlationType, k -> Sets.newHashSet());
        correlations.add(correlationId);
    }

    public Map<String, Set<String>> getFiles() {
        return files;
    }

    public void setFiles(
            Map<String, Set<String>> files) {
        this.files = files;
    }

    public Set<String> getExternalIntelligence() {
        return externalIntelligence;
    }

    public void setExternalIntelligence(Set<String> externalIntelligence) {
        this.externalIntelligence = externalIntelligence;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("File", getFiles().size())
                          .add("extInt", getExternalIntelligence().size())
                          .toString();
    }
}
