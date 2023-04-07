
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for PtzInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PtzInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Capabilities" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfCapabilityInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EditPreset" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IsCenterOnPositionInViewSupported" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IsPtzCenterAndZoomToRectangleSupported" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IsPtzDiagonalSupported" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IsPtzHomeSupported" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Presets" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfPresetInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PtzEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PtzInfo", propOrder = {
    "capabilities",
    "editPreset",
    "isCenterOnPositionInViewSupported",
    "isPtzCenterAndZoomToRectangleSupported",
    "isPtzDiagonalSupported",
    "isPtzHomeSupported",
    "presets",
    "ptzEnabled"
})
public class PtzInfo {

    @XmlElement(name = "Capabilities", nillable = true)
    protected ArrayOfCapabilityInfo capabilities;
    @XmlElement(name = "EditPreset")
    protected Boolean editPreset;
    @XmlElement(name = "IsCenterOnPositionInViewSupported")
    protected Boolean isCenterOnPositionInViewSupported;
    @XmlElement(name = "IsPtzCenterAndZoomToRectangleSupported")
    protected Boolean isPtzCenterAndZoomToRectangleSupported;
    @XmlElement(name = "IsPtzDiagonalSupported")
    protected Boolean isPtzDiagonalSupported;
    @XmlElement(name = "IsPtzHomeSupported")
    protected Boolean isPtzHomeSupported;
    @XmlElement(name = "Presets", nillable = true)
    protected ArrayOfPresetInfo presets;
    @XmlElement(name = "PtzEnabled")
    protected Boolean ptzEnabled;

    /**
     * Gets the value of the capabilities property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCapabilityInfo }
     *     
     */
    public ArrayOfCapabilityInfo getCapabilities() {
        return capabilities;
    }

    /**
     * Sets the value of the capabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCapabilityInfo }
     *     
     */
    public void setCapabilities(ArrayOfCapabilityInfo value) {
        this.capabilities = value;
    }

    /**
     * Gets the value of the editPreset property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEditPreset() {
        return editPreset;
    }

    /**
     * Sets the value of the editPreset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEditPreset(Boolean value) {
        this.editPreset = value;
    }

    /**
     * Gets the value of the isCenterOnPositionInViewSupported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCenterOnPositionInViewSupported() {
        return isCenterOnPositionInViewSupported;
    }

    /**
     * Sets the value of the isCenterOnPositionInViewSupported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCenterOnPositionInViewSupported(Boolean value) {
        this.isCenterOnPositionInViewSupported = value;
    }

    /**
     * Gets the value of the isPtzCenterAndZoomToRectangleSupported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsPtzCenterAndZoomToRectangleSupported() {
        return isPtzCenterAndZoomToRectangleSupported;
    }

    /**
     * Sets the value of the isPtzCenterAndZoomToRectangleSupported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPtzCenterAndZoomToRectangleSupported(Boolean value) {
        this.isPtzCenterAndZoomToRectangleSupported = value;
    }

    /**
     * Gets the value of the isPtzDiagonalSupported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsPtzDiagonalSupported() {
        return isPtzDiagonalSupported;
    }

    /**
     * Sets the value of the isPtzDiagonalSupported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPtzDiagonalSupported(Boolean value) {
        this.isPtzDiagonalSupported = value;
    }

    /**
     * Gets the value of the isPtzHomeSupported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsPtzHomeSupported() {
        return isPtzHomeSupported;
    }

    /**
     * Sets the value of the isPtzHomeSupported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPtzHomeSupported(Boolean value) {
        this.isPtzHomeSupported = value;
    }

    /**
     * Gets the value of the presets property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPresetInfo }
     *     
     */
    public ArrayOfPresetInfo getPresets() {
        return presets;
    }

    /**
     * Sets the value of the presets property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPresetInfo }
     *     
     */
    public void setPresets(ArrayOfPresetInfo value) {
        this.presets = value;
    }

    /**
     * Gets the value of the ptzEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPtzEnabled() {
        return ptzEnabled;
    }

    /**
     * Sets the value of the ptzEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPtzEnabled(Boolean value) {
        this.ptzEnabled = value;
    }

}
