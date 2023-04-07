
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for CustomSettingInternal complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CustomSettingInternal"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CustomSettingData" type="{http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server}CustomSettingInternalData" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CustomSettingInfo" type="{http://video.net/2/XProtectCSServerCommand}CustomSettingInfo" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomSettingInternal", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", propOrder = {
    "customSettingData",
    "customSettingInfo"
})
public class CustomSettingInternal {

    @XmlElement(name = "CustomSettingData", nillable = true)
    protected CustomSettingInternalData customSettingData;
    @XmlElement(name = "CustomSettingInfo", nillable = true)
    protected CustomSettingInfo customSettingInfo;

    /**
     * Gets the value of the customSettingData property.
     * 
     * @return
     *     possible object is
     *     {@link CustomSettingInternalData }
     *     
     */
    public CustomSettingInternalData getCustomSettingData() {
        return customSettingData;
    }

    /**
     * Sets the value of the customSettingData property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomSettingInternalData }
     *     
     */
    public void setCustomSettingData(CustomSettingInternalData value) {
        this.customSettingData = value;
    }

    /**
     * Gets the value of the customSettingInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustomSettingInfo }
     *     
     */
    public CustomSettingInfo getCustomSettingInfo() {
        return customSettingInfo;
    }

    /**
     * Sets the value of the customSettingInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomSettingInfo }
     *     
     */
    public void setCustomSettingInfo(CustomSettingInfo value) {
        this.customSettingInfo = value;
    }

}
