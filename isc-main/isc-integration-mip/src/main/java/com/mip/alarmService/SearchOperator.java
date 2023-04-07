
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SearchOperator.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="SearchOperator"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Equals"/&amp;gt;
 *     &amp;lt;enumeration value="NotEquals"/&amp;gt;
 *     &amp;lt;enumeration value="LessThan"/&amp;gt;
 *     &amp;lt;enumeration value="GreaterThan"/&amp;gt;
 *     &amp;lt;enumeration value="Contains"/&amp;gt;
 *     &amp;lt;enumeration value="BeginsWith"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "SearchOperator", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm")
@XmlEnum
public enum SearchOperator {

    @XmlEnumValue("Equals")
    EQUALS("Equals"),
    @XmlEnumValue("NotEquals")
    NOT_EQUALS("NotEquals"),
    @XmlEnumValue("LessThan")
    LESS_THAN("LessThan"),
    @XmlEnumValue("GreaterThan")
    GREATER_THAN("GreaterThan"),
    @XmlEnumValue("Contains")
    CONTAINS("Contains"),
    @XmlEnumValue("BeginsWith")
    BEGINS_WITH("BeginsWith");
    private final String value;

    SearchOperator(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SearchOperator fromValue(String v) {
        for (SearchOperator c: SearchOperator.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
