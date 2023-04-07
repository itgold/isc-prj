package com.iscweb.integration.cameras.mip.utils;

import com.iscweb.common.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.GregorianCalendar;

public final class XmlUtils {

    public static final String IMG_SERVER_MSG_FOOTER = "\r\n\r\n";

    /**
     * Current time as <code>XMLGregorianCalendar</code>
     *
     * @return New instance of <code>XMLGregorianCalendar</code> representing the current server time.
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar now() throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        return now;
    }

    public static Duration durationFromMilliSeconds(long durationInMilliSeconds) throws DatatypeConfigurationException {
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        return datatypeFactory.newDuration(durationInMilliSeconds);
    }

    public static <T> String toXml(T obj) throws JAXBException {
        String xml = StringUtils.EMPTY;

        if (obj != null) {
            Class<T> clazz = (Class<T>) obj.getClass();
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            marshaller.marshal(obj, stream);
            xml = new String(stream.toByteArray());
        }

        return xml;
    }

    public static <T> T fromXml(String xml, Class<T> clazz) throws JAXBException {
        JAXBContext jaxbContext= JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        return (T) unmarshaller.unmarshal(inputStream);
    }

    public static <T> T getValue(JAXBElement<T> el, T defaultValue) {
        return el != null ? el.getValue() : defaultValue;
    }

    public static <T> JAXBElement<T> createValue(String name, Class<T> clazz, T value) {
        return new JAXBElement<T>(QName.valueOf(name), clazz, value);
    }
}
