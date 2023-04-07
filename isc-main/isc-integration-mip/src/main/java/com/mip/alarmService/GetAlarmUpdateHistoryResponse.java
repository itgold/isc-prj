
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
 *         &amp;lt;element name="GetAlarmUpdateHistoryResult" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}ArrayOfAlarmUpdate" minOccurs="0"/&amp;gt;
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
    "getAlarmUpdateHistoryResult"
})
@XmlRootElement(name = "GetAlarmUpdateHistoryResponse")
public class GetAlarmUpdateHistoryResponse {

    @XmlElement(name = "GetAlarmUpdateHistoryResult", nillable = true)
    protected ArrayOfAlarmUpdate getAlarmUpdateHistoryResult;

    /**
     * Gets the value of the getAlarmUpdateHistoryResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAlarmUpdate }
     *     
     */
    public ArrayOfAlarmUpdate getGetAlarmUpdateHistoryResult() {
        return getAlarmUpdateHistoryResult;
    }

    /**
     * Sets the value of the getAlarmUpdateHistoryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAlarmUpdate }
     *     
     */
    public void setGetAlarmUpdateHistoryResult(ArrayOfAlarmUpdate value) {
        this.getAlarmUpdateHistoryResult = value;
    }

}
