
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
 *         &amp;lt;element name="GetAlarmLinesResult" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}ArrayOfAlarmLine" minOccurs="0"/&amp;gt;
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
    "getAlarmLinesResult"
})
@XmlRootElement(name = "GetAlarmLinesResponse")
public class GetAlarmLinesResponse {

    @XmlElement(name = "GetAlarmLinesResult", nillable = true)
    protected ArrayOfAlarmLine getAlarmLinesResult;

    /**
     * Gets the value of the getAlarmLinesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAlarmLine }
     *     
     */
    public ArrayOfAlarmLine getGetAlarmLinesResult() {
        return getAlarmLinesResult;
    }

    /**
     * Sets the value of the getAlarmLinesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAlarmLine }
     *     
     */
    public void setGetAlarmLinesResult(ArrayOfAlarmLine value) {
        this.getAlarmLinesResult = value;
    }

}
