
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * &lt;p&gt;Java class for anonymous complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="deviceIds" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="searchText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="users" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="createdFromTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="createdToTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="markedDataFromTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="markedDataToTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="tagTimeFromTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="tagTimeToTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="retentionExpireFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="retentionExpireTo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="pageIndex" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="pageSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="sortOrder" type="{http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService}SortOrderOption" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="sortAscending" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "token",
    "deviceIds",
    "searchText",
    "users",
    "createdFromTime",
    "createdToTime",
    "markedDataFromTime",
    "markedDataToTime",
    "tagTimeFromTime",
    "tagTimeToTime",
    "retentionExpireFrom",
    "retentionExpireTo",
    "pageIndex",
    "pageSize",
    "sortOrder",
    "sortAscending"
})
@XmlRootElement(name = "MarkedDataSearch")
public class MarkedDataSearch {

    @XmlElement(nillable = true)
    protected String token;
    @XmlElement(nillable = true)
    protected ArrayOfguid deviceIds;
    @XmlElement(nillable = true)
    protected String searchText;
    @XmlElement(nillable = true)
    protected ArrayOfstring users;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdFromTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdToTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar markedDataFromTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar markedDataToTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tagTimeFromTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tagTimeToTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar retentionExpireFrom;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar retentionExpireTo;
    protected Integer pageIndex;
    protected Integer pageSize;
    @XmlSchemaType(name = "string")
    protected SortOrderOption sortOrder;
    protected Boolean sortAscending;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the deviceIds property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getDeviceIds() {
        return deviceIds;
    }

    /**
     * Sets the value of the deviceIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setDeviceIds(ArrayOfguid value) {
        this.deviceIds = value;
    }

    /**
     * Gets the value of the searchText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchText() {
        return searchText;
    }

    /**
     * Sets the value of the searchText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchText(String value) {
        this.searchText = value;
    }

    /**
     * Gets the value of the users property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfstring }
     *     
     */
    public ArrayOfstring getUsers() {
        return users;
    }

    /**
     * Sets the value of the users property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfstring }
     *     
     */
    public void setUsers(ArrayOfstring value) {
        this.users = value;
    }

    /**
     * Gets the value of the createdFromTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedFromTime() {
        return createdFromTime;
    }

    /**
     * Sets the value of the createdFromTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedFromTime(XMLGregorianCalendar value) {
        this.createdFromTime = value;
    }

    /**
     * Gets the value of the createdToTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedToTime() {
        return createdToTime;
    }

    /**
     * Sets the value of the createdToTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedToTime(XMLGregorianCalendar value) {
        this.createdToTime = value;
    }

    /**
     * Gets the value of the markedDataFromTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMarkedDataFromTime() {
        return markedDataFromTime;
    }

    /**
     * Sets the value of the markedDataFromTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMarkedDataFromTime(XMLGregorianCalendar value) {
        this.markedDataFromTime = value;
    }

    /**
     * Gets the value of the markedDataToTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMarkedDataToTime() {
        return markedDataToTime;
    }

    /**
     * Sets the value of the markedDataToTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMarkedDataToTime(XMLGregorianCalendar value) {
        this.markedDataToTime = value;
    }

    /**
     * Gets the value of the tagTimeFromTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTagTimeFromTime() {
        return tagTimeFromTime;
    }

    /**
     * Sets the value of the tagTimeFromTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTagTimeFromTime(XMLGregorianCalendar value) {
        this.tagTimeFromTime = value;
    }

    /**
     * Gets the value of the tagTimeToTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTagTimeToTime() {
        return tagTimeToTime;
    }

    /**
     * Sets the value of the tagTimeToTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTagTimeToTime(XMLGregorianCalendar value) {
        this.tagTimeToTime = value;
    }

    /**
     * Gets the value of the retentionExpireFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRetentionExpireFrom() {
        return retentionExpireFrom;
    }

    /**
     * Sets the value of the retentionExpireFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRetentionExpireFrom(XMLGregorianCalendar value) {
        this.retentionExpireFrom = value;
    }

    /**
     * Gets the value of the retentionExpireTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRetentionExpireTo() {
        return retentionExpireTo;
    }

    /**
     * Sets the value of the retentionExpireTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRetentionExpireTo(XMLGregorianCalendar value) {
        this.retentionExpireTo = value;
    }

    /**
     * Gets the value of the pageIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPageIndex() {
        return pageIndex;
    }

    /**
     * Sets the value of the pageIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPageIndex(Integer value) {
        this.pageIndex = value;
    }

    /**
     * Gets the value of the pageSize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Sets the value of the pageSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPageSize(Integer value) {
        this.pageSize = value;
    }

    /**
     * Gets the value of the sortOrder property.
     * 
     * @return
     *     possible object is
     *     {@link SortOrderOption }
     *     
     */
    public SortOrderOption getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the value of the sortOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link SortOrderOption }
     *     
     */
    public void setSortOrder(SortOrderOption value) {
        this.sortOrder = value;
    }

    /**
     * Gets the value of the sortAscending property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSortAscending() {
        return sortAscending;
    }

    /**
     * Sets the value of the sortAscending property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSortAscending(Boolean value) {
        this.sortAscending = value;
    }

}
