
package com.mip.recorderCommand;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SmartSearchStatusType.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="SmartSearchStatusType"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="UnspecifiedError"/&amp;gt;
 *     &amp;lt;enumeration value="SearchInProgress"/&amp;gt;
 *     &amp;lt;enumeration value="SearchResultReady"/&amp;gt;
 *     &amp;lt;enumeration value="SearchEndTimeReached"/&amp;gt;
 *     &amp;lt;enumeration value="SearchCancelled"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "SmartSearchStatusType")
@XmlEnum
public enum SmartSearchStatusType {

    @XmlEnumValue("UnspecifiedError")
    UNSPECIFIED_ERROR("UnspecifiedError"),
    @XmlEnumValue("SearchInProgress")
    SEARCH_IN_PROGRESS("SearchInProgress"),
    @XmlEnumValue("SearchResultReady")
    SEARCH_RESULT_READY("SearchResultReady"),
    @XmlEnumValue("SearchEndTimeReached")
    SEARCH_END_TIME_REACHED("SearchEndTimeReached"),
    @XmlEnumValue("SearchCancelled")
    SEARCH_CANCELLED("SearchCancelled");
    private final String value;

    SmartSearchStatusType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SmartSearchStatusType fromValue(String v) {
        for (SmartSearchStatusType c: SmartSearchStatusType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
