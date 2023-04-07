
package com.mip.command;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ResultStatus.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="ResultStatus"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Undefined"/&amp;gt;
 *     &amp;lt;enumeration value="Success"/&amp;gt;
 *     &amp;lt;enumeration value="Failed"/&amp;gt;
 *     &amp;lt;enumeration value="PartlySuccess"/&amp;gt;
 *     &amp;lt;enumeration value="MarkedDataNotFound"/&amp;gt;
 *     &amp;lt;enumeration value="FeatureNotLicensed"/&amp;gt;
 *     &amp;lt;enumeration value="RecorderNotFound"/&amp;gt;
 *     &amp;lt;enumeration value="ManagementServerUnavailable"/&amp;gt;
 *     &amp;lt;enumeration value="Unauthorized"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "ResultStatus", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService")
@XmlEnum
public enum ResultStatus {

    @XmlEnumValue("Undefined")
    UNDEFINED("Undefined"),
    @XmlEnumValue("Success")
    SUCCESS("Success"),
    @XmlEnumValue("Failed")
    FAILED("Failed"),
    @XmlEnumValue("PartlySuccess")
    PARTLY_SUCCESS("PartlySuccess"),
    @XmlEnumValue("MarkedDataNotFound")
    MARKED_DATA_NOT_FOUND("MarkedDataNotFound"),
    @XmlEnumValue("FeatureNotLicensed")
    FEATURE_NOT_LICENSED("FeatureNotLicensed"),
    @XmlEnumValue("RecorderNotFound")
    RECORDER_NOT_FOUND("RecorderNotFound"),
    @XmlEnumValue("ManagementServerUnavailable")
    MANAGEMENT_SERVER_UNAVAILABLE("ManagementServerUnavailable"),
    @XmlEnumValue("Unauthorized")
    UNAUTHORIZED("Unauthorized");
    private final String value;

    ResultStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ResultStatus fromValue(String v) {
        for (ResultStatus c: ResultStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
