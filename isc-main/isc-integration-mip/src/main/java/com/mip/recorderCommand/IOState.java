
package com.mip.recorderCommand;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for IOState.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="IOState"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Unknown"/&amp;gt;
 *     &amp;lt;enumeration value="Activated"/&amp;gt;
 *     &amp;lt;enumeration value="Deactivated"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "IOState")
@XmlEnum
public enum IOState {

    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("Activated")
    ACTIVATED("Activated"),
    @XmlEnumValue("Deactivated")
    DEACTIVATED("Deactivated");
    private final String value;

    IOState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IOState fromValue(String v) {
        for (IOState c: IOState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
