
package com.mip.command;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for IntegrationType.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="IntegrationType"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Client"/&amp;gt;
 *     &amp;lt;enumeration value="Administration"/&amp;gt;
 *     &amp;lt;enumeration value="EventServer"/&amp;gt;
 *     &amp;lt;enumeration value="Standalone"/&amp;gt;
 *     &amp;lt;enumeration value="DeviceDriver"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "IntegrationType")
@XmlEnum
public enum IntegrationType {

    @XmlEnumValue("Client")
    CLIENT("Client"),
    @XmlEnumValue("Administration")
    ADMINISTRATION("Administration"),
    @XmlEnumValue("EventServer")
    EVENT_SERVER("EventServer"),
    @XmlEnumValue("Standalone")
    STANDALONE("Standalone"),
    @XmlEnumValue("DeviceDriver")
    DEVICE_DRIVER("DeviceDriver");
    private final String value;

    IntegrationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IntegrationType fromValue(String v) {
        for (IntegrationType c: IntegrationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
