
package com.mip.command;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SetViewGroupDataResultType.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="SetViewGroupDataResultType"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="UnspecifiedError"/&amp;gt;
 *     &amp;lt;enumeration value="Success"/&amp;gt;
 *     &amp;lt;enumeration value="ReadOnlyError"/&amp;gt;
 *     &amp;lt;enumeration value="ModifiedError"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "SetViewGroupDataResultType")
@XmlEnum
public enum SetViewGroupDataResultType {

    @XmlEnumValue("UnspecifiedError")
    UNSPECIFIED_ERROR("UnspecifiedError"),
    @XmlEnumValue("Success")
    SUCCESS("Success"),
    @XmlEnumValue("ReadOnlyError")
    READ_ONLY_ERROR("ReadOnlyError"),
    @XmlEnumValue("ModifiedError")
    MODIFIED_ERROR("ModifiedError");
    private final String value;

    SetViewGroupDataResultType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SetViewGroupDataResultType fromValue(String v) {
        for (SetViewGroupDataResultType c: SetViewGroupDataResultType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
