
package com.mip.recorderStatus;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for StorageStatus complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="StorageStatus"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="StorageId" type="{http://microsoft.com/wsdl/types/}guid"/&amp;gt;
 *         &amp;lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Available" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *         &amp;lt;element name="UsedSpaceInBytes" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&amp;gt;
 *         &amp;lt;element name="FreeSpaceInBytes" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StorageStatus", propOrder = {
    "storageId",
    "name",
    "path",
    "available",
    "usedSpaceInBytes",
    "freeSpaceInBytes"
})
public class StorageStatus {

    @XmlElement(name = "StorageId", required = true)
    protected String storageId;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Path")
    protected String path;
    @XmlElement(name = "Available")
    protected boolean available;
    @XmlElement(name = "UsedSpaceInBytes", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger usedSpaceInBytes;
    @XmlElement(name = "FreeSpaceInBytes", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger freeSpaceInBytes;

    /**
     * Gets the value of the storageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorageId() {
        return storageId;
    }

    /**
     * Sets the value of the storageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorageId(String value) {
        this.storageId = value;
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
     * Gets the value of the available property.
     * 
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the value of the available property.
     * 
     */
    public void setAvailable(boolean value) {
        this.available = value;
    }

    /**
     * Gets the value of the usedSpaceInBytes property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUsedSpaceInBytes() {
        return usedSpaceInBytes;
    }

    /**
     * Sets the value of the usedSpaceInBytes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUsedSpaceInBytes(BigInteger value) {
        this.usedSpaceInBytes = value;
    }

    /**
     * Gets the value of the freeSpaceInBytes property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFreeSpaceInBytes() {
        return freeSpaceInBytes;
    }

    /**
     * Sets the value of the freeSpaceInBytes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFreeSpaceInBytes(BigInteger value) {
        this.freeSpaceInBytes = value;
    }

}
