
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
 *         &amp;lt;element name="StartManualRecordingResult" type="{http://video.net/2/XProtectCSRecorderCommand}ArrayOfManualRecordingResult" minOccurs="0"/&amp;gt;
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
    "startManualRecordingResult"
})
@XmlRootElement(name = "StartManualRecordingResponse")
public class StartManualRecordingResponse {

    @XmlElement(name = "StartManualRecordingResult")
    protected ArrayOfManualRecordingResult startManualRecordingResult;

    /**
     * Gets the value of the startManualRecordingResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfManualRecordingResult }
     *     
     */
    public ArrayOfManualRecordingResult getStartManualRecordingResult() {
        return startManualRecordingResult;
    }

    /**
     * Sets the value of the startManualRecordingResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfManualRecordingResult }
     *     
     */
    public void setStartManualRecordingResult(ArrayOfManualRecordingResult value) {
        this.startManualRecordingResult = value;
    }

}
