
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
 *         &amp;lt;element name="GetCustomSettingDataUser2Result" type="{http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server}CustomSettingInternal" minOccurs="0"/&amp;gt;
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
    "getCustomSettingDataUser2Result"
})
@XmlRootElement(name = "GetCustomSettingDataUser2Response")
public class GetCustomSettingDataUser2Response {

    @XmlElement(name = "GetCustomSettingDataUser2Result", nillable = true)
    protected CustomSettingInternal getCustomSettingDataUser2Result;

    /**
     * Gets the value of the getCustomSettingDataUser2Result property.
     * 
     * @return
     *     possible object is
     *     {@link CustomSettingInternal }
     *     
     */
    public CustomSettingInternal getGetCustomSettingDataUser2Result() {
        return getCustomSettingDataUser2Result;
    }

    /**
     * Sets the value of the getCustomSettingDataUser2Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomSettingInternal }
     *     
     */
    public void setGetCustomSettingDataUser2Result(CustomSettingInternal value) {
        this.getCustomSettingDataUser2Result = value;
    }

}
