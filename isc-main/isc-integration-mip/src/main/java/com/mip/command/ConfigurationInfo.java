
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ConfigurationInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ConfigurationInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="AlertTypeGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfAlertTypeGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AlertTypes" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfAlertTypeInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ApplicationAccess" type="{http://video.net/2/XProtectCSServerCommand}ApplicationSecurityInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AudioMessages" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfAudioMessageInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="BookmarkSettings" type="{http://video.net/2/XProtectCSServerCommand}BookmarkSettingInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CameraGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfCameraGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DeviceMappings" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfDeviceMappingInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EventTypeGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfEventTypeGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EventTypes" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfEventTypeInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="FailoverCheckInterval" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="InputGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfInputGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Licenses" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfLicenseInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MatrixMonitors" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfMatrixMonitorInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MetadataDeviceGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfMetadataDeviceGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MicrophoneGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfMicrophoneGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="OutputGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfOutputGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Recorders" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfRecorderInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RetentionSettings" type="{http://video.net/2/XProtectCSServerCommand}RetentionSettingInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ServerDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ServerId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ServerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ServerOptions" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfServerOption" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SpeakerGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfSpeakerGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SystemEventTypes" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfSystemEventTypeInfo" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigurationInfo", propOrder = {
    "alertTypeGroups",
    "alertTypes",
    "applicationAccess",
    "audioMessages",
    "bookmarkSettings",
    "cameraGroups",
    "deviceMappings",
    "eventTypeGroups",
    "eventTypes",
    "failoverCheckInterval",
    "inputGroups",
    "licenses",
    "matrixMonitors",
    "metadataDeviceGroups",
    "microphoneGroups",
    "outputGroups",
    "recorders",
    "retentionSettings",
    "serverDescription",
    "serverId",
    "serverName",
    "serverOptions",
    "speakerGroups",
    "systemEventTypes"
})
public class ConfigurationInfo {

    @XmlElement(name = "AlertTypeGroups", nillable = true)
    protected ArrayOfAlertTypeGroupInfo alertTypeGroups;
    @XmlElement(name = "AlertTypes", nillable = true)
    protected ArrayOfAlertTypeInfo alertTypes;
    @XmlElement(name = "ApplicationAccess", nillable = true)
    protected ApplicationSecurityInfo applicationAccess;
    @XmlElement(name = "AudioMessages", nillable = true)
    protected ArrayOfAudioMessageInfo audioMessages;
    @XmlElement(name = "BookmarkSettings", nillable = true)
    protected BookmarkSettingInfo bookmarkSettings;
    @XmlElement(name = "CameraGroups", nillable = true)
    protected ArrayOfCameraGroupInfo cameraGroups;
    @XmlElement(name = "DeviceMappings", nillable = true)
    protected ArrayOfDeviceMappingInfo deviceMappings;
    @XmlElement(name = "EventTypeGroups", nillable = true)
    protected ArrayOfEventTypeGroupInfo eventTypeGroups;
    @XmlElement(name = "EventTypes", nillable = true)
    protected ArrayOfEventTypeInfo eventTypes;
    @XmlElement(name = "FailoverCheckInterval")
    protected Integer failoverCheckInterval;
    @XmlElement(name = "InputGroups", nillable = true)
    protected ArrayOfInputGroupInfo inputGroups;
    @XmlElement(name = "Licenses", nillable = true)
    protected ArrayOfLicenseInfo licenses;
    @XmlElement(name = "MatrixMonitors", nillable = true)
    protected ArrayOfMatrixMonitorInfo matrixMonitors;
    @XmlElement(name = "MetadataDeviceGroups", nillable = true)
    protected ArrayOfMetadataDeviceGroupInfo metadataDeviceGroups;
    @XmlElement(name = "MicrophoneGroups", nillable = true)
    protected ArrayOfMicrophoneGroupInfo microphoneGroups;
    @XmlElement(name = "OutputGroups", nillable = true)
    protected ArrayOfOutputGroupInfo outputGroups;
    @XmlElement(name = "Recorders", nillable = true)
    protected ArrayOfRecorderInfo recorders;
    @XmlElement(name = "RetentionSettings", nillable = true)
    protected RetentionSettingInfo retentionSettings;
    @XmlElement(name = "ServerDescription", nillable = true)
    protected String serverDescription;
    @XmlElement(name = "ServerId")
    protected String serverId;
    @XmlElement(name = "ServerName", nillable = true)
    protected String serverName;
    @XmlElement(name = "ServerOptions", nillable = true)
    protected ArrayOfServerOption serverOptions;
    @XmlElement(name = "SpeakerGroups", nillable = true)
    protected ArrayOfSpeakerGroupInfo speakerGroups;
    @XmlElement(name = "SystemEventTypes", nillable = true)
    protected ArrayOfSystemEventTypeInfo systemEventTypes;

