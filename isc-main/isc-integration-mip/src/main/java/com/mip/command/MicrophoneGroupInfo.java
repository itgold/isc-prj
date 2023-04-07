
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for MicrophoneGroupInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MicrophoneGroupInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}GroupInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="MicrophoneGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfMicrophoneGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Microphones" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MicrophoneGroupInfo", propOrder = {
    "microphoneGroups",
    "microphones"
})
public class MicrophoneGroupInfo
    extends GroupInfo
{

    @XmlElement(name = "MicrophoneGroups", nillable = true)
    protected ArrayOfMicrophoneGroupInfo microphoneGroups;
    @XmlElement(name = "Microphones", nillable = true)
    protected ArrayOfguid microphones;

    /**
     * Gets the value of the microphoneGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMicrophoneGroupInfo }
     *     
     */
    public ArrayOfMicrophoneGroupInfo getMicrophoneGroups() {
        return microphoneGroups;
    }

    /**
     * Sets the value of the microphoneGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMicrophoneGroupInfo }
     *     
     */
    public void setMicrophoneGroups(ArrayOfMicrophoneGroupInfo value) {
        this.microphoneGroups = value;
    }

    /**
     * Gets the value of the microphones property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getMicrophones() {
        return microphones;
    }

    /**
     * Sets the value of the microphones property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setMicrophones(ArrayOfguid value) {
        this.microphones = value;
    }

}
