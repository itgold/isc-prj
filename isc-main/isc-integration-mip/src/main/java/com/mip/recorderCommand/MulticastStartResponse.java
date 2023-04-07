
package com.mip.recorderCommand;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for anonymous complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="MulticastStartResult" type="{http://video.net/2/XProtectCSRecorderCommand}ArrayOfMulticastInfo" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "multicastStartResult"
})
@XmlRootElement(name = "MulticastStartResponse")
public class MulticastStartResponse {

    @XmlElement(name = "MulticastStartResult")
    protected ArrayOfMulticastInfo multicastStartResult;

    /**
     * Gets the value of the multicastStartResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMulticastInfo }
     *     
     */
    public ArrayOfMulticastInfo getMulticastStartResult() {
        return multicastStartResult;
    }

    /**
     * Sets the value of the multicastStartResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMulticastInfo }
     *     
     */
    public void setMulticastStartResult(ArrayOfMulticastInfo value) {
        this.multicastStartResult = value;
    }

}
