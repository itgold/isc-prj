package com.iscweb.common.events.alerts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.common.model.alert.AlertTriggerMatcherDto;
import com.iscweb.common.model.alert.matcher.RootClause;
import com.iscweb.common.util.MatcherContextUtils;
import lombok.Getter;
import org.springframework.data.annotation.Transient;

/**
 * Base class for all alert trigger matcher implementations
 */
public abstract class BaseAlertTriggerMatcher extends AlertTriggerMatcherDto {

    @JsonIgnore
    @Transient
    @Getter
    private final ObjectMapper jsonMapper;

    @JsonIgnore
    @Transient
    @Getter
    private final RootClause root;

    public BaseAlertTriggerMatcher(String json, ObjectMapper jsonMapper) throws JsonProcessingException {
        this.jsonMapper = jsonMapper;
        this.root = this.jsonMapper.readValue(json, RootClause.class);
    }

    @JsonIgnore
    @Transient
    public boolean isMatching(AlertMatchingContext context) {
        boolean isMatching = true; // matching empty matcher for any context

        // check "or" first
        if (root.getOr() != null) { // root is always use AND operator
            isMatching = MatcherContextUtils.matchOne(root.getOr(), context);
        }
        if (isMatching && root.getAnd() != null) {
            isMatching = MatcherContextUtils.matchAll(root.getAnd(), context);
        }

        return isMatching;
    }
}
