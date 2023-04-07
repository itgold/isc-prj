
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for Target.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="Target"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="LocalId"/&amp;gt;
 *     &amp;lt;enumeration value="Id"/&amp;gt;
 *     &amp;lt;enumeration value="State"/&amp;gt;
 *     &amp;lt;enumeration value="Priority"/&amp;gt;
 *     &amp;lt;enumeration value="Timestamp"/&amp;gt;
 *     &amp;lt;enumeration value="SourceName"/&amp;gt;
 *     &amp;lt;enumeration value="Name"/&amp;gt;
 *     &amp;lt;enumeration value="AssignedTo"/&amp;gt;
 *     &amp;lt;enumeration value="Type"/&amp;gt;
 *     &amp;lt;enumeration value="RuleType"/&amp;gt;
 *     &amp;lt;enumeration value="Location"/&amp;gt;
 *     &amp;lt;enumeration value="Message"/&amp;gt;
 *     &amp;lt;enumeration value="CustomTag"/&amp;gt;
 *     &amp;lt;enumeration value="VendorName"/&amp;gt;
 *     &amp;lt;enumeration value="ObjectValue"/&amp;gt;
 *     &amp;lt;enumeration value="Description"/&amp;gt;
 *     &amp;lt;enumeration value="ObjectId"/&amp;gt;
 *     &amp;lt;enumeration value="Modified"/&amp;gt;
 *     &amp;lt;enumeration value="PriorityName"/&amp;gt;
 *     &amp;lt;enumeration value="Category"/&amp;gt;
 *     &amp;lt;enumeration value="CategoryName"/&amp;gt;
 *     &amp;lt;enumeration value="StateName"/&amp;gt;
 *     &amp;lt;enumeration value="CameraId"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "Target", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm")
@XmlEnum
public enum Target {

    @XmlEnumValue("LocalId")
    LOCAL_ID("LocalId"),
    @XmlEnumValue("Id")
    ID("Id"),
    @XmlEnumValue("State")
    STATE("State"),
    @XmlEnumValue("Priority")
    PRIORITY("Priority"),
    @XmlEnumValue("Timestamp")
    TIMESTAMP("Timestamp"),
    @XmlEnumValue("SourceName")
    SOURCE_NAME("SourceName"),
    @XmlEnumValue("Name")
    NAME("Name"),
    @XmlEnumValue("AssignedTo")
    ASSIGNED_TO("AssignedTo"),
    @XmlEnumValue("Type")
    TYPE("Type"),
    @XmlEnumValue("RuleType")
    RULE_TYPE("RuleType"),
    @XmlEnumValue("Location")
    LOCATION("Location"),
    @XmlEnumValue("Message")
    MESSAGE("Message"),
    @XmlEnumValue("CustomTag")
    CUSTOM_TAG("CustomTag"),
    @XmlEnumValue("VendorName")
    VENDOR_NAME("VendorName"),
    @XmlEnumValue("ObjectValue")
    OBJECT_VALUE("ObjectValue"),
    @XmlEnumValue("Description")
    DESCRIPTION("Description"),
    @XmlEnumValue("ObjectId")
    OBJECT_ID("ObjectId"),
    @XmlEnumValue("Modified")
    MODIFIED("Modified"),
    @XmlEnumValue("PriorityName")
    PRIORITY_NAME("PriorityName"),
    @XmlEnumValue("Category")
    CATEGORY("Category"),
    @XmlEnumValue("CategoryName")
    CATEGORY_NAME("CategoryName"),
    @XmlEnumValue("StateName")
    STATE_NAME("StateName"),
    @XmlEnumValue("CameraId")
    CAMERA_ID("CameraId");
    private final String value;

    Target(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Target fromValue(String v) {
        for (Target c: Target.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
