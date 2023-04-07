
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for AccessControlEvent complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="AccessControlEvent"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{urn:milestone-systems}BaseEvent"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="AccessControlSystemEventId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AccessControlSystemId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AccessControlEventTypeId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AccessControlEventSourceTypeId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AccessControlEventSourceId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RelatedAccessControlCredentialHolderIds" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RelatedAccessControlElementIds" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EventCategories" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RelatedSurveillanceItems" type="{urn:milestone-systems}ArrayOfFQID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Properties" type="{urn:milestone-systems}ArrayOfProperty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccessControlEvent", namespace = "urn:milestone-systems", propOrder = {
    "accessControlSystemEventId",
    "accessControlSystemId",
    "accessControlEventTypeId",
    "accessControlEventSourceTypeId",
    "accessControlEventSourceId",
    "relatedAccessControlCredentialHolderIds",
    "relatedAccessControlElementIds",
    "eventCategories",
    "relatedSurveillanceItems",
    "properties",
    "reason"
})
public class AccessControlEvent
    extends BaseEvent
{

    @XmlElement(name = "AccessControlSystemEventId", nillable = true)
    protected String accessControlSystemEventId;
    @XmlElement(name = "AccessControlSystemId")
    protected String accessControlSystemId;
    @XmlElement(name = "AccessControlEventTypeId")
    protected String accessControlEventTypeId;
    @XmlElement(name = "AccessControlEventSourceTypeId")
    protected String accessControlEventSourceTypeId;
    @XmlElement(name = "AccessControlEventSourceId")
    protected String accessControlEventSourceId;
    @XmlElement(name = "RelatedAccessControlCredentialHolderIds", nillable = true)
    protected ArrayOfstring relatedAccessControlCredentialHolderIds;
    @XmlElement(name = "RelatedAccessControlElementIds", nillable = true)
    protected ArrayOfguid relatedAccessControlElementIds;
    @XmlElement(name = "EventCategories", nillable = true)
    protected ArrayOfguid eventCategories;
    @XmlElement(name = "RelatedSurveillanceItems", nillable = true)
    protected ArrayOfFQID relatedSurveillanceItems;
    @XmlElement(name = "Properties", nillable = true)
    protected ArrayOfProperty properties;
    @XmlElement(name = "Reason", nillable = true)
    protected String reason;

    /**
     * Gets the value of the accessControlSystemEventId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessControlSystemEventId() {
        return accessControlSystemEventId;
    }

    /**
     * Sets the value of the accessControlSystemEventId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessControlSystemEventId(String value) {
        this.accessControlSystemEventId = value;
    }

    /**
     * Gets the value of the accessControlSystemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessControlSystemId() {
        return accessControlSystemId;
    }

    /**
     * Sets the value of the accessControlSystemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessControlSystemId(String value) {
        this.accessControlSystemId = value;
    }

    /**
     * Gets the value of the accessControlEventTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessControlEventTypeId() {
        return accessControlEventTypeId;
    }

    /**
     * Sets the value of the accessControlEventTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessControlEventTypeId(String value) {
        this.accessControlEventTypeId = value;
    }

    /**
     * Gets the value of the accessControlEventSourceTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessControlEventSourceTypeId() {
        return accessControlEventSourceTypeId;
    }

    /**
     * Sets the value of the accessControlEventSourceTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessControlEventSourceTypeId(String value) {
        this.accessControlEventSourceTypeId = value;
    }

    /**
     * Gets the value of the accessControlEventSourceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessControlEventSourceId() {
        return accessControlEventSourceId;
    }

    /**
     * Sets the value of the accessControlEventSourceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessControlEventSourceId(String value) {
        this.accessControlEventSourceId = value;
    }

    /**
     * Gets the value of the relatedAccessControlCredentialHolderIds property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfstring }
     *     
     */
    public ArrayOfstring getRelatedAccessControlCredentialHolderIds() {
        return relatedAccessControlCredentialHolderIds;
    }

    /**
     * Sets the value of the relatedAccessControlCredentialHolderIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfstring }
     *     
     */
    public void setRelatedAccessControlCredentialHolderIds(ArrayOfstring value) {
        this.relatedAccessControlCredentialHolderIds = value;
    }

    /**
     * Gets the value of the relatedAccessControlElementIds property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getRelatedAccessControlElementIds() {
        return relatedAccessControlElementIds;
    }

    /**
     * Sets the value of the relatedAccessControlElementIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setRelatedAccessControlElementIds(ArrayOfguid value) {
        this.relatedAccessControlElementIds = value;
    }

    /**
     * Gets the value of the eventCategories property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getEventCategories() {
        return eventCategories;
    }

    /**
     * Sets the value of the eventCategories property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setEventCategories(ArrayOfguid value) {
        this.eventCategories = value;
    }

    /**
     * Gets the value of the relatedSurveillanceItems property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFQID }
     *     
     */
    public ArrayOfFQID getRelatedSurveillanceItems() {
        return relatedSurveillanceItems;
    }

    /**
     * Sets the value of the relatedSurveillanceItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFQID }
     *     
     */
    public void setRelatedSurveillanceItems(ArrayOfFQID value) {
        this.relatedSurveillanceItems = value;
    }

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProperty }
     *     
     */
    public ArrayOfProperty getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProperty }
     *     
     */
    public void setProperties(ArrayOfProperty value) {
        this.properties = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

}