    /**
     * Gets the value of the alertTypeGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAlertTypeGroupInfo }
     *     
     */
    public ArrayOfAlertTypeGroupInfo getAlertTypeGroups() {
        return alertTypeGroups;
    }

    /**
     * Sets the value of the alertTypeGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAlertTypeGroupInfo }
     *     
     */
    public void setAlertTypeGroups(ArrayOfAlertTypeGroupInfo value) {
        this.alertTypeGroups = value;
    }

    /**
     * Gets the value of the alertTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAlertTypeInfo }
     *     
     */
    public ArrayOfAlertTypeInfo getAlertTypes() {
        return alertTypes;
    }

    /**
     * Sets the value of the alertTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAlertTypeInfo }
     *     
     */
    public void setAlertTypes(ArrayOfAlertTypeInfo value) {
        this.alertTypes = value;
    }

    /**
     * Gets the value of the applicationAccess property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationSecurityInfo }
     *     
     */
    public ApplicationSecurityInfo getApplicationAccess() {
        return applicationAccess;
    }

    /**
     * Sets the value of the applicationAccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationSecurityInfo }
     *     
     */
    public void setApplicationAccess(ApplicationSecurityInfo value) {
        this.applicationAccess = value;
    }

    /**
     * Gets the value of the audioMessages property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAudioMessageInfo }
     *     
     */
    public ArrayOfAudioMessageInfo getAudioMessages() {
        return audioMessages;
    }

    /**
     * Sets the value of the audioMessages property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAudioMessageInfo }
     *     
     */
    public void setAudioMessages(ArrayOfAudioMessageInfo value) {
        this.audioMessages = value;
    }

    /**
     * Gets the value of the bookmarkSettings property.
     * 
     * @return
     *     possible object is
     *     {@link BookmarkSettingInfo }
     *     
     */
    public BookmarkSettingInfo getBookmarkSettings() {
        return bookmarkSettings;
    }

    /**
     * Sets the value of the bookmarkSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookmarkSettingInfo }
     *     
     */
    public void setBookmarkSettings(BookmarkSettingInfo value) {
        this.bookmarkSettings = value;
    }

    /**
     * Gets the value of the cameraGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCameraGroupInfo }
     *     
     */
    public ArrayOfCameraGroupInfo getCameraGroups() {
        return cameraGroups;
    }

    /**
     * Sets the value of the cameraGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCameraGroupInfo }
     *     
     */
    public void setCameraGroups(ArrayOfCameraGroupInfo value) {
        this.cameraGroups = value;
    }

    /**
     * Gets the value of the deviceMappings property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDeviceMappingInfo }
     *     
     */
    public ArrayOfDeviceMappingInfo getDeviceMappings() {
        return deviceMappings;
    }

    /**
     * Sets the value of the deviceMappings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDeviceMappingInfo }
     *     
     */
    public void setDeviceMappings(ArrayOfDeviceMappingInfo value) {
        this.deviceMappings = value;
    }

    /**
     * Gets the value of the eventTypeGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEventTypeGroupInfo }
     *     
     */
    public ArrayOfEventTypeGroupInfo getEventTypeGroups() {
        return eventTypeGroups;
    }

    /**
     * Sets the value of the eventTypeGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEventTypeGroupInfo }
     *     
     */
    public void setEventTypeGroups(ArrayOfEventTypeGroupInfo value) {
        this.eventTypeGroups = value;
    }

    /**
     * Gets the value of the eventTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEventTypeInfo }
     *     
     */
    public ArrayOfEventTypeInfo getEventTypes() {
        return eventTypes;
    }

    /**
     * Sets the value of the eventTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEventTypeInfo }
     *     
     */
    public void setEventTypes(ArrayOfEventTypeInfo value) {
        this.eventTypes = value;
    }

    /**
     * Gets the value of the failoverCheckInterval property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFailoverCheckInterval() {
        return failoverCheckInterval;
    }

    /**
     * Sets the value of the failoverCheckInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFailoverCheckInterval(Integer value) {
        this.failoverCheckInterval = value;
    }

    /**
     * Gets the value of the inputGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInputGroupInfo }
     *     
     */
    public ArrayOfInputGroupInfo getInputGroups() {
        return inputGroups;
    }

    /**
     * Sets the value of the inputGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInputGroupInfo }
     *     
     */
    public void setInputGroups(ArrayOfInputGroupInfo value) {
        this.inputGroups = value;
    }

