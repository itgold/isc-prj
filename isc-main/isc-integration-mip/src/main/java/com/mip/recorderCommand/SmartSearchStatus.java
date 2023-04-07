
package com.mip.recorderCommand;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * &lt;p&gt;Java class for SmartSearchStatus complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SmartSearchStatus"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CurrentSearchTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="Status" type="{http://video.net/2/XProtectCSRecorderCommand}SmartSearchStatusType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SmartSearchStatus", propOrder = {
    "currentSearchTime",
    "status"
})
public class SmartSearchStatus {

    @XmlElement(name = "CurrentSearchTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar currentSearchTime;
    @XmlElement(name = "Status", required = true)
    @XmlSchemaType(name = "string")
    protected SmartSearchStatusType status;

    /**
     * Gets the value of the currentSearchTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCurrentSearchTime() {
        return currentSearchTime;
    }

    /**
     * Sets the value of the currentSearchTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCurrentSearchTime(XMLGregorianCalendar value) {
        this.currentSearchTime = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link SmartSearchStatusType }
     *     
     */
    public SmartSearchStatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link SmartSearchStatusType }
     *     
     */
    public void setStatus(SmartSearchStatusType value) {
        this.status = value;
    }

}
