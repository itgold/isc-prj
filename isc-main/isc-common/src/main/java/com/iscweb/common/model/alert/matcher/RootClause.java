package com.iscweb.common.model.alert.matcher;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Root group item for the alert trigger matcher.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RootClause {
    @JsonProperty("and")
    private MatcherClause and;

    @JsonProperty("or")
    private MatcherClause or;
}
