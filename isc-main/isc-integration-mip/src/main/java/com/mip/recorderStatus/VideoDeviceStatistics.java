
package com.mip.recorderStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for VideoDeviceStatistics complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="VideoDeviceStatistics"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSRecorderStatus2}DeviceStatisticsBase"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="VideoStreamStatisticsArray" type="{http://video.net/2/XProtectCSRecorderStatus2}ArrayOfVideoStreamStatistics" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VideoDeviceStatistics", propOrder = {
    "videoStreamStatisticsArray"
})
public class VideoDeviceStatistics
    extends DeviceStatisticsBase
{

    @XmlElement(name = "VideoStreamStatisticsArray")
    protected ArrayOfVideoStreamStatistics videoStreamStatisticsArray;

    /**
     * Gets the value of the videoStreamStatisticsArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVideoStreamStatistics }
     *     
     */
    public ArrayOfVideoStreamStatistics getVideoStreamStatisticsArray() {
        return videoStreamStatisticsArray;
    }

    /**
     * Sets the value of the videoStreamStatisticsArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVideoStreamStatistics }
     *     
     */
    public void setVideoStreamStatisticsArray(ArrayOfVideoStreamStatistics value) {
        this.videoStreamStatisticsArray = value;
    }

}
