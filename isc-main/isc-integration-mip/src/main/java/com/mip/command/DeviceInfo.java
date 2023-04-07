
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for DeviceInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="DeviceInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CoverageDepth" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CoverageDirection" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CoverageFieldOfView" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DeviceId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DeviceIndex" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="GisPoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HardwareId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Icon" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RecorderId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ShortName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Shortcut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceInfo", propOrder = {
    "coverageDepth",
    "coverageDirection",
    "coverageFieldOfView",
    "description",
    "deviceId",
    "deviceIndex",
    "gisPoint",
    "hardwareId",
    "icon",
    "name",
    "recorderId",
    "shortName",
    "shortcut"
})
@XmlSeeAlso({
    CameraInfo.class,
    InputInfo.class,
    MetadataDeviceInfo.class,
    MicrophoneInfo.class,
    OutputInfo.class,
    SpeakerInfo.class
})
public class DeviceInfo {

    @XmlElement(name = "CoverageDepth")
    protected Double coverageDepth;
    @XmlElement(name = "CoverageDirection")
    protected Double coverageDirection;
    @XmlElement(name = "CoverageFieldOfView")
    protected Double coverageFieldOfView;
    @XmlElement(name = "Description", nillable = true)
    protected String description;
    @XmlElement(name = "DeviceId")
    protected String deviceId;
    @XmlElement(name = "DeviceIndex")
    protected Integer deviceIndex;
    @XmlElement(name = "GisPoint", nillable = true)
    protected String gisPoint;
    @XmlElement(name = "HardwareId")
    protected String hardwareId;
    @XmlElement(name = "Icon")
    protected Integer icon;
    @XmlElement(name = "Name", nillable = true)
    protected String name;
    @XmlElement(name = "RecorderId")
    protected String recorderId;
    @XmlElement(name = "ShortName", nillable = true)
    protected String shortName;
    @XmlElement(name = "Shortcut", nillable = true)
    protected String shortcut;

    /**
     * Gets the value of the coverageDepth property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCoverageDepth() {
        return coverageDepth;
    }

    /**
     * Sets the value of the coverageDepth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCoverageDepth(Double value) {
        this.coverageDepth = value;
    }

    /**
     * Gets the value of the coverageDirection property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCoverageDirection() {
        return coverageDirection;
    }

    /**
     * Sets the value of the coverageDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCoverageDirection(Double value) {
        this.coverageDirection = value;
    }

    /**
     * Gets the value of the coverageFieldOfView property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCoverageFieldOfView() {
        return coverageFieldOfView;
    }

    /**
     * Sets the value of the coverageFieldOfView property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCoverageFieldOfView(Double value) {
        this.coverageFieldOfView = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the deviceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the value of the deviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceId(String value) {
        this.deviceId = value;
    }

    /**
     * Gets the value of the deviceIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDeviceIndex() {
        return deviceIndex;
    }

    /**
     * Sets the value of the deviceIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDeviceIndex(Integer value) {
        this.deviceIndex = value;
    }

    /**
     * Gets the value of the gisPoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGisPoint() {
        return gisPoint;
    }

    /**
     * Sets the value of the gisPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGisPoint(String value) {
        this.gisPoint = value;
    }

    /**
     * Gets the value of the hardwareId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHardwareId() {
        return hardwareId;
    }

    /**
     * Sets the value of the hardwareId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHardwareId(String value) {
        this.hardwareId = value;
    }

    /**
     * Gets the value of the icon property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIcon() {
        return icon;
    }

    /**
     * Sets the value of the icon property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIcon(Integer value) {
        this.icon = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the recorderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecorderId() {
        return recorderId;
    }

    /**
     * Sets the value of the recorderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecorderId(String value) {
        this.recorderId = value;
    }

    /**
     * Gets the value of the shortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Sets the value of the shortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortName(String value) {
        this.shortName = value;
    }

    /**
     * Gets the value of the shortcut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortcut() {
        return shortcut;
    }

    /**
     * Sets the value of the shortcut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortcut(String value) {
        this.shortcut = value;
    }

}
