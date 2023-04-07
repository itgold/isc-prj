
package com.mip.command;

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
 *         &amp;lt;element name="GetConfigurationHardwareResult" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfHardwareInfo" minOccurs="0"/&amp;gt;
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
    "getConfigurationHardwareResult"
})
@XmlRootElement(name = "GetConfigurationHardwareResponse")
public class GetConfigurationHardwareResponse {

    @XmlElement(name = "GetConfigurationHardwareResult", nillable = true)
    protected ArrayOfHardwareInfo getConfigurationHardwareResult;

    /**
     * Gets the value of the getConfigurationHardwareResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfHardwareInfo }
     *     
     */
    public ArrayOfHardwareInfo getGetConfigurationHardwareResult() {
        return getConfigurationHardwareResult;
    }

    /**
     * Sets the value of the getConfigurationHardwareResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfHardwareInfo }
     *     
     */
    public void setGetConfigurationHardwareResult(ArrayOfHardwareInfo value) {
        this.getConfigurationHardwareResult = value;
    }

}
