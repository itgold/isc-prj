package com.iscweb.common.model.metadata;

/**
 * Generic interface to indicate an enum over a range of possible values for a certain attribute of report
 * metadata.
 * <p>Enumerations implementing this interface must annotate their members
 * with a {@link com.fasterxml.jackson.annotation.JsonProperty} except when their name match the string being
 * looked up.</p>
 */
public interface AttributeEnum {
}
