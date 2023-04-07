
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for StatisticType.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="StatisticType"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Total"/&amp;gt;
 *     &amp;lt;enumeration value="Priority"/&amp;gt;
 *     &amp;lt;enumeration value="State"/&amp;gt;
 *     &amp;lt;enumeration value="Unknown"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "StatisticType", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm")
@XmlEnum
public enum StatisticType {

    @XmlEnumValue("Total")
    TOTAL("Total"),
    @XmlEnumValue("Priority")
    PRIORITY("Priority"),
    @XmlEnumValue("State")
    STATE("State"),
    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown");
    private final String value;

    StatisticType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatisticType fromValue(String v) {
        for (StatisticType c: StatisticType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
