
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for RetentionOption complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RetentionOption"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="RetentionOptionType" type="{http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService}RetentionOptionType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RetentionUnits" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetentionOption", propOrder = {
    "retentionOptionType",
    "retentionUnits"
})
public class RetentionOption {

    @XmlElement(name = "RetentionOptionType")
    @XmlSchemaType(name = "string")
    protected RetentionOptionType retentionOptionType;
    @XmlElement(name = "RetentionUnits")
    protected Integer retentionUnits;

    /**
     * Gets the value of the retentionOptionType property.
     * 
     * @return
     *     possible object is
     *     {@link RetentionOptionType }
     *     
     */
    public RetentionOptionType getRetentionOptionType() {
        return retentionOptionType;
    }

    /**
     * Sets the value of the retentionOptionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetentionOptionType }
     *     
     */
    public void setRetentionOptionType(RetentionOptionType value) {
        this.retentionOptionType = value;
    }

    /**
     * Gets the value of the retentionUnits property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRetentionUnits() {
        return retentionUnits;
    }

    /**
     * Sets the value of the retentionUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRetentionUnits(Integer value) {
        this.retentionUnits = value;
    }

}
