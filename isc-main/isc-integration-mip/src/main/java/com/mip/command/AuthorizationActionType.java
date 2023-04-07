
package com.mip.command;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for AuthorizationActionType.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="AuthorizationActionType"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="None"/&amp;gt;
 *     &amp;lt;enumeration value="LogOn"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "AuthorizationActionType")
@XmlEnum
public enum AuthorizationActionType {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("LogOn")
    LOG_ON("LogOn");
    private final String value;

    AuthorizationActionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AuthorizationActionType fromValue(String v) {
        for (AuthorizationActionType c: AuthorizationActionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
