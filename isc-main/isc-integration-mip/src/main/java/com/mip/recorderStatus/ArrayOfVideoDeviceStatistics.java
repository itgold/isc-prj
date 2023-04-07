
package com.mip.recorderStatus;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ArrayOfVideoDeviceStatistics complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ArrayOfVideoDeviceStatistics"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="VideoDeviceStatistics" type="{http://video.net/2/XProtectCSRecorderStatus2}VideoDeviceStatistics" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVideoDeviceStatistics", propOrder = {
    "videoDeviceStatistics"
})
public class ArrayOfVideoDeviceStatistics {

    @XmlElement(name = "VideoDeviceStatistics", nillable = true)
    protected List<VideoDeviceStatistics> videoDeviceStatistics;

    /**
     * Gets the value of the videoDeviceStatistics property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the videoDeviceStatistics property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getVideoDeviceStatistics().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link VideoDeviceStatistics }
     * 
     * 
     */
    public List<VideoDeviceStatistics> getVideoDeviceStatistics() {
        if (videoDeviceStatistics == null) {
            videoDeviceStatistics = new ArrayList<VideoDeviceStatistics>();
        }
        return this.videoDeviceStatistics;
    }

}
