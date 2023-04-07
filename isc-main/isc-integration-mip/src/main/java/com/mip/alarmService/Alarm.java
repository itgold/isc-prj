
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * &lt;p&gt;Java class for Alarm complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="Alarm"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="EventHeader" type="{urn:milestone-systems}EventHeader"/&amp;gt;
 *         &amp;lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="StateName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CategoryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Count" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RuleList" type="{urn:milestone-systems}RuleList" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ObjectList" type="{urn:milestone-systems}AnalyticsObjectList" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ReferenceList" type="{urn:milestone-systems}ReferenceList" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SnapshotList" type="{urn:milestone-systems}SnapshotList" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Vendor" type="{urn:milestone-systems}Vendor" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Alarm", namespace = "urn:milestone-systems", propOrder = {
    "eventHeader",
    "description",
    "startTime",
    "endTime",
    "location",
    "state",
    "stateName",
    "category",
    "categoryName",
    "assignedTo",
    "count",
    "ruleList",
    "objectList",
    "referenceList",
    "snapshotList",
    "vendor"
})
public class Alarm {

    @XmlElement(name = "EventHeader", required = true, nillable = true)
    protected EventHeader eventHeader;
    @XmlElement(name = "Description", nillable = true)
    protected String description;
    @XmlElement(name = "StartTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    @XmlElement(name = "EndTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    @XmlElement(name = "Location", nillable = true)
    protected String location;
    @XmlElement(name = "State")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer state;
    @XmlElement(name = "StateName", nillable = true)
    protected String stateName;
    @XmlElement(name = "Category")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer category;
    @XmlElement(name = "CategoryName", nillable = true)
    protected String categoryName;
    @XmlElement(name = "AssignedTo", nillable = true)
    protected String assignedTo;
    @XmlElement(name = "Count")
    @XmlSchemaType(name = "unsignedInt")
    protected Long count;
    @XmlElement(name = "RuleList", nillable = true)
    protected RuleList ruleList;
    @XmlElement(name = "ObjectList", nillable = true)
    protected AnalyticsObjectList objectList;
    @XmlElement(name = "ReferenceList", nillable = true)
    protected ReferenceList referenceList;
    @XmlElement(name = "SnapshotList", nillable = true)
    protected SnapshotList snapshotList;
    @XmlElement(name = "Vendor", nillable = true)
    protected Vendor vendor;

    /**
     * Gets the value of the eventHeader property.
     * 
     * @return
     *     possible object is
     *     {@link EventHeader }
     *     
     */
    public EventHeader getEventHeader() {
        return eventHeader;
    }

    /**
     * Sets the value of the eventHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventHeader }
     *     
     */
    public void setEventHeader(EventHeader value) {
        this.eventHeader = value;
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
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setState(Integer value) {
        this.state = value;
    }

    /**
     * Gets the value of the stateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * Sets the value of the stateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateName(String value) {
        this.stateName = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCategory(Integer value) {
        this.category = value;
    }

    /**
     * Gets the value of the categoryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the value of the categoryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoryName(String value) {
        this.categoryName = value;
    }

    /**
     * Gets the value of the assignedTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignedTo() {
        return assignedTo;
    }

    /**
     * Sets the value of the assignedTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignedTo(String value) {
        this.assignedTo = value;
    }

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCount(Long value) {
        this.count = value;
    }

    /**
     * Gets the value of the ruleList property.
     * 
     * @return
     *     possible object is
     *     {@link RuleList }
     *     
     */
    public RuleList getRuleList() {
        return ruleList;
    }

    /**
     * Sets the value of the ruleList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuleList }
     *     
     */
    public void setRuleList(RuleList value) {
        this.ruleList = value;
    }

    /**
     * Gets the value of the objectList property.
     * 
     * @return
     *     possible object is
     *     {@link AnalyticsObjectList }
     *     
     */
    public AnalyticsObjectList getObjectList() {
        return objectList;
    }

    /**
     * Sets the value of the objectList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnalyticsObjectList }
     *     
     */
    public void setObjectList(AnalyticsObjectList value) {
        this.objectList = value;
    }

    /**
     * Gets the value of the referenceList property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceList }
     *     
     */
    public ReferenceList getReferenceList() {
        return referenceList;
    }

    /**
     * Sets the value of the referenceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceList }
     *     
     */
    public void setReferenceList(ReferenceList value) {
        this.referenceList = value;
    }

    /**
     * Gets the value of the snapshotList property.
     * 
     * @return
     *     possible object is
     *     {@link SnapshotList }
     *     
     */
    public SnapshotList getSnapshotList() {
        return snapshotList;
    }

    /**
     * Sets the value of the snapshotList property.
     * 
     * @param value
     *     allowed object is
     *     {@link SnapshotList }
     *     
     */
    public void setSnapshotList(SnapshotList value) {
        this.snapshotList = value;
    }

    /**
     * Gets the value of the vendor property.
     * 
     * @return
     *     possible object is
     *     {@link Vendor }
     *     
     */
    public Vendor getVendor() {
        return vendor;
    }

    /**
     * Sets the value of the vendor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vendor }
     *     
     */
    public void setVendor(Vendor value) {
        this.vendor = value;
    }

}
