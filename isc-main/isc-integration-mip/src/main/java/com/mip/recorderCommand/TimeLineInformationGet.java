
package com.mip.recorderCommand;

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
 *         &amp;lt;element name="deviceId" type="{http://microsoft.com/wsdl/types/}guid"/&amp;gt;
 *         &amp;lt;element name="timeLineInformationTypes" type="{http://video.net/2/XProtectCSRecorderCommand}ArrayOfGuid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="timeLineInformationBeginTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="timeLineInformationInterval" type="{http://video.net/2/XProtectCSRecorderCommand}TimeDuration" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="timeLineInformationCount" type="{http://www.w3.org/2001/XMLSchema}int"/&amp;gt;
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
    "deviceId",
    "timeLineInformationTypes",
    "timeLineInformationBeginTime",
    "timeLineInformationInterval",
    "timeLineInformationCount"
})
@XmlRootElement(name = "TimeLineInformationGet")
public class TimeLineInformationGet {

    protected String token;
    @XmlElement(required = true)
    protected String deviceId;
    protected ArrayOfGuid timeLineInformationTypes;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeLineInformationBeginTime;
    protected TimeDuration timeLineInformationInterval;
    protected int timeLineInformationCount;

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
     * Gets the value of the timeLineInformationTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGuid }
     *     
     */
    public ArrayOfGuid getTimeLineInformationTypes() {
        return timeLineInformationTypes;
    }

    /**
     * Sets the value of the timeLineInformationTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGuid }
     *     
     */
    public void setTimeLineInformationTypes(ArrayOfGuid value) {
        this.timeLineInformationTypes = value;
    }

    /**
     * Gets the value of the timeLineInformationBeginTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimeLineInformationBeginTime() {
        return timeLineInformationBeginTime;
    }

    /**
     * Sets the value of the timeLineInformationBeginTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimeLineInformationBeginTime(XMLGregorianCalendar value) {
        this.timeLineInformationBeginTime = value;
    }

    /**
     * Gets the value of the timeLineInformationInterval property.
     * 
     * @return
     *     possible object is
     *     {@link TimeDuration }
     *     
     */
    public TimeDuration getTimeLineInformationInterval() {
        return timeLineInformationInterval;
    }

    /**
     * Sets the value of the timeLineInformationInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeDuration }
     *     
     */
    public void setTimeLineInformationInterval(TimeDuration value) {
        this.timeLineInformationInterval = value;
    }

    /**
     * Gets the value of the timeLineInformationCount property.
     * 
     */
    public int getTimeLineInformationCount() {
        return timeLineInformationCount;
    }

    /**
     * Sets the value of the timeLineInformationCount property.
     * 
     */
    public void setTimeLineInformationCount(int value) {
        this.timeLineInformationCount = value;
    }

}
