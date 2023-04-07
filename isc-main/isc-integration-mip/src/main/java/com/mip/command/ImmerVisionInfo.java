
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ImmerVisionInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ImmerVisionInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="LensProfileData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LensProfileFieldOfView" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LensProfileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LensProfileRpl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImmerVisionInfo", propOrder = {
    "lensProfileData",
    "lensProfileFieldOfView",
    "lensProfileName",
    "lensProfileRpl"
})
public class ImmerVisionInfo {

    @XmlElement(name = "LensProfileData", nillable = true)
    protected String lensProfileData;
    @XmlElement(name = "LensProfileFieldOfView")
    protected Double lensProfileFieldOfView;
    @XmlElement(name = "LensProfileName", nillable = true)
    protected String lensProfileName;
    @XmlElement(name = "LensProfileRpl", nillable = true)
    protected String lensProfileRpl;

    /**
     * Gets the value of the lensProfileData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLensProfileData() {
        return lensProfileData;
    }

    /**
     * Sets the value of the lensProfileData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLensProfileData(String value) {
        this.lensProfileData = value;
    }

    /**
     * Gets the value of the lensProfileFieldOfView property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLensProfileFieldOfView() {
        return lensProfileFieldOfView;
    }

    /**
     * Sets the value of the lensProfileFieldOfView property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLensProfileFieldOfView(Double value) {
        this.lensProfileFieldOfView = value;
    }

    /**
     * Gets the value of the lensProfileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLensProfileName() {
        return lensProfileName;
    }

    /**
     * Sets the value of the lensProfileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLensProfileName(String value) {
        this.lensProfileName = value;
    }

    /**
     * Gets the value of the lensProfileRpl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLensProfileRpl() {
        return lensProfileRpl;
    }

    /**
     * Sets the value of the lensProfileRpl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLensProfileRpl(String value) {
        this.lensProfileRpl = value;
    }

}
