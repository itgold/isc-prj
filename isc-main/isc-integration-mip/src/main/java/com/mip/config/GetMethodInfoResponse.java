
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
 *         &amp;lt;element name="GetMethodInfoResult" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}MethodInfo" minOccurs="0"/&amp;gt;
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
    "getMethodInfoResult"
})
@XmlRootElement(name = "GetMethodInfoResponse")
public class GetMethodInfoResponse {

    @XmlElement(name = "GetMethodInfoResult", nillable = true)
    protected MethodInfo getMethodInfoResult;

    /**
     * Gets the value of the getMethodInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link MethodInfo }
     *     
     */
    public MethodInfo getGetMethodInfoResult() {
        return getMethodInfoResult;
    }

    /**
     * Sets the value of the getMethodInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link MethodInfo }
     *     
     */
    public void setGetMethodInfoResult(MethodInfo value) {
        this.getMethodInfoResult = value;
    }

}
