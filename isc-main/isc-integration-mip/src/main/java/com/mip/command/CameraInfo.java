
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for CameraInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CameraInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}DeviceInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="BrowsableStream" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CameraSecurity" type="{http://video.net/2/XProtectCSServerCommand}CameraSecurityInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EdgeStoragePlayback" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EdgeStorageSupported" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IpixSettings" type="{http://video.net/2/XProtectCSServerCommand}IpixInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MaxFPS" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MulticastEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PanoramicLensSettings" type="{http://video.net/2/XProtectCSServerCommand}PanoramicLensInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PtzSettings" type="{http://video.net/2/XProtectCSServerCommand}PtzInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="StopManualRecordingSeconds" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Streams" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfStreamInfo" minOccurs="0"/&amp;gt;
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
@XmlType(name = "CameraInfo", propOrder = {
    "browsableStream",
    "cameraSecurity",
    "edgeStoragePlayback",
    "edgeStorageSupported",
    "ipixSettings",
    "maxFPS",
    "multicastEnabled",
    "panoramicLensSettings",
    "ptzSettings",
    "stopManualRecordingSeconds",
    "streams",
    "tracks"
})
public class CameraInfo
    extends DeviceInfo
{

    @XmlElement(name = "BrowsableStream")
    protected Boolean browsableStream;
    @XmlElement(name = "CameraSecurity", nillable = true)
    protected CameraSecurityInfo cameraSecurity;
    @XmlElement(name = "EdgeStoragePlayback")
    protected Boolean edgeStoragePlayback;
    @XmlElement(name = "EdgeStorageSupported")
    protected Boolean edgeStorageSupported;
    @XmlElement(name = "IpixSettings", nillable = true)
    protected IpixInfo ipixSettings;
    @XmlElement(name = "MaxFPS")
    protected Double maxFPS;
    @XmlElement(name = "MulticastEnabled")
    protected Boolean multicastEnabled;
    @XmlElement(name = "PanoramicLensSettings", nillable = true)
    protected PanoramicLensInfo panoramicLensSettings;
    @XmlElement(name = "PtzSettings", nillable = true)
    protected PtzInfo ptzSettings;
    @XmlElement(name = "StopManualRecordingSeconds")
    protected Integer stopManualRecordingSeconds;
    @XmlElement(name = "Streams", nillable = true)
    protected ArrayOfStreamInfo streams;
    @XmlElement(name = "Tracks", nillable = true)
    protected ArrayOfTrackInfo tracks;

    /**
     * Gets the value of the browsableStream property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBrowsableStream() {
        return browsableStream;
    }

    /**
     * Sets the value of the browsableStream property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBrowsableStream(Boolean value) {
        this.browsableStream = value;
    }

    /**
     * Gets the value of the cameraSecurity property.
     * 
     * @return
     *     possible object is
     *     {@link CameraSecurityInfo }
     *     
     */
    public CameraSecurityInfo getCameraSecurity() {
        return cameraSecurity;
    }

    /**
     * Sets the value of the cameraSecurity property.
     * 
     * @param value
     *     allowed object is
     *     {@link CameraSecurityInfo }
     *     
     */
    public void setCameraSecurity(CameraSecurityInfo value) {
        this.cameraSecurity = value;
    }

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
     * Gets the value of the ipixSettings property.
     * 
     * @return
     *     possible object is
     *     {@link IpixInfo }
     *     
     */
    public IpixInfo getIpixSettings() {
        return ipixSettings;
    }

    /**
     * Sets the value of the ipixSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link IpixInfo }
     *     
     */
    public void setIpixSettings(IpixInfo value) {
        this.ipixSettings = value;
    }

    /**
     * Gets the value of the maxFPS property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaxFPS() {
        return maxFPS;
    }

    /**
     * Sets the value of the maxFPS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaxFPS(Double value) {
        this.maxFPS = value;
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
     * Gets the value of the panoramicLensSettings property.
     * 
     * @return
     *     possible object is
     *     {@link PanoramicLensInfo }
     *     
     */
    public PanoramicLensInfo getPanoramicLensSettings() {
        return panoramicLensSettings;
    }

    /**
     * Sets the value of the panoramicLensSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link PanoramicLensInfo }
     *     
     */
    public void setPanoramicLensSettings(PanoramicLensInfo value) {
        this.panoramicLensSettings = value;
    }

    /**
     * Gets the value of the ptzSettings property.
     * 
     * @return
     *     possible object is
     *     {@link PtzInfo }
     *     
     */
    public PtzInfo getPtzSettings() {
        return ptzSettings;
    }

    /**
     * Sets the value of the ptzSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link PtzInfo }
     *     
     */
    public void setPtzSettings(PtzInfo value) {
        this.ptzSettings = value;
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
     * Gets the value of the streams property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStreamInfo }
     *     
     */
    public ArrayOfStreamInfo getStreams() {
        return streams;
    }

    /**
     * Sets the value of the streams property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStreamInfo }
     *     
     */
    public void setStreams(ArrayOfStreamInfo value) {
        this.streams = value;
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
