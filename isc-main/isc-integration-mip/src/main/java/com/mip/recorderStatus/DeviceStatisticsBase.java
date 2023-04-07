
package com.mip.recorderStatus;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for DeviceStatisticsBase complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="DeviceStatisticsBase"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="DeviceId" type="{http://microsoft.com/wsdl/types/}guid"/&amp;gt;
 *         &amp;lt;element name="UsedSpaceInBytes" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceStatisticsBase", propOrder = {
    "deviceId",
    "usedSpaceInBytes"
})
@XmlSeeAlso({
    VideoDeviceStatistics.class,
    AudioDeviceStatistics.class,
    MetadataDeviceStatistics.class
})
public class DeviceStatisticsBase {

    @XmlElement(name = "DeviceId", required = true)
    protected String deviceId;
    @XmlElement(name = "UsedSpaceInBytes", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger usedSpaceInBytes;

    /**
     * Gets the value of the deviceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the value of the deviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceId(String value) {
        this.deviceId = value;
    }

    /**
     * Gets the value of the usedSpaceInBytes property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUsedSpaceInBytes() {
        return usedSpaceInBytes;
    }

    /**
     * Sets the value of the usedSpaceInBytes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUsedSpaceInBytes(BigInteger value) {
        this.usedSpaceInBytes = value;
    }

}
