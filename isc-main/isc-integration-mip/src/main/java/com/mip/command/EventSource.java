
package com.mip.command;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for EventSource.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="EventSource"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Undefined"/&amp;gt;
 *     &amp;lt;enumeration value="Device"/&amp;gt;
 *     &amp;lt;enumeration value="External"/&amp;gt;
 *     &amp;lt;enumeration value="Hardware"/&amp;gt;
 *     &amp;lt;enumeration value="Recorder"/&amp;gt;
 *     &amp;lt;enumeration value="Server"/&amp;gt;
 *     &amp;lt;enumeration value="System"/&amp;gt;
 *     &amp;lt;enumeration value="Timer"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "EventSource")
@XmlEnum
public enum EventSource {

    @XmlEnumValue("Undefined")
    UNDEFINED("Undefined"),
    @XmlEnumValue("Device")
    DEVICE("Device"),
    @XmlEnumValue("External")
    EXTERNAL("External"),
    @XmlEnumValue("Hardware")
    HARDWARE("Hardware"),
    @XmlEnumValue("Recorder")
    RECORDER("Recorder"),
    @XmlEnumValue("Server")
    SERVER("Server"),
    @XmlEnumValue("System")
    SYSTEM("System"),
    @XmlEnumValue("Timer")
    TIMER("Timer");
    private final String value;

    EventSource(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EventSource fromValue(String v) {
        for (EventSource c: EventSource.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
