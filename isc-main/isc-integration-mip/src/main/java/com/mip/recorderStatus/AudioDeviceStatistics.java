
package com.mip.recorderStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for AudioDeviceStatistics complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="AudioDeviceStatistics"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSRecorderStatus2}DeviceStatisticsBase"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="AudioStreamStatisticsArray" type="{http://video.net/2/XProtectCSRecorderStatus2}ArrayOfAudioStreamStatistics" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AudioDeviceStatistics", propOrder = {
    "audioStreamStatisticsArray"
})
public class AudioDeviceStatistics
    extends DeviceStatisticsBase
{

    @XmlElement(name = "AudioStreamStatisticsArray")
    protected ArrayOfAudioStreamStatistics audioStreamStatisticsArray;

    /**
     * Gets the value of the audioStreamStatisticsArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAudioStreamStatistics }
     *     
     */
    public ArrayOfAudioStreamStatistics getAudioStreamStatisticsArray() {
        return audioStreamStatisticsArray;
    }

    /**
     * Sets the value of the audioStreamStatisticsArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAudioStreamStatistics }
     *     
     */
    public void setAudioStreamStatisticsArray(ArrayOfAudioStreamStatistics value) {
        this.audioStreamStatisticsArray = value;
    }

}
