package com.iscweb.common.sis;

import com.iscweb.common.sis.exceptions.SisBusinessException;
import com.iscweb.common.sis.exceptions.SisParsingException;
import com.iscweb.common.sis.model.SisMethodMetadata;

/**
 * Base contract for marshaling/un-marshaling implementation used by a specific integration framework.
 *
 * Different third party integration APIs are expecting communication through specific
 * <code>ISisServiceTransport</code> media using pre-defined payload formats. This interface defines abstraction
 * to implement different payload formats. For example: XML, JSON, custom text protocol, etc.
 */
public interface ISisServiceMapper {

    /**
     * Serializes given metadata object with the arguments into its String representation.
     *
     * @param metadata method metadata object instance.
     * @param args method arguments.
     *
     * @return metadata string.
     * @throws SisParsingException if unable to parse metadata information.
     */
    String writeAsString(SisMethodMetadata metadata, Object[] args) throws SisParsingException;

    /**
     * Deserializes method metadata information from its string representation.
     *
     * @param value string representation of method metadata.
     * @param clazz a class to deserialize to.
     * @param <T> type of the object to be deserialized.
     * @return a new instance of deserialized metadata object.
     * @throws SisBusinessException if operation failed.
     */
    <T> T readFromString(String value, Class<T> clazz) throws SisBusinessException;

    /**
     * Default method implementation for method metadata deserialization.
     *
     * @see this#readFromString(String, Class)
     */
    @SuppressWarnings("unchecked")
    default <T> T readFromString(SisMethodMetadata metadata, String value) throws SisBusinessException {
        return (T) readFromString(value, metadata.getResponseType());
    }
}
