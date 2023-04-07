package com.iscweb.common.model.alert.matcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Alert trigger matcher item represent single value constraint
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemClause {
    String property;

    Operator operator;

    Object value;
}
