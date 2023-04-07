
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
 *         &amp;lt;element name="GetEventsResult" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}GetEventsResp" minOccurs="0"/&amp;gt;
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
    "getEventsResult"
})
@XmlRootElement(name = "GetEventsResponse")
public class GetEventsResponse {

    @XmlElement(name = "GetEventsResult", nillable = true)
    protected GetEventsResp getEventsResult;

    /**
     * Gets the value of the getEventsResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetEventsResp }
     *     
     */
    public GetEventsResp getGetEventsResult() {
        return getEventsResult;
    }

    /**
     * Sets the value of the getEventsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetEventsResp }
     *     
     */
    public void setGetEventsResult(GetEventsResp value) {
        this.getEventsResult = value;
    }

}
