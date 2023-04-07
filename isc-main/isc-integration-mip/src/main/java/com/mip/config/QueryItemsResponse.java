
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
 *         &amp;lt;element name="QueryItemsResult" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}ArrayOfConfigurationItem" minOccurs="0"/&amp;gt;
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
    "queryItemsResult"
})
@XmlRootElement(name = "QueryItemsResponse")
public class QueryItemsResponse {

    @XmlElement(name = "QueryItemsResult", nillable = true)
    protected ArrayOfConfigurationItem queryItemsResult;

    /**
     * Gets the value of the queryItemsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfConfigurationItem }
     *     
     */
    public ArrayOfConfigurationItem getQueryItemsResult() {
        return queryItemsResult;
    }

    /**
     * Sets the value of the queryItemsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfConfigurationItem }
     *     
     */
    public void setQueryItemsResult(ArrayOfConfigurationItem value) {
        this.queryItemsResult = value;
    }

}
