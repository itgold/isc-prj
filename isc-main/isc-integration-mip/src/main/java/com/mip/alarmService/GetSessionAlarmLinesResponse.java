
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
 *         &amp;lt;element name="GetSessionAlarmLinesResult" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}AlarmUpdateData" minOccurs="0"/&amp;gt;
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
    "getSessionAlarmLinesResult"
})
@XmlRootElement(name = "GetSessionAlarmLinesResponse")
public class GetSessionAlarmLinesResponse {

    @XmlElement(name = "GetSessionAlarmLinesResult", nillable = true)
    protected AlarmUpdateData getSessionAlarmLinesResult;

    /**
     * Gets the value of the getSessionAlarmLinesResult property.
     * 
     * @return
     *     possible object is
     *     {@link AlarmUpdateData }
     *     
     */
    public AlarmUpdateData getGetSessionAlarmLinesResult() {
        return getSessionAlarmLinesResult;
    }

    /**
     * Sets the value of the getSessionAlarmLinesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlarmUpdateData }
     *     
     */
    public void setGetSessionAlarmLinesResult(AlarmUpdateData value) {
        this.getSessionAlarmLinesResult = value;
    }

}
