
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
 *         &amp;lt;element name="GetResult" type="{urn:milestone-systems}Alarm" minOccurs="0"/&amp;gt;
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
    "getResult"
})
@XmlRootElement(name = "GetResponse")
public class GetResponse {

    @XmlElement(name = "GetResult", nillable = true)
    protected Alarm getResult;

    /**
     * Gets the value of the getResult property.
     * 
     * @return
     *     possible object is
     *     {@link Alarm }
     *     
     */
    public Alarm getGetResult() {
        return getResult;
    }

    /**
     * Sets the value of the getResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Alarm }
     *     
     */
    public void setGetResult(Alarm value) {
        this.getResult = value;
    }

}
