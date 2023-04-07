
package com.mip.recorderStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for MetadataDeviceStatistics complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MetadataDeviceStatistics"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSRecorderStatus2}DeviceStatisticsBase"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="MetadataStreamStatisticsArray" type="{http://video.net/2/XProtectCSRecorderStatus2}ArrayOfMetadataStreamStatistics" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetadataDeviceStatistics", propOrder = {
    "metadataStreamStatisticsArray"
})
public class MetadataDeviceStatistics
    extends DeviceStatisticsBase
{

    @XmlElement(name = "MetadataStreamStatisticsArray")
    protected ArrayOfMetadataStreamStatistics metadataStreamStatisticsArray;

    /**
     * Gets the value of the metadataStreamStatisticsArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMetadataStreamStatistics }
     *     
     */
    public ArrayOfMetadataStreamStatistics getMetadataStreamStatisticsArray() {
        return metadataStreamStatisticsArray;
    }

    /**
     * Sets the value of the metadataStreamStatisticsArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMetadataStreamStatistics }
     *     
     */
    public void setMetadataStreamStatisticsArray(ArrayOfMetadataStreamStatistics value) {
        this.metadataStreamStatisticsArray = value;
    }

}
