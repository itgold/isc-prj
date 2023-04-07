
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * &lt;p&gt;Java class for RecorderInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RecorderInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Cameras" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfCameraInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DefaultRecordingTimeSeconds" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Hardware" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfHardwareInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="HostName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Inputs" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfInputInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="LastModified" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MetadataDevices" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfMetadataDeviceInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Microphones" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfMicrophoneInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Outputs" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfOutputInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RecorderId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ServiceId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Speakers" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfSpeakerInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TimeZoneName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="WebServerUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="XmlEncoding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecorderInfo", propOrder = {
    "cameras",
    "defaultRecordingTimeSeconds",
    "description",
    "hardware",
    "hostName",
    "inputs",
    "lastModified",
    "metadataDevices",
    "microphones",
    "name",
    "outputs",
    "recorderId",
    "serviceId",
    "speakers",
    "timeZoneName",
    "webServerUri",
    "xmlEncoding"
})
public class RecorderInfo {

    @XmlElement(name = "Cameras", nillable = true)
    protected ArrayOfCameraInfo cameras;
    @XmlElement(name = "DefaultRecordingTimeSeconds")
    protected Integer defaultRecordingTimeSeconds;
    @XmlElement(name = "Description", nillable = true)
    protected String description;
    @XmlElement(name = "Hardware", nillable = true)
    protected ArrayOfHardwareInfo hardware;
    @XmlElement(name = "HostName", nillable = true)
    protected String hostName;
    @XmlElement(name = "Inputs", nillable = true)
    protected ArrayOfInputInfo inputs;
    @XmlElement(name = "LastModified")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModified;
    @XmlElement(name = "MetadataDevices", nillable = true)
    protected ArrayOfMetadataDeviceInfo metadataDevices;
    @XmlElement(name = "Microphones", nillable = true)
    protected ArrayOfMicrophoneInfo microphones;
    @XmlElement(name = "Name", nillable = true)
    protected String name;
    @XmlElement(name = "Outputs", nillable = true)
    protected ArrayOfOutputInfo outputs;
    @XmlElement(name = "RecorderId")
    protected String recorderId;
    @XmlElement(name = "ServiceId")
    protected String serviceId;
    @XmlElement(name = "Speakers", nillable = true)
    protected ArrayOfSpeakerInfo speakers;
    @XmlElement(name = "TimeZoneName", nillable = true)
    protected String timeZoneName;
    @XmlElement(name = "WebServerUri", nillable = true)
    protected String webServerUri;
    @XmlElement(name = "XmlEncoding", nillable = true)
    protected String xmlEncoding;

    /**
     * Gets the value of the cameras property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCameraInfo }
     *     
     */
    public ArrayOfCameraInfo getCameras() {
        return cameras;
    }

    /**
     * Sets the value of the cameras property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCameraInfo }
     *     
     */
    public void setCameras(ArrayOfCameraInfo value) {
        this.cameras = value;
    }

    /**
     * Gets the value of the defaultRecordingTimeSeconds property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDefaultRecordingTimeSeconds() {
        return defaultRecordingTimeSeconds;
    }

    /**
     * Sets the value of the defaultRecordingTimeSeconds property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDefaultRecordingTimeSeconds(Integer value) {
        this.defaultRecordingTimeSeconds = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the hardware property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfHardwareInfo }
     *     
     */
    public ArrayOfHardwareInfo getHardware() {
        return hardware;
    }

    /**
     * Sets the value of the hardware property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfHardwareInfo }
     *     
     */
    public void setHardware(ArrayOfHardwareInfo value) {
        this.hardware = value;
    }

    /**
     * Gets the value of the hostName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the value of the hostName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostName(String value) {
        this.hostName = value;
    }

    /**
     * Gets the value of the inputs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInputInfo }
     *     
     */
    public ArrayOfInputInfo getInputs() {
        return inputs;
    }

    /**
     * Sets the value of the inputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInputInfo }
     *     
     */
    public void setInputs(ArrayOfInputInfo value) {
        this.inputs = value;
    }

    /**
     * Gets the value of the lastModified property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastModified() {
        return lastModified;
    }

    /**
     * Sets the value of the lastModified property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModified(XMLGregorianCalendar value) {
        this.lastModified = value;
    }

    /**
     * Gets the value of the metadataDevices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMetadataDeviceInfo }
     *     
     */
    public ArrayOfMetadataDeviceInfo getMetadataDevices() {
        return metadataDevices;
    }

    /**
     * Sets the value of the metadataDevices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMetadataDeviceInfo }
     *     
     */
    public void setMetadataDevices(ArrayOfMetadataDeviceInfo value) {
        this.metadataDevices = value;
    }

    /**
     * Gets the value of the microphones property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMicrophoneInfo }
     *     
     */
    public ArrayOfMicrophoneInfo getMicrophones() {
        return microphones;
    }

    /**
     * Sets the value of the microphones property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMicrophoneInfo }
     *     
     */
    public void setMicrophones(ArrayOfMicrophoneInfo value) {
        this.microphones = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the outputs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOutputInfo }
     *     
     */
    public ArrayOfOutputInfo getOutputs() {
        return outputs;
    }

    /**
     * Sets the value of the outputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOutputInfo }
     *     
     */
    public void setOutputs(ArrayOfOutputInfo value) {
        this.outputs = value;
    }

    /**
     * Gets the value of the recorderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecorderId() {
        return recorderId;
    }

    /**
     * Sets the value of the recorderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecorderId(String value) {
        this.recorderId = value;
    }

    /**
     * Gets the value of the serviceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * Sets the value of the serviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceId(String value) {
        this.serviceId = value;
    }

    /**
     * Gets the value of the speakers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSpeakerInfo }
     *     
     */
    public ArrayOfSpeakerInfo getSpeakers() {
        return speakers;
    }

    /**
     * Sets the value of the speakers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSpeakerInfo }
     *     
     */
    public void setSpeakers(ArrayOfSpeakerInfo value) {
        this.speakers = value;
    }

    /**
     * Gets the value of the timeZoneName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeZoneName() {
        return timeZoneName;
    }

    /**
     * Sets the value of the timeZoneName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeZoneName(String value) {
        this.timeZoneName = value;
    }

    /**
     * Gets the value of the webServerUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebServerUri() {
        return webServerUri;
    }

    /**
     * Sets the value of the webServerUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebServerUri(String value) {
        this.webServerUri = value;
    }

    /**
     * Gets the value of the xmlEncoding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlEncoding() {
        return xmlEncoding;
    }

    /**
     * Sets the value of the xmlEncoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlEncoding(String value) {
        this.xmlEncoding = value;
    }

}
