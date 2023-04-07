
package com.mip.config;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for EnableFilter.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="EnableFilter"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="All"/&amp;gt;
 *     &amp;lt;enumeration value="Enabled"/&amp;gt;
 *     &amp;lt;enumeration value="Disabled"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "EnableFilter", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI")
@XmlEnum
public enum EnableFilter {

    @XmlEnumValue("All")
    ALL("All"),
    @XmlEnumValue("Enabled")
    ENABLED("Enabled"),
    @XmlEnumValue("Disabled")
    DISABLED("Disabled");
    private final String value;

    EnableFilter(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnableFilter fromValue(String v) {
        for (EnableFilter c: EnableFilter.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
