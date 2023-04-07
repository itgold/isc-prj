
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for AlarmFaultType.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="AlarmFaultType"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="UnknownSession"/&amp;gt;
 *     &amp;lt;enumeration value="Unknown"/&amp;gt;
 *     &amp;lt;enumeration value="DbRead"/&amp;gt;
 *     &amp;lt;enumeration value="Read"/&amp;gt;
 *     &amp;lt;enumeration value="DbConnection"/&amp;gt;
 *     &amp;lt;enumeration value="Serialization"/&amp;gt;
 *     &amp;lt;enumeration value="DbWrite"/&amp;gt;
 *     &amp;lt;enumeration value="DbUpdate"/&amp;gt;
 *     &amp;lt;enumeration value="InvalidAuthentication"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "AlarmFaultType", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm")
@XmlEnum
public enum AlarmFaultType {

    @XmlEnumValue("UnknownSession")
    UNKNOWN_SESSION("UnknownSession"),
    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("DbRead")
    DB_READ("DbRead"),
    @XmlEnumValue("Read")
    READ("Read"),
    @XmlEnumValue("DbConnection")
    DB_CONNECTION("DbConnection"),
    @XmlEnumValue("Serialization")
    SERIALIZATION("Serialization"),
    @XmlEnumValue("DbWrite")
    DB_WRITE("DbWrite"),
    @XmlEnumValue("DbUpdate")
    DB_UPDATE("DbUpdate"),
    @XmlEnumValue("InvalidAuthentication")
    INVALID_AUTHENTICATION("InvalidAuthentication");
    private final String value;

    AlarmFaultType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AlarmFaultType fromValue(String v) {
        for (AlarmFaultType c: AlarmFaultType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
