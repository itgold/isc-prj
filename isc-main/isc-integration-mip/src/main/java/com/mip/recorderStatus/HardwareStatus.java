
package com.mip.recorderStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for HardwareStatus complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="HardwareStatus"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSRecorderStatus2}StatusBase"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="HardwareId" type="{http://microsoft.com/wsdl/types/}guid"/&amp;gt;
 *         &amp;lt;element name="IsChange" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *         &amp;lt;element name="Enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *         &amp;lt;element name="Started" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *         &amp;lt;element name="Error" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *         &amp;lt;element name="ErrorNotLicensed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *         &amp;lt;element name="ErrorNoConnection" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HardwareStatus", propOrder = {
    "hardwareId",
    "isChange",
    "enabled",
    "started",
    "error",
    "errorNotLicensed",
    "errorNoConnection"
})
public class HardwareStatus
    extends StatusBase
{

    @XmlElement(name = "HardwareId", required = true)
    protected String hardwareId;
    @XmlElement(name = "IsChange")
    protected boolean isChange;
    @XmlElement(name = "Enabled")
    protected boolean enabled;
    @XmlElement(name = "Started")
    protected boolean started;
    @XmlElement(name = "Error")
    protected boolean error;
    @XmlElement(name = "ErrorNotLicensed")
    protected boolean errorNotLicensed;
    @XmlElement(name = "ErrorNoConnection")
    protected boolean errorNoConnection;

    /**
     * Gets the value of the hardwareId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHardwareId() {
        return hardwareId;
    }

    /**
     * Sets the value of the hardwareId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHardwareId(String value) {
        this.hardwareId = value;
    }

    /**
     * Gets the value of the isChange property.
     * 
     */
    public boolean isIsChange() {
        return isChange;
    }

    /**
     * Sets the value of the isChange property.
     * 
     */
    public void setIsChange(boolean value) {
        this.isChange = value;
    }

    /**
     * Gets the value of the enabled property.
     * 
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     */
    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the started property.
     * 
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Sets the value of the started property.
     * 
     */
    public void setStarted(boolean value) {
        this.started = value;
    }

    /**
     * Gets the value of the error property.
     * 
     */
    public boolean isError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     */
    public void setError(boolean value) {
        this.error = value;
    }

    /**
     * Gets the value of the errorNotLicensed property.
     * 
     */
    public boolean isErrorNotLicensed() {
        return errorNotLicensed;
    }

    /**
     * Sets the value of the errorNotLicensed property.
     * 
     */
    public void setErrorNotLicensed(boolean value) {
        this.errorNotLicensed = value;
    }

    /**
     * Gets the value of the errorNoConnection property.
     * 
     */
    public boolean isErrorNoConnection() {
        return errorNoConnection;
    }

    /**
     * Sets the value of the errorNoConnection property.
     * 
     */
    public void setErrorNoConnection(boolean value) {
        this.errorNoConnection = value;
    }

}
