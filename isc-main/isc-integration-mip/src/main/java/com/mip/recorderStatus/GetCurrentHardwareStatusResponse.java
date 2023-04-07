
package com.mip.recorderStatus;

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
 *         &amp;lt;element name="GetCurrentHardwareStatusResult" type="{http://video.net/2/XProtectCSRecorderStatus2}Status" minOccurs="0"/&amp;gt;
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
    "getCurrentHardwareStatusResult"
})
@XmlRootElement(name = "GetCurrentHardwareStatusResponse")
public class GetCurrentHardwareStatusResponse {

    @XmlElement(name = "GetCurrentHardwareStatusResult")
    protected Status getCurrentHardwareStatusResult;

    /**
     * Gets the value of the getCurrentHardwareStatusResult property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getGetCurrentHardwareStatusResult() {
        return getCurrentHardwareStatusResult;
    }

    /**
     * Sets the value of the getCurrentHardwareStatusResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setGetCurrentHardwareStatusResult(Status value) {
        this.getCurrentHardwareStatusResult = value;
    }

}
