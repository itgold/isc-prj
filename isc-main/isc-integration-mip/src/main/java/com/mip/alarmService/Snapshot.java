
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for Snapshot complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="Snapshot"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="TimeOffset" type="{http://www.w3.org/2001/XMLSchema}int"/&amp;gt;
 *         &amp;lt;element name="Width" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Height" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HasOverlay" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SizeInBytes" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Image" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="FQID" type="{urn:milestone-systems}FQID" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Snapshot", namespace = "urn:milestone-systems", propOrder = {
    "timeOffset",
    "width",
    "height",
    "hasOverlay",
    "sizeInBytes",
    "image",
    "path",
    "fqid"
})
public class Snapshot {

    @XmlElement(name = "TimeOffset")
    protected int timeOffset;
    @XmlElement(name = "Width")
    @XmlSchemaType(name = "unsignedInt")
    protected Long width;
    @XmlElement(name = "Height")
    @XmlSchemaType(name = "unsignedInt")
    protected Long height;
    @XmlElement(name = "HasOverlay")
    protected Boolean hasOverlay;
    @XmlElement(name = "SizeInBytes")
    @XmlSchemaType(name = "unsignedInt")
    protected Long sizeInBytes;
    @XmlElement(name = "Image", nillable = true)
    protected byte[] image;
    @XmlElement(name = "Path", nillable = true)
    protected String path;
    @XmlElement(name = "FQID", nillable = true)
    protected FQID fqid;

    /**
     * Gets the value of the timeOffset property.
     * 
     */
    public int getTimeOffset() {
        return timeOffset;
    }

    /**
     * Sets the value of the timeOffset property.
     * 
     */
    public void setTimeOffset(int value) {
        this.timeOffset = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setWidth(Long value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHeight(Long value) {
        this.height = value;
    }

    /**
     * Gets the value of the hasOverlay property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasOverlay() {
        return hasOverlay;
    }

    /**
     * Sets the value of the hasOverlay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasOverlay(Boolean value) {
        this.hasOverlay = value;
    }

    /**
     * Gets the value of the sizeInBytes property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSizeInBytes() {
        return sizeInBytes;
    }

    /**
     * Sets the value of the sizeInBytes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSizeInBytes(Long value) {
        this.sizeInBytes = value;
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImage(byte[] value) {
        this.image = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the fqid property.
     * 
     * @return
     *     possible object is
     *     {@link FQID }
     *     
     */
    public FQID getFQID() {
        return fqid;
    }

    /**
     * Sets the value of the fqid property.
     * 
     * @param value
     *     allowed object is
     *     {@link FQID }
     *     
     */
    public void setFQID(FQID value) {
        this.fqid = value;
    }

}