    /**
     * Gets the value of the licenses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfLicenseInfo }
     *     
     */
    public ArrayOfLicenseInfo getLicenses() {
        return licenses;
    }

    /**
     * Sets the value of the licenses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfLicenseInfo }
     *     
     */
    public void setLicenses(ArrayOfLicenseInfo value) {
        this.licenses = value;
    }

    /**
     * Gets the value of the matrixMonitors property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMatrixMonitorInfo }
     *     
     */
    public ArrayOfMatrixMonitorInfo getMatrixMonitors() {
        return matrixMonitors;
    }

    /**
     * Sets the value of the matrixMonitors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMatrixMonitorInfo }
     *     
     */
    public void setMatrixMonitors(ArrayOfMatrixMonitorInfo value) {
        this.matrixMonitors = value;
    }

    /**
     * Gets the value of the metadataDeviceGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMetadataDeviceGroupInfo }
     *     
     */
    public ArrayOfMetadataDeviceGroupInfo getMetadataDeviceGroups() {
        return metadataDeviceGroups;
    }

    /**
     * Sets the value of the metadataDeviceGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMetadataDeviceGroupInfo }
     *     
     */
    public void setMetadataDeviceGroups(ArrayOfMetadataDeviceGroupInfo value) {
        this.metadataDeviceGroups = value;
    }

    /**
     * Gets the value of the microphoneGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMicrophoneGroupInfo }
     *     
     */
    public ArrayOfMicrophoneGroupInfo getMicrophoneGroups() {
        return microphoneGroups;
    }

    /**
     * Sets the value of the microphoneGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMicrophoneGroupInfo }
     *     
     */
    public void setMicrophoneGroups(ArrayOfMicrophoneGroupInfo value) {
        this.microphoneGroups = value;
    }

    /**
     * Gets the value of the outputGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOutputGroupInfo }
     *     
     */
    public ArrayOfOutputGroupInfo getOutputGroups() {
        return outputGroups;
    }

    /**
     * Sets the value of the outputGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOutputGroupInfo }
     *     
     */
    public void setOutputGroups(ArrayOfOutputGroupInfo value) {
        this.outputGroups = value;
    }

    /**
     * Gets the value of the recorders property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRecorderInfo }
     *     
     */
    public ArrayOfRecorderInfo getRecorders() {
        return recorders;
    }

    /**
     * Sets the value of the recorders property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRecorderInfo }
     *     
     */
    public void setRecorders(ArrayOfRecorderInfo value) {
        this.recorders = value;
    }

    /**
     * Gets the value of the retentionSettings property.
     * 
     * @return
     *     possible object is
     *     {@link RetentionSettingInfo }
     *     
     */
    public RetentionSettingInfo getRetentionSettings() {
        return retentionSettings;
    }

    /**
     * Sets the value of the retentionSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetentionSettingInfo }
     *     
     */
    public void setRetentionSettings(RetentionSettingInfo value) {
        this.retentionSettings = value;
    }

    /**
     * Gets the value of the serverDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerDescription() {
        return serverDescription;
    }

    /**
     * Sets the value of the serverDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerDescription(String value) {
        this.serverDescription = value;
    }

    /**
     * Gets the value of the serverId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * Sets the value of the serverId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerId(String value) {
        this.serverId = value;
    }

    /**
     * Gets the value of the serverName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * Sets the value of the serverName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerName(String value) {
        this.serverName = value;
    }

    /**
     * Gets the value of the serverOptions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServerOption }
     *     
     */
    public ArrayOfServerOption getServerOptions() {
        return serverOptions;
    }

    /**
     * Sets the value of the serverOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServerOption }
     *     
     */
    public void setServerOptions(ArrayOfServerOption value) {
        this.serverOptions = value;
    }

    /**
     * Gets the value of the speakerGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSpeakerGroupInfo }
     *     
     */
    public ArrayOfSpeakerGroupInfo getSpeakerGroups() {
        return speakerGroups;
    }

    /**
     * Sets the value of the speakerGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSpeakerGroupInfo }
     *     
     */
    public void setSpeakerGroups(ArrayOfSpeakerGroupInfo value) {
        this.speakerGroups = value;
    }

    /**
     * Gets the value of the systemEventTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSystemEventTypeInfo }
     *     
     */
    public ArrayOfSystemEventTypeInfo getSystemEventTypes() {
        return systemEventTypes;
    }

    /**
     * Sets the value of the systemEventTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSystemEventTypeInfo }
     *     
     */
    public void setSystemEventTypes(ArrayOfSystemEventTypeInfo value) {
        this.systemEventTypes = value;
    }

}
