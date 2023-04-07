
package com.mip.command;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SetCustomSettingResultType.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="SetCustomSettingResultType"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="UnspecifiedError"/&amp;gt;
 *     &amp;lt;enumeration value="Success"/&amp;gt;
 *     &amp;lt;enumeration value="ModifiedError"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "SetCustomSettingResultType")
@XmlEnum
public enum SetCustomSettingResultType {

    @XmlEnumValue("UnspecifiedError")
    UNSPECIFIED_ERROR("UnspecifiedError"),
    @XmlEnumValue("Success")
    SUCCESS("Success"),
    @XmlEnumValue("ModifiedError")
    MODIFIED_ERROR("ModifiedError");
    private final String value;

    SetCustomSettingResultType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SetCustomSettingResultType fromValue(String v) {
        for (SetCustomSettingResultType c: SetCustomSettingResultType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
