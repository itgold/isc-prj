
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SpeakerGroupInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SpeakerGroupInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}GroupInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="SpeakerGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfSpeakerGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Speakers" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpeakerGroupInfo", propOrder = {
    "speakerGroups",
    "speakers"
})
public class SpeakerGroupInfo
    extends GroupInfo
{

    @XmlElement(name = "SpeakerGroups", nillable = true)
    protected ArrayOfSpeakerGroupInfo speakerGroups;
    @XmlElement(name = "Speakers", nillable = true)
    protected ArrayOfguid speakers;

    /**
     * Gets the value of the speakerGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSpeakerGroupInfo }
     *     
     */
    public ArrayOfSpeakerGroupInfo getSpeakerGroups() {
        return speakerGroups;
    }

    /**
     * Sets the value of the speakerGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSpeakerGroupInfo }
     *     
     */
    public void setSpeakerGroups(ArrayOfSpeakerGroupInfo value) {
        this.speakerGroups = value;
    }

    /**
     * Gets the value of the speakers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getSpeakers() {
        return speakers;
    }

    /**
     * Sets the value of the speakers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setSpeakers(ArrayOfguid value) {
        this.speakers = value;
    }

}
