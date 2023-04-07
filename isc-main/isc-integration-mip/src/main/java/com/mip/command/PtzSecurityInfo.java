
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for PtzSecurityInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PtzSecurityInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="ManualControl" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PresetControl" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ReserveControl" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PtzSecurityInfo", propOrder = {
    "manualControl",
    "presetControl",
    "reserveControl"
})
public class PtzSecurityInfo {

    @XmlElement(name = "ManualControl")
    protected Boolean manualControl;
    @XmlElement(name = "PresetControl")
    protected Boolean presetControl;
    @XmlElement(name = "ReserveControl")
    protected Boolean reserveControl;

    /**
     * Gets the value of the manualControl property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isManualControl() {
        return manualControl;
    }

    /**
     * Sets the value of the manualControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setManualControl(Boolean value) {
        this.manualControl = value;
    }

    /**
     * Gets the value of the presetControl property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPresetControl() {
        return presetControl;
    }

    /**
     * Sets the value of the presetControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPresetControl(Boolean value) {
        this.presetControl = value;
    }

    /**
     * Gets the value of the reserveControl property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReserveControl() {
        return reserveControl;
    }

    /**
     * Sets the value of the reserveControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReserveControl(Boolean value) {
        this.reserveControl = value;
    }

}
