package com.iscweb.common.sis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Service metadata.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SisServiceMetadata implements IMetadata {

    private String serviceName;

    private List<SisMethodMetadata> methods;

}
