
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for MetadataDeviceSecurityInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MetadataDeviceSecurityInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Browse" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ExportDatabase" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="GetSequences" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Live" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RetentionCreate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RetentionRemove" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RetentionView" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="RetrieveEdgeRecordings" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="StartRecording" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="StopRecording" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetadataDeviceSecurityInfo", propOrder = {
    "browse",
    "exportDatabase",
    "getSequences",
    "live",
    "retentionCreate",
    "retentionRemove",
    "retentionView",
    "retrieveEdgeRecordings",
    "startRecording",
    "stopRecording"
})
public class MetadataDeviceSecurityInfo {

    @XmlElement(name = "Browse")
    protected Boolean browse;
    @XmlElement(name = "ExportDatabase")
    protected Boolean exportDatabase;
    @XmlElement(name = "GetSequences")
    protected Boolean getSequences;
    @XmlElement(name = "Live")
    protected Boolean live;
    @XmlElement(name = "RetentionCreate")
    protected Boolean retentionCreate;
    @XmlElement(name = "RetentionRemove")
    protected Boolean retentionRemove;
    @XmlElement(name = "RetentionView")
    protected Boolean retentionView;
    @XmlElement(name = "RetrieveEdgeRecordings")
    protected Boolean retrieveEdgeRecordings;
    @XmlElement(name = "StartRecording")
    protected Boolean startRecording;
    @XmlElement(name = "StopRecording")
    protected Boolean stopRecording;

    /**
     * Gets the value of the browse property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBrowse() {
        return browse;
    }

    /**
     * Sets the value of the browse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBrowse(Boolean value) {
        this.browse = value;
    }

    /**
     * Gets the value of the exportDatabase property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExportDatabase() {
        return exportDatabase;
    }

    /**
     * Sets the value of the exportDatabase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExportDatabase(Boolean value) {
        this.exportDatabase = value;
    }

    /**
     * Gets the value of the getSequences property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGetSequences() {
        return getSequences;
    }

    /**
     * Sets the value of the getSequences property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGetSequences(Boolean value) {
        this.getSequences = value;
    }

    /**
     * Gets the value of the live property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLive() {
        return live;
    }

    /**
     * Sets the value of the live property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLive(Boolean value) {
        this.live = value;
    }

    /**
     * Gets the value of the retentionCreate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetentionCreate() {
        return retentionCreate;
    }

    /**
     * Sets the value of the retentionCreate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetentionCreate(Boolean value) {
        this.retentionCreate = value;
    }

    /**
     * Gets the value of the retentionRemove property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetentionRemove() {
        return retentionRemove;
    }

    /**
     * Sets the value of the retentionRemove property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetentionRemove(Boolean value) {
        this.retentionRemove = value;
    }

    /**
     * Gets the value of the retentionView property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetentionView() {
        return retentionView;
    }

    /**
     * Sets the value of the retentionView property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetentionView(Boolean value) {
        this.retentionView = value;
    }

    /**
     * Gets the value of the retrieveEdgeRecordings property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetrieveEdgeRecordings() {
        return retrieveEdgeRecordings;
    }

    /**
     * Sets the value of the retrieveEdgeRecordings property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetrieveEdgeRecordings(Boolean value) {
        this.retrieveEdgeRecordings = value;
    }

    /**
     * Gets the value of the startRecording property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStartRecording() {
        return startRecording;
    }

    /**
     * Sets the value of the startRecording property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStartRecording(Boolean value) {
        this.startRecording = value;
    }

    /**
     * Gets the value of the stopRecording property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStopRecording() {
        return stopRecording;
    }

    /**
     * Sets the value of the stopRecording property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStopRecording(Boolean value) {
        this.stopRecording = value;
    }

}
