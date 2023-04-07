
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for CustomSettingInternalData complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CustomSettingInternalData"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CustomSettingDataXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DataVersion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomSettingInternalData", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", propOrder = {
    "customSettingDataXml",
    "dataVersion"
})
public class CustomSettingInternalData {

    @XmlElement(name = "CustomSettingDataXml", nillable = true)
    protected String customSettingDataXml;
    @XmlElement(name = "DataVersion")
    protected Integer dataVersion;

    /**
     * Gets the value of the customSettingDataXml property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomSettingDataXml() {
        return customSettingDataXml;
    }

    /**
     * Sets the value of the customSettingDataXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomSettingDataXml(String value) {
        this.customSettingDataXml = value;
    }

    /**
     * Gets the value of the dataVersion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDataVersion() {
        return dataVersion;
    }

    /**
     * Sets the value of the dataVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDataVersion(Integer value) {
        this.dataVersion = value;
    }

}
