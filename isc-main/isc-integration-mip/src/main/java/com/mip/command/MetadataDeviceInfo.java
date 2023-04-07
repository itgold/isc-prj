
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for MetadataDeviceInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MetadataDeviceInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}DeviceInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="EdgeStoragePlayback" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EdgeStorageSupported" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MetadataDeviceSecurity" type="{http://video.net/2/XProtectCSServerCommand}MetadataDeviceSecurityInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MetadataTypes" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfMetadataTypeInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MulticastEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="StopManualRecordingSeconds" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Tracks" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfTrackInfo" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetadataDeviceInfo", propOrder = {
    "edgeStoragePlayback",
    "edgeStorageSupported",
    "metadataDeviceSecurity",
    "metadataTypes",
    "multicastEnabled",
    "stopManualRecordingSeconds",
    "tracks"
})
public class MetadataDeviceInfo
    extends DeviceInfo
{

    @XmlElement(name = "EdgeStoragePlayback")
    protected Boolean edgeStoragePlayback;
    @XmlElement(name = "EdgeStorageSupported")
    protected Boolean edgeStorageSupported;
    @XmlElement(name = "MetadataDeviceSecurity", nillable = true)
    protected MetadataDeviceSecurityInfo metadataDeviceSecurity;
    @XmlElement(name = "MetadataTypes", nillable = true)
    protected ArrayOfMetadataTypeInfo metadataTypes;
    @XmlElement(name = "MulticastEnabled")
    protected Boolean multicastEnabled;
    @XmlElement(name = "StopManualRecordingSeconds")
    protected Integer stopManualRecordingSeconds;
    @XmlElement(name = "Tracks", nillable = true)
    protected ArrayOfTrackInfo tracks;

    /**
     * Gets the value of the edgeStoragePlayback property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEdgeStoragePlayback() {
        return edgeStoragePlayback;
    }

    /**
     * Sets the value of the edgeStoragePlayback property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEdgeStoragePlayback(Boolean value) {
        this.edgeStoragePlayback = value;
    }

    /**
     * Gets the value of the edgeStorageSupported property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEdgeStorageSupported() {
        return edgeStorageSupported;
    }

    /**
     * Sets the value of the edgeStorageSupported property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEdgeStorageSupported(Boolean value) {
        this.edgeStorageSupported = value;
    }

    /**
     * Gets the value of the metadataDeviceSecurity property.
     * 
     * @return
     *     possible object is
     *     {@link MetadataDeviceSecurityInfo }
     *     
     */
    public MetadataDeviceSecurityInfo getMetadataDeviceSecurity() {
        return metadataDeviceSecurity;
    }

    /**
     * Sets the value of the metadataDeviceSecurity property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetadataDeviceSecurityInfo }
     *     
     */
    public void setMetadataDeviceSecurity(MetadataDeviceSecurityInfo value) {
        this.metadataDeviceSecurity = value;
    }

    /**
     * Gets the value of the metadataTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMetadataTypeInfo }
     *     
     */
    public ArrayOfMetadataTypeInfo getMetadataTypes() {
        return metadataTypes;
    }

    /**
     * Sets the value of the metadataTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMetadataTypeInfo }
     *     
     */
    public void setMetadataTypes(ArrayOfMetadataTypeInfo value) {
        this.metadataTypes = value;
    }

    /**
     * Gets the value of the multicastEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMulticastEnabled() {
        return multicastEnabled;
    }

    /**
     * Sets the value of the multicastEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMulticastEnabled(Boolean value) {
        this.multicastEnabled = value;
    }

    /**
     * Gets the value of the stopManualRecordingSeconds property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStopManualRecordingSeconds() {
        return stopManualRecordingSeconds;
    }

    /**
     * Sets the value of the stopManualRecordingSeconds property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStopManualRecordingSeconds(Integer value) {
        this.stopManualRecordingSeconds = value;
    }

    /**
     * Gets the value of the tracks property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTrackInfo }
     *     
     */
    public ArrayOfTrackInfo getTracks() {
        return tracks;
    }

    /**
     * Sets the value of the tracks property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTrackInfo }
     *     
     */
    public void setTracks(ArrayOfTrackInfo value) {
        this.tracks = value;
    }

}
