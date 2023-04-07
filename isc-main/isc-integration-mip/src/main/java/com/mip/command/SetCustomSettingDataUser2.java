
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
 *         &amp;lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="customSettingId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="customSettingData" type="{http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server}CustomSettingInternalData" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="forceOverride" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
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
    "token",
    "customSettingId",
    "customSettingData",
    "forceOverride"
})
@XmlRootElement(name = "SetCustomSettingDataUser2")
public class SetCustomSettingDataUser2 {

    @XmlElement(nillable = true)
    protected String token;
    protected String customSettingId;
    @XmlElement(nillable = true)
    protected CustomSettingInternalData customSettingData;
    protected Boolean forceOverride;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the customSettingId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomSettingId() {
        return customSettingId;
    }

    /**
     * Sets the value of the customSettingId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomSettingId(String value) {
        this.customSettingId = value;
    }

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
     * Gets the value of the forceOverride property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isForceOverride() {
        return forceOverride;
    }

    /**
     * Sets the value of the forceOverride property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setForceOverride(Boolean value) {
        this.forceOverride = value;
    }

}
