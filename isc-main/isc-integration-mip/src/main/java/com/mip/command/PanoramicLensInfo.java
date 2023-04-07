
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for PanoramicLensInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PanoramicLensInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CameraMount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ImmerVision" type="{http://video.net/2/XProtectCSServerCommand}ImmerVisionInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PanoramicLensEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PanoramicLensType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PanoramicLensInfo", propOrder = {
    "cameraMount",
    "immerVision",
    "panoramicLensEnabled",
    "panoramicLensType"
})
public class PanoramicLensInfo {

    @XmlElement(name = "CameraMount", nillable = true)
    protected String cameraMount;
    @XmlElement(name = "ImmerVision", nillable = true)
    protected ImmerVisionInfo immerVision;
    @XmlElement(name = "PanoramicLensEnabled")
    protected Boolean panoramicLensEnabled;
    @XmlElement(name = "PanoramicLensType", nillable = true)
    protected String panoramicLensType;

    /**
     * Gets the value of the cameraMount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCameraMount() {
        return cameraMount;
    }

    /**
     * Sets the value of the cameraMount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCameraMount(String value) {
        this.cameraMount = value;
    }

    /**
     * Gets the value of the immerVision property.
     * 
     * @return
     *     possible object is
     *     {@link ImmerVisionInfo }
     *     
     */
    public ImmerVisionInfo getImmerVision() {
        return immerVision;
    }

    /**
     * Sets the value of the immerVision property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImmerVisionInfo }
     *     
     */
    public void setImmerVision(ImmerVisionInfo value) {
        this.immerVision = value;
    }

    /**
     * Gets the value of the panoramicLensEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPanoramicLensEnabled() {
        return panoramicLensEnabled;
    }

    /**
     * Sets the value of the panoramicLensEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPanoramicLensEnabled(Boolean value) {
        this.panoramicLensEnabled = value;
    }

    /**
     * Gets the value of the panoramicLensType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPanoramicLensType() {
        return panoramicLensType;
    }

    /**
     * Sets the value of the panoramicLensType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPanoramicLensType(String value) {
        this.panoramicLensType = value;
    }

}
