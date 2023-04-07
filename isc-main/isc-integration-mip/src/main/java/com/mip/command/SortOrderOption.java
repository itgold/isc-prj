
package com.mip.command;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SortOrderOption.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="SortOrderOption"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Header"/&amp;gt;
 *     &amp;lt;enumeration value="Description"/&amp;gt;
 *     &amp;lt;enumeration value="StartTime"/&amp;gt;
 *     &amp;lt;enumeration value="TagTime"/&amp;gt;
 *     &amp;lt;enumeration value="EndTime"/&amp;gt;
 *     &amp;lt;enumeration value="CreateTime"/&amp;gt;
 *     &amp;lt;enumeration value="RetentionExpireTime"/&amp;gt;
 *     &amp;lt;enumeration value="UserName"/&amp;gt;
 *     &amp;lt;enumeration value="Size"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "SortOrderOption", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService")
@XmlEnum
public enum SortOrderOption {

    @XmlEnumValue("Header")
    HEADER("Header"),
    @XmlEnumValue("Description")
    DESCRIPTION("Description"),
    @XmlEnumValue("StartTime")
    START_TIME("StartTime"),
    @XmlEnumValue("TagTime")
    TAG_TIME("TagTime"),
    @XmlEnumValue("EndTime")
    END_TIME("EndTime"),
    @XmlEnumValue("CreateTime")
    CREATE_TIME("CreateTime"),
    @XmlEnumValue("RetentionExpireTime")
    RETENTION_EXPIRE_TIME("RetentionExpireTime"),
    @XmlEnumValue("UserName")
    USER_NAME("UserName"),
    @XmlEnumValue("Size")
    SIZE("Size");
    private final String value;

    SortOrderOption(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SortOrderOption fromValue(String v) {
        for (SortOrderOption c: SortOrderOption.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
