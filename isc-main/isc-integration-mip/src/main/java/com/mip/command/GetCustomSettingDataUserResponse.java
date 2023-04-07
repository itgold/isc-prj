
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
 *         &amp;lt;element name="GetCustomSettingDataUserResult" type="{http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server}CustomSettingInternal" minOccurs="0"/&amp;gt;
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
    "getCustomSettingDataUserResult"
})
@XmlRootElement(name = "GetCustomSettingDataUserResponse")
public class GetCustomSettingDataUserResponse {

    @XmlElement(name = "GetCustomSettingDataUserResult", nillable = true)
    protected CustomSettingInternal getCustomSettingDataUserResult;

    /**
     * Gets the value of the getCustomSettingDataUserResult property.
     * 
     * @return
     *     possible object is
     *     {@link CustomSettingInternal }
     *     
     */
    public CustomSettingInternal getGetCustomSettingDataUserResult() {
        return getCustomSettingDataUserResult;
    }

    /**
     * Sets the value of the getCustomSettingDataUserResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomSettingInternal }
     *     
     */
    public void setGetCustomSettingDataUserResult(CustomSettingInternal value) {
        this.getCustomSettingDataUserResult = value;
    }

}
