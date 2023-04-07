
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for HemisphereInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="HemisphereInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CenterX" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CenterY" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RadiusX" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RadiusY" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HemisphereInfo", propOrder = {
    "centerX",
    "centerY",
    "radiusX",
    "radiusY"
})
public class HemisphereInfo {

    @XmlElement(name = "CenterX")
    protected Double centerX;
    @XmlElement(name = "CenterY")
    protected Double centerY;
    @XmlElement(name = "RadiusX")
    protected Double radiusX;
    @XmlElement(name = "RadiusY")
    protected Double radiusY;

    /**
     * Gets the value of the centerX property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCenterX() {
        return centerX;
    }

    /**
     * Sets the value of the centerX property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCenterX(Double value) {
        this.centerX = value;
    }

    /**
     * Gets the value of the centerY property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCenterY() {
        return centerY;
    }

    /**
     * Sets the value of the centerY property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCenterY(Double value) {
        this.centerY = value;
    }

    /**
     * Gets the value of the radiusX property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRadiusX() {
        return radiusX;
    }

    /**
     * Sets the value of the radiusX property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRadiusX(Double value) {
        this.radiusX = value;
    }

    /**
     * Gets the value of the radiusY property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRadiusY() {
        return radiusY;
    }

    /**
     * Sets the value of the radiusY property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRadiusY(Double value) {
        this.radiusY = value;
    }

}
