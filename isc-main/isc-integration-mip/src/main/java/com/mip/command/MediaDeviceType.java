
package com.mip.command;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for MediaDeviceType.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="MediaDeviceType"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Camera"/&amp;gt;
 *     &amp;lt;enumeration value="Microphone"/&amp;gt;
 *     &amp;lt;enumeration value="Speaker"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "MediaDeviceType")
@XmlEnum
public enum MediaDeviceType {

    @XmlEnumValue("Camera")
    CAMERA("Camera"),
    @XmlEnumValue("Microphone")
    MICROPHONE("Microphone"),
    @XmlEnumValue("Speaker")
    SPEAKER("Speaker");
    private final String value;

    MediaDeviceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MediaDeviceType fromValue(String v) {
        for (MediaDeviceType c: MediaDeviceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
