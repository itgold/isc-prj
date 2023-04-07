
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
 *         &amp;lt;element name="GetMethodInfosResult" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}ArrayOfMethodInfo" minOccurs="0"/&amp;gt;
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
    "getMethodInfosResult"
})
@XmlRootElement(name = "GetMethodInfosResponse")
public class GetMethodInfosResponse {

    @XmlElement(name = "GetMethodInfosResult", nillable = true)
    protected ArrayOfMethodInfo getMethodInfosResult;

    /**
     * Gets the value of the getMethodInfosResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMethodInfo }
     *     
     */
    public ArrayOfMethodInfo getGetMethodInfosResult() {
        return getMethodInfosResult;
    }

    /**
     * Sets the value of the getMethodInfosResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMethodInfo }
     *     
     */
    public void setGetMethodInfosResult(ArrayOfMethodInfo value) {
        this.getMethodInfosResult = value;
    }

}
