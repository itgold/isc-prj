
package com.mip.recorderStatus;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ArrayOfSpeakerDeviceStatus complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ArrayOfSpeakerDeviceStatus"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="SpeakerDeviceStatus" type="{http://video.net/2/XProtectCSRecorderStatus2}SpeakerDeviceStatus" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSpeakerDeviceStatus", propOrder = {
    "speakerDeviceStatus"
})
public class ArrayOfSpeakerDeviceStatus {

    @XmlElement(name = "SpeakerDeviceStatus", nillable = true)
    protected List<SpeakerDeviceStatus> speakerDeviceStatus;

    /**
     * Gets the value of the speakerDeviceStatus property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the speakerDeviceStatus property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSpeakerDeviceStatus().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SpeakerDeviceStatus }
     * 
     * 
     */
    public List<SpeakerDeviceStatus> getSpeakerDeviceStatus() {
        if (speakerDeviceStatus == null) {
            speakerDeviceStatus = new ArrayList<SpeakerDeviceStatus>();
        }
        return this.speakerDeviceStatus;
    }

}
