
package com.mip.recorderStatus;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for VideoStreamStatistics complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="VideoStreamStatistics"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSRecorderStatus2}MediaStreamStatisticsBase"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="ImageResolution" type="{http://video.net/2/XProtectCSRecorderStatus2}Size"/&amp;gt;
 *         &amp;lt;element name="VideoFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ImageSizeInBytes" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&amp;gt;
 *         &amp;lt;element name="FPSRequested" type="{http://www.w3.org/2001/XMLSchema}double"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VideoStreamStatistics", propOrder = {
    "imageResolution",
    "videoFormat",
    "imageSizeInBytes",
    "fpsRequested"
})
public class VideoStreamStatistics
    extends MediaStreamStatisticsBase
{

    @XmlElement(name = "ImageResolution", required = true)
    protected Size imageResolution;
    @XmlElement(name = "VideoFormat")
    protected String videoFormat;
    @XmlElement(name = "ImageSizeInBytes", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger imageSizeInBytes;
    @XmlElement(name = "FPSRequested")
    protected double fpsRequested;

    /**
     * Gets the value of the imageResolution property.
     * 
     * @return
     *     possible object is
     *     {@link Size }
     *     
     */
    public Size getImageResolution() {
        return imageResolution;
    }

    /**
     * Sets the value of the imageResolution property.
     * 
     * @param value
     *     allowed object is
     *     {@link Size }
     *     
     */
    public void setImageResolution(Size value) {
        this.imageResolution = value;
    }

    /**
     * Gets the value of the videoFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoFormat() {
        return videoFormat;
    }

    /**
     * Sets the value of the videoFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoFormat(String value) {
        this.videoFormat = value;
    }

    /**
     * Gets the value of the imageSizeInBytes property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getImageSizeInBytes() {
        return imageSizeInBytes;
    }

    /**
     * Sets the value of the imageSizeInBytes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setImageSizeInBytes(BigInteger value) {
        this.imageSizeInBytes = value;
    }

    /**
     * Gets the value of the fpsRequested property.
     * 
     */
    public double getFPSRequested() {
        return fpsRequested;
    }

    /**
     * Sets the value of the fpsRequested property.
     * 
     */
    public void setFPSRequested(double value) {
        this.fpsRequested = value;
    }

}
