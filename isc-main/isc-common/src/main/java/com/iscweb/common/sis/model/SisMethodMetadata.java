package com.iscweb.common.sis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Method metadata object.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SisMethodMetadata implements IMetadata {

    private String key;

    private String name;

    private Class<?> responseType;

    private List<SisFieldMetadata> args;
}
