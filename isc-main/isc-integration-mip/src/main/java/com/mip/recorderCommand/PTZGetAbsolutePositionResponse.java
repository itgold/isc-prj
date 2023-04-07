
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
 *         &amp;lt;element name="PTZGetAbsolutePositionResult" type="{http://video.net/2/XProtectCSRecorderCommand}PTZArgs" minOccurs="0"/&amp;gt;
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
    "ptzGetAbsolutePositionResult"
})
@XmlRootElement(name = "PTZGetAbsolutePositionResponse")
public class PTZGetAbsolutePositionResponse {

    @XmlElement(name = "PTZGetAbsolutePositionResult")
    protected PTZArgs ptzGetAbsolutePositionResult;

    /**
     * Gets the value of the ptzGetAbsolutePositionResult property.
     * 
     * @return
     *     possible object is
     *     {@link PTZArgs }
     *     
     */
    public PTZArgs getPTZGetAbsolutePositionResult() {
        return ptzGetAbsolutePositionResult;
    }

    /**
     * Sets the value of the ptzGetAbsolutePositionResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link PTZArgs }
     *     
     */
    public void setPTZGetAbsolutePositionResult(PTZArgs value) {
        this.ptzGetAbsolutePositionResult = value;
    }

}
