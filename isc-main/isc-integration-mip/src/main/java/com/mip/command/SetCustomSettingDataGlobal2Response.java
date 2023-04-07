
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &amp;lt;element name="SetCustomSettingDataGlobal2Result" type="{http://video.net/2/XProtectCSServerCommand}SetCustomSettingResultType" minOccurs="0"/&amp;gt;
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
    "setCustomSettingDataGlobal2Result"
})
@XmlRootElement(name = "SetCustomSettingDataGlobal2Response")
public class SetCustomSettingDataGlobal2Response {

    @XmlElement(name = "SetCustomSettingDataGlobal2Result")
    @XmlSchemaType(name = "string")
    protected SetCustomSettingResultType setCustomSettingDataGlobal2Result;

    /**
     * Gets the value of the setCustomSettingDataGlobal2Result property.
     * 
     * @return
     *     possible object is
     *     {@link SetCustomSettingResultType }
     *     
     */
    public SetCustomSettingResultType getSetCustomSettingDataGlobal2Result() {
        return setCustomSettingDataGlobal2Result;
    }

    /**
     * Sets the value of the setCustomSettingDataGlobal2Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link SetCustomSettingResultType }
     *     
     */
    public void setSetCustomSettingDataGlobal2Result(SetCustomSettingResultType value) {
        this.setCustomSettingDataGlobal2Result = value;
    }

}
