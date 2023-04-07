
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SmartClientSecurityInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SmartClientSecurityInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="SmartClientBrowse" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SmartClientLive" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SmartClientReport" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SmartClientSetup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SmartClientSecurityInfo", propOrder = {
    "smartClientBrowse",
    "smartClientLive",
    "smartClientReport",
    "smartClientSetup"
})
public class SmartClientSecurityInfo {

    @XmlElement(name = "SmartClientBrowse")
    protected Boolean smartClientBrowse;
    @XmlElement(name = "SmartClientLive")
    protected Boolean smartClientLive;
    @XmlElement(name = "SmartClientReport")
    protected Boolean smartClientReport;
    @XmlElement(name = "SmartClientSetup")
    protected Boolean smartClientSetup;

    /**
     * Gets the value of the smartClientBrowse property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSmartClientBrowse() {
        return smartClientBrowse;
    }

    /**
     * Sets the value of the smartClientBrowse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSmartClientBrowse(Boolean value) {
        this.smartClientBrowse = value;
    }

    /**
     * Gets the value of the smartClientLive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSmartClientLive() {
        return smartClientLive;
    }

    /**
     * Sets the value of the smartClientLive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSmartClientLive(Boolean value) {
        this.smartClientLive = value;
    }

    /**
     * Gets the value of the smartClientReport property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSmartClientReport() {
        return smartClientReport;
    }

    /**
     * Sets the value of the smartClientReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSmartClientReport(Boolean value) {
        this.smartClientReport = value;
    }

    /**
     * Gets the value of the smartClientSetup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSmartClientSetup() {
        return smartClientSetup;
    }

    /**
     * Sets the value of the smartClientSetup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSmartClientSetup(Boolean value) {
        this.smartClientSetup = value;
    }

}
