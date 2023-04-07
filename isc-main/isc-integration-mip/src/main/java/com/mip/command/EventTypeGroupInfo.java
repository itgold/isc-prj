
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for EventTypeGroupInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="EventTypeGroupInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}GroupInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="EventTypeGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfEventTypeGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EventTypes" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventTypeGroupInfo", propOrder = {
    "eventTypeGroups",
    "eventTypes"
})
public class EventTypeGroupInfo
    extends GroupInfo
{

    @XmlElement(name = "EventTypeGroups", nillable = true)
    protected ArrayOfEventTypeGroupInfo eventTypeGroups;
    @XmlElement(name = "EventTypes", nillable = true)
    protected ArrayOfguid eventTypes;

    /**
     * Gets the value of the eventTypeGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEventTypeGroupInfo }
     *     
     */
    public ArrayOfEventTypeGroupInfo getEventTypeGroups() {
        return eventTypeGroups;
    }

    /**
     * Sets the value of the eventTypeGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEventTypeGroupInfo }
     *     
     */
    public void setEventTypeGroups(ArrayOfEventTypeGroupInfo value) {
        this.eventTypeGroups = value;
    }

    /**
     * Gets the value of the eventTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getEventTypes() {
        return eventTypes;
    }

    /**
     * Sets the value of the eventTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setEventTypes(ArrayOfguid value) {
        this.eventTypes = value;
    }

}
