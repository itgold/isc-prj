
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
 *         &amp;lt;element name="alertIds" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="centerTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="maxTimeBefore" type="{http://video.net/2/XProtectCSServerCommand}TimeDuration" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="maxCountBefore" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="maxTimeAfter" type="{http://video.net/2/XProtectCSServerCommand}TimeDuration" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="maxCountAfter" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
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
    "alertIds",
    "centerTime",
    "maxTimeBefore",
    "maxCountBefore",
    "maxTimeAfter",
    "maxCountAfter"
})
@XmlRootElement(name = "AlertsGetAroundWithSpan")
public class AlertsGetAroundWithSpan {

    @XmlElement(nillable = true)
    protected String token;
    @XmlElement(nillable = true)
    protected ArrayOfguid alertIds;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar centerTime;
    @XmlElement(nillable = true)
    protected TimeDuration maxTimeBefore;
    protected Integer maxCountBefore;
    @XmlElement(nillable = true)
    protected TimeDuration maxTimeAfter;
    protected Integer maxCountAfter;

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
     * Gets the value of the alertIds property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getAlertIds() {
        return alertIds;
    }

    /**
     * Sets the value of the alertIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setAlertIds(ArrayOfguid value) {
        this.alertIds = value;
    }

    /**
     * Gets the value of the centerTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCenterTime() {
        return centerTime;
    }

    /**
     * Sets the value of the centerTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCenterTime(XMLGregorianCalendar value) {
        this.centerTime = value;
    }

    /**
     * Gets the value of the maxTimeBefore property.
     * 
     * @return
     *     possible object is
     *     {@link TimeDuration }
     *     
     */
    public TimeDuration getMaxTimeBefore() {
        return maxTimeBefore;
    }

    /**
     * Sets the value of the maxTimeBefore property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeDuration }
     *     
     */
    public void setMaxTimeBefore(TimeDuration value) {
        this.maxTimeBefore = value;
    }

    /**
     * Gets the value of the maxCountBefore property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxCountBefore() {
        return maxCountBefore;
    }

    /**
     * Sets the value of the maxCountBefore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxCountBefore(Integer value) {
        this.maxCountBefore = value;
    }

    /**
     * Gets the value of the maxTimeAfter property.
     * 
     * @return
     *     possible object is
     *     {@link TimeDuration }
     *     
     */
    public TimeDuration getMaxTimeAfter() {
        return maxTimeAfter;
    }

    /**
     * Sets the value of the maxTimeAfter property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeDuration }
     *     
     */
    public void setMaxTimeAfter(TimeDuration value) {
        this.maxTimeAfter = value;
    }

    /**
     * Gets the value of the maxCountAfter property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxCountAfter() {
        return maxCountAfter;
    }

    /**
     * Sets the value of the maxCountAfter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxCountAfter(Integer value) {
        this.maxCountAfter = value;
    }

}
