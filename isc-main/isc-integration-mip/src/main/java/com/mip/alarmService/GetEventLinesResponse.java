
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
 *         &amp;lt;element name="GetEventLinesResult" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}ArrayOfEventLine" minOccurs="0"/&amp;gt;
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
    "getEventLinesResult"
})
@XmlRootElement(name = "GetEventLinesResponse")
public class GetEventLinesResponse {

    @XmlElement(name = "GetEventLinesResult", nillable = true)
    protected ArrayOfEventLine getEventLinesResult;

    /**
     * Gets the value of the getEventLinesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEventLine }
     *     
     */
    public ArrayOfEventLine getGetEventLinesResult() {
        return getEventLinesResult;
    }

    /**
     * Sets the value of the getEventLinesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEventLine }
     *     
     */
    public void setGetEventLinesResult(ArrayOfEventLine value) {
        this.getEventLinesResult = value;
    }

}
