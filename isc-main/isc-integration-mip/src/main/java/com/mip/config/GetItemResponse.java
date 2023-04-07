
package com.mip.config;

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
 *         &amp;lt;element name="GetItemResult" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}ConfigurationItem" minOccurs="0"/&amp;gt;
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
    "getItemResult"
})
@XmlRootElement(name = "GetItemResponse")
public class GetItemResponse {

    @XmlElement(name = "GetItemResult", nillable = true)
    protected ConfigurationItem getItemResult;

    /**
     * Gets the value of the getItemResult property.
     * 
     * @return
     *     possible object is
     *     {@link ConfigurationItem }
     *     
     */
    public ConfigurationItem getGetItemResult() {
        return getItemResult;
    }

    /**
     * Sets the value of the getItemResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigurationItem }
     *     
     */
    public void setGetItemResult(ConfigurationItem value) {
        this.getItemResult = value;
    }

}
