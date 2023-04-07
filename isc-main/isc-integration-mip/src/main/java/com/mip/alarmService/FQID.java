
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for FQID complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="FQID"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="ServerId" type="{urn:milestone-systems}ServerId" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ParentId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ObjectId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ObjectIdString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="FolderType" type="{urn:milestone-systems}FolderType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Kind" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FQID", namespace = "urn:milestone-systems", propOrder = {
    "serverId",
    "parentId",
    "objectId",
    "objectIdString",
    "folderType",
    "kind"
})
public class FQID {

    @XmlElement(name = "ServerId", nillable = true)
    protected ServerId serverId;
    @XmlElement(name = "ParentId")
    protected String parentId;
    @XmlElement(name = "ObjectId")
    protected String objectId;
    @XmlElement(name = "ObjectIdString", nillable = true)
    protected String objectIdString;
    @XmlElement(name = "FolderType")
    protected String folderType;
    @XmlElement(name = "Kind")
    protected String kind;

    /**
     * Gets the value of the serverId property.
     * 
     * @return
     *     possible object is
     *     {@link ServerId }
     *     
     */
    public ServerId getServerId() {
        return serverId;
    }

    /**
     * Sets the value of the serverId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerId }
     *     
     */
    public void setServerId(ServerId value) {
        this.serverId = value;
    }

    /**
     * Gets the value of the parentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Sets the value of the parentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentId(String value) {
        this.parentId = value;
    }

    /**
     * Gets the value of the objectId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Sets the value of the objectId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectId(String value) {
        this.objectId = value;
    }

    /**
     * Gets the value of the objectIdString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectIdString() {
        return objectIdString;
    }

    /**
     * Sets the value of the objectIdString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectIdString(String value) {
        this.objectIdString = value;
    }

    /**
     * Gets the value of the folderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolderType() {
        return folderType;
    }

    /**
     * Sets the value of the folderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolderType(String value) {
        this.folderType = value;
    }

    /**
     * Gets the value of the kind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKind() {
        return kind;
    }

    /**
     * Sets the value of the kind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKind(String value) {
        this.kind = value;
    }

}
