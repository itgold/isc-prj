package com.iscweb.integration.doors;

import com.ctc.wstx.api.WstxOutputProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.google.common.collect.Maps;
import com.iscweb.common.sis.ISisServiceMapper;
import com.iscweb.common.sis.exceptions.SisBusinessException;
import com.iscweb.common.sis.exceptions.SisParsingException;
import com.iscweb.common.sis.model.SisFieldMetadata;
import com.iscweb.common.sis.model.SisMethodMetadata;
import com.iscweb.common.util.StringUtils;
import com.iscweb.integration.doors.exceptions.SaltoParseResponseException;
import com.iscweb.integration.doors.exceptions.SaltoRequestException;
import com.iscweb.integration.doors.models.SaltoErrorDto;
import com.iscweb.integration.doors.models.internal.SaltoRequestDto;
import com.iscweb.integration.doors.serializers.NumericBooleanDeserializer;
import com.iscweb.integration.doors.serializers.NumericBooleanSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

/**
 * Integration service mapper for salto system.
 */
@Slf4j
public class SaltoSisServiceMapper implements ISisServiceMapper {

    private static final String NODE_DATA = "Params";

    private final XmlMapper objectMapper;

    private final XMLInputFactory inputFactory;

    /**
     * Default constructor.
     */
    public SaltoSisServiceMapper() {
        final JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);

        final SimpleModule myModule = new SimpleModule();
        myModule.addDeserializer(Boolean.class, new NumericBooleanDeserializer());
        myModule.addSerializer(Boolean.class, new NumericBooleanSerializer());

        objectMapper = new XmlMapper(xmlModule);
        objectMapper.registerModule(myModule);
        objectMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.getFactory()
                .getXMLOutputFactory()
                .setProperty(WstxOutputProperties.P_USE_DOUBLE_QUOTES_IN_XML_DECL, true);

        inputFactory = XMLInputFactory.newFactory();
    }

    /**
     * @see ISisServiceMapper#writeAsString(SisMethodMetadata, Object[])
     */
    @Override
    public String writeAsString(SisMethodMetadata metadata, Object[] args) throws SisParsingException {
        Object data;
        if (null != args && args.length == 1 && metadata.getArgs().get(0).getType() == SisFieldMetadata.Types.OBJECT) {
            data = args[0];
        } else if (null != args) {
            Map<String, Object> dataMap = new TreeMap<>();
            int index = 0;
            for (SisFieldMetadata arg : metadata.getArgs()) {
                dataMap.put(StringUtils.capitalize(arg.getName()), args[index++]);
            }
            data = dataMap;
        } else {
            data = Maps.newHashMap();
        }

        SaltoRequestDto request = new SaltoRequestDto(metadata.getName(), data);
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new SisParsingException("Unable to marshal value to string", e);
        }
    }

    /**
     * @see ISisServiceMapper#readFromString(String, Class)
     */
    @Override
    public <T> T readFromString(String value, Class<T> clazz) throws SisBusinessException {
        return parseResponse(value, clazz);
    }

    private void nextElement(XMLStreamReader sr) throws XMLStreamException {
        sr.next(); // to point to <root>
        while (sr.getEventType() != XMLStreamConstants.START_ELEMENT) {
            sr.next();
        }
    }

    private <T> T parseResponse(String value, Class<T> clazz) throws SaltoParseResponseException, SaltoRequestException {
        XMLStreamReader sr = null;
        try {
            sr = inputFactory.createXMLStreamReader(IOUtils.toInputStream(value, Charset.defaultCharset()));
            sr.next();

            while (sr.getEventType() != XMLStreamConstants.END_DOCUMENT) {
                String currentName = sr.getLocalName();
                if (SaltoErrorDto.NODE_NAME.equalsIgnoreCase(currentName)) {
                    SaltoErrorDto parsedValue = objectMapper.readValue(sr, SaltoErrorDto.class);
                    throw new SaltoRequestException(parsedValue);
                } else if (NODE_DATA.equalsIgnoreCase(currentName)) {
                    return objectMapper.readValue(sr, clazz);
                }

                nextElement(sr); // go next element
            }
        } catch (XMLStreamException | IOException e) {
            throw new SaltoParseResponseException("Unable to parse XML: " + value, e);
        } finally {
            if (null != sr) {
                try {
                    sr.close();
                } catch (XMLStreamException e) {
                    log.warn("Unable to close XML stream", e);
                }
            }
        }

        throw new SaltoParseResponseException("Unable to parse XML: " + value);
    }
}
