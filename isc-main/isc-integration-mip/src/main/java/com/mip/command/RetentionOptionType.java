
package com.mip.command;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RetentionOptionType.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="RetentionOptionType"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Unused"/&amp;gt;
 *     &amp;lt;enumeration value="Days"/&amp;gt;
 *     &amp;lt;enumeration value="Weeks"/&amp;gt;
 *     &amp;lt;enumeration value="Months"/&amp;gt;
 *     &amp;lt;enumeration value="Years"/&amp;gt;
 *     &amp;lt;enumeration value="UserDefined"/&amp;gt;
 *     &amp;lt;enumeration value="Indefinite"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "RetentionOptionType", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService")
@XmlEnum
public enum RetentionOptionType {

    @XmlEnumValue("Unused")
    UNUSED("Unused"),
    @XmlEnumValue("Days")
    DAYS("Days"),
    @XmlEnumValue("Weeks")
    WEEKS("Weeks"),
    @XmlEnumValue("Months")
    MONTHS("Months"),
    @XmlEnumValue("Years")
    YEARS("Years"),
    @XmlEnumValue("UserDefined")
    USER_DEFINED("UserDefined"),
    @XmlEnumValue("Indefinite")
    INDEFINITE("Indefinite");
    private final String value;

    RetentionOptionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RetentionOptionType fromValue(String v) {
        for (RetentionOptionType c: RetentionOptionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
