
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
 *         &amp;lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="timeLimit" type="{http://video.net/2/XProtectCSServerCommand}TimeDuration" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="countLimit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="deviceTypes" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfMediaDeviceType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="optDeviceIds" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="optUsers" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="optSearchStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
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
    "time",
    "timeLimit",
    "countLimit",
    "deviceTypes",
    "optDeviceIds",
    "optUsers",
    "optSearchStr"
})
@XmlRootElement(name = "BookmarkSearchTime")
public class BookmarkSearchTime {

    @XmlElement(nillable = true)
    protected String token;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar time;
    @XmlElement(nillable = true)
    protected TimeDuration timeLimit;
    protected Integer countLimit;
    @XmlElement(nillable = true)
    protected ArrayOfMediaDeviceType deviceTypes;
    @XmlElement(nillable = true)
    protected ArrayOfguid optDeviceIds;
    @XmlElement(nillable = true)
    protected ArrayOfstring optUsers;
    @XmlElement(nillable = true)
    protected String optSearchStr;

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
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

    /**
     * Gets the value of the timeLimit property.
     * 
     * @return
     *     possible object is
     *     {@link TimeDuration }
     *     
     */
    public TimeDuration getTimeLimit() {
        return timeLimit;
    }

    /**
     * Sets the value of the timeLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeDuration }
     *     
     */
    public void setTimeLimit(TimeDuration value) {
        this.timeLimit = value;
    }

    /**
     * Gets the value of the countLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCountLimit() {
        return countLimit;
    }

    /**
     * Sets the value of the countLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCountLimit(Integer value) {
        this.countLimit = value;
    }

    /**
     * Gets the value of the deviceTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMediaDeviceType }
     *     
     */
    public ArrayOfMediaDeviceType getDeviceTypes() {
        return deviceTypes;
    }

    /**
     * Sets the value of the deviceTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMediaDeviceType }
     *     
     */
    public void setDeviceTypes(ArrayOfMediaDeviceType value) {
        this.deviceTypes = value;
    }

    /**
     * Gets the value of the optDeviceIds property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getOptDeviceIds() {
        return optDeviceIds;
    }

    /**
     * Sets the value of the optDeviceIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setOptDeviceIds(ArrayOfguid value) {
        this.optDeviceIds = value;
    }

    /**
     * Gets the value of the optUsers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfstring }
     *     
     */
    public ArrayOfstring getOptUsers() {
        return optUsers;
    }

    /**
     * Sets the value of the optUsers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfstring }
     *     
     */
    public void setOptUsers(ArrayOfstring value) {
        this.optUsers = value;
    }

    /**
     * Gets the value of the optSearchStr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptSearchStr() {
        return optSearchStr;
    }

    /**
     * Sets the value of the optSearchStr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptSearchStr(String value) {
        this.optSearchStr = value;
    }

}
