
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
 *         &amp;lt;element name="StopManualRecordingResult" type="{http://video.net/2/XProtectCSRecorderCommand}ArrayOfManualRecordingResult" minOccurs="0"/&amp;gt;
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
    "stopManualRecordingResult"
})
@XmlRootElement(name = "StopManualRecordingResponse")
public class StopManualRecordingResponse {

    @XmlElement(name = "StopManualRecordingResult")
    protected ArrayOfManualRecordingResult stopManualRecordingResult;

    /**
     * Gets the value of the stopManualRecordingResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfManualRecordingResult }
     *     
     */
    public ArrayOfManualRecordingResult getStopManualRecordingResult() {
        return stopManualRecordingResult;
    }

    /**
     * Sets the value of the stopManualRecordingResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfManualRecordingResult }
     *     
     */
    public void setStopManualRecordingResult(ArrayOfManualRecordingResult value) {
        this.stopManualRecordingResult = value;
    }

}
