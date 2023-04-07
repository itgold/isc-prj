package com.iscweb.common.model.alert.matcher;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

/**
 * Alert trigger matcher item represent group of constraints
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonSerialize(using = MatcherClauseSerializer.class)
@JsonDeserialize(using = MatcherClauseDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatcherClause extends ArrayList<ItemClause> {

    @JsonProperty("and")
    private MatcherClause and;

    @JsonProperty("or")
    private MatcherClause or;
}
