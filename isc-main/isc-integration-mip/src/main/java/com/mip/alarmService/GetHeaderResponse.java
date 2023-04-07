
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &amp;lt;element name="GetHeaderResult" type="{urn:milestone-systems}EventHeader" minOccurs="0"/&amp;gt;
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
    "getHeaderResult"
})
@XmlRootElement(name = "GetHeaderResponse")
public class GetHeaderResponse {

    @XmlElement(name = "GetHeaderResult", nillable = true)
    protected EventHeader getHeaderResult;

    /**
     * Gets the value of the getHeaderResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventHeader }
     *     
     */
    public EventHeader getGetHeaderResult() {
        return getHeaderResult;
    }

    /**
     * Sets the value of the getHeaderResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventHeader }
     *     
     */
    public void setGetHeaderResult(EventHeader value) {
        this.getHeaderResult = value;
    }

}
