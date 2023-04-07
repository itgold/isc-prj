
package com.mip.recorderCommand;

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
 *         &amp;lt;element name="TimeLineInformationGetResult" type="{http://video.net/2/XProtectCSRecorderCommand}ArrayOfTimeLineInformationData" minOccurs="0"/&amp;gt;
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
    "timeLineInformationGetResult"
})
@XmlRootElement(name = "TimeLineInformationGetResponse")
public class TimeLineInformationGetResponse {

    @XmlElement(name = "TimeLineInformationGetResult")
    protected ArrayOfTimeLineInformationData timeLineInformationGetResult;

    /**
     * Gets the value of the timeLineInformationGetResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTimeLineInformationData }
     *     
     */
    public ArrayOfTimeLineInformationData getTimeLineInformationGetResult() {
        return timeLineInformationGetResult;
    }

    /**
     * Sets the value of the timeLineInformationGetResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTimeLineInformationData }
     *     
     */
    public void setTimeLineInformationGetResult(ArrayOfTimeLineInformationData value) {
        this.timeLineInformationGetResult = value;
    }

}
