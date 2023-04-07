
package com.mip.registry;

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
 *         &amp;lt;element name="GetServicesIncludingDisabledResult" type="{http://video.net/2/XProtectCSServiceRegistration}ArrayOfServiceInfo" minOccurs="0"/&amp;gt;
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
    "getServicesIncludingDisabledResult"
})
@XmlRootElement(name = "GetServicesIncludingDisabledResponse")
public class GetServicesIncludingDisabledResponse {

    @XmlElement(name = "GetServicesIncludingDisabledResult", nillable = true)
    protected ArrayOfServiceInfo getServicesIncludingDisabledResult;

    /**
     * Gets the value of the getServicesIncludingDisabledResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceInfo }
     *     
     */
    public ArrayOfServiceInfo getGetServicesIncludingDisabledResult() {
        return getServicesIncludingDisabledResult;
    }

    /**
     * Sets the value of the getServicesIncludingDisabledResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceInfo }
     *     
     */
    public void setGetServicesIncludingDisabledResult(ArrayOfServiceInfo value) {
        this.getServicesIncludingDisabledResult = value;
    }

}
