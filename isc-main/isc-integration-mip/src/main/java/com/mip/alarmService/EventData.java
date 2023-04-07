
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for EventData complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="EventData"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{urn:milestone-systems}BaseEvent"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="EventSequence" type="{urn:milestone-systems}EventSequence" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventData", namespace = "urn:milestone-systems", propOrder = {
    "eventSequence"
})
public class EventData
    extends BaseEvent
{

    @XmlElement(name = "EventSequence", nillable = true)
    protected EventSequence eventSequence;

    /**
     * Gets the value of the eventSequence property.
     * 
     * @return
     *     possible object is
     *     {@link EventSequence }
     *     
     */
    public EventSequence getEventSequence() {
        return eventSequence;
    }

    /**
     * Sets the value of the eventSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventSequence }
     *     
     */
    public void setEventSequence(EventSequence value) {
        this.eventSequence = value;
    }

}
