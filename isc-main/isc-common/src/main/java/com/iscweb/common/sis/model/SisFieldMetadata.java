package com.iscweb.common.sis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Field metadata object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SisFieldMetadata implements IMetadata {

    public enum Types {
        STRING, BOOL, INTEGER, LONG, DOUBLE, OBJECT
    }

    private String name;

    private Types type;
}
