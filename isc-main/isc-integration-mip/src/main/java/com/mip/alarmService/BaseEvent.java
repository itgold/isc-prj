
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for BaseEvent complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="BaseEvent"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="EventHeader" type="{urn:milestone-systems}EventHeader"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseEvent", namespace = "urn:milestone-systems", propOrder = {
    "eventHeader"
})
@XmlSeeAlso({
    AnalyticsEvent.class,
    EventData.class,
    AccessControlEvent.class
})
public class BaseEvent {

    @XmlElement(name = "EventHeader", required = true, nillable = true)
    protected EventHeader eventHeader;

    /**
     * Gets the value of the eventHeader property.
     * 
     * @return
     *     possible object is
     *     {@link EventHeader }
     *     
     */
    public EventHeader getEventHeader() {
        return eventHeader;
    }

    /**
     * Sets the value of the eventHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventHeader }
     *     
     */
    public void setEventHeader(EventHeader value) {
        this.eventHeader = value;
    }

}
