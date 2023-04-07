
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RetentionSettingInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RetentionSettingInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="RetentionOptions" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfRetentionOption" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetentionSettingInfo", propOrder = {
    "retentionOptions"
})
public class RetentionSettingInfo {

    @XmlElement(name = "RetentionOptions", nillable = true)
    protected ArrayOfRetentionOption retentionOptions;

    /**
     * Gets the value of the retentionOptions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRetentionOption }
     *     
     */
    public ArrayOfRetentionOption getRetentionOptions() {
        return retentionOptions;
    }

    /**
     * Sets the value of the retentionOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRetentionOption }
     *     
     */
    public void setRetentionOptions(ArrayOfRetentionOption value) {
        this.retentionOptions = value;
    }

}
