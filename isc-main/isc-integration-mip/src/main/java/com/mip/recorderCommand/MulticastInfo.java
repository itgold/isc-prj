
package com.mip.recorderCommand;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for MulticastInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MulticastInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="DeviceId" type="{http://microsoft.com/wsdl/types/}guid"/&amp;gt;
 *         &amp;lt;element name="IPAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ServerPort" type="{http://www.w3.org/2001/XMLSchema}int"/&amp;gt;
 *         &amp;lt;element name="ClientPort" type="{http://www.w3.org/2001/XMLSchema}int"/&amp;gt;
 *         &amp;lt;element name="KeepAliveInterval" type="{http://video.net/2/XProtectCSRecorderCommand}TimeDuration" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Encrypted" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *         &amp;lt;element name="MulticastSessionId" type="{http://microsoft.com/wsdl/types/}guid"/&amp;gt;
 *         &amp;lt;element name="SSRC" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&amp;gt;
 *         &amp;lt;element name="PrivacyMask" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PrivacyMaskVersion" type="{http://www.w3.org/2001/XMLSchema}int"/&amp;gt;
 *         &amp;lt;element name="Success" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MulticastInfo", propOrder = {
    "deviceId",
    "ipAddress",
    "serverPort",
    "clientPort",
    "keepAliveInterval",
    "encrypted",
    "multicastSessionId",
    "ssrc",
    "privacyMask",
    "privacyMaskVersion",
    "success"
})
public class MulticastInfo {

    @XmlElement(name = "DeviceId", required = true)
    protected String deviceId;
    @XmlElement(name = "IPAddress")
    protected String ipAddress;
    @XmlElement(name = "ServerPort")
    protected int serverPort;
    @XmlElement(name = "ClientPort")
    protected int clientPort;
    @XmlElement(name = "KeepAliveInterval")
    protected TimeDuration keepAliveInterval;
    @XmlElement(name = "Encrypted")
    protected boolean encrypted;
    @XmlElement(name = "MulticastSessionId", required = true)
    protected String multicastSessionId;
    @XmlElement(name = "SSRC")
    @XmlSchemaType(name = "unsignedInt")
    protected long ssrc;
    @XmlElement(name = "PrivacyMask")
    protected String privacyMask;
    @XmlElement(name = "PrivacyMaskVersion")
    protected int privacyMaskVersion;
    @XmlElement(name = "Success")
    protected boolean success;

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
     * Gets the value of the ipAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIPAddress() {
        return ipAddress;
    }

    /**
     * Sets the value of the ipAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIPAddress(String value) {
        this.ipAddress = value;
    }

    /**
     * Gets the value of the serverPort property.
     * 
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Sets the value of the serverPort property.
     * 
     */
    public void setServerPort(int value) {
        this.serverPort = value;
    }

    /**
     * Gets the value of the clientPort property.
     * 
     */
    public int getClientPort() {
        return clientPort;
    }

    /**
     * Sets the value of the clientPort property.
     * 
     */
    public void setClientPort(int value) {
        this.clientPort = value;
    }

    /**
     * Gets the value of the keepAliveInterval property.
     * 
     * @return
     *     possible object is
     *     {@link TimeDuration }
     *     
     */
    public TimeDuration getKeepAliveInterval() {
        return keepAliveInterval;
    }

    /**
     * Sets the value of the keepAliveInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeDuration }
     *     
     */
    public void setKeepAliveInterval(TimeDuration value) {
        this.keepAliveInterval = value;
    }

    /**
     * Gets the value of the encrypted property.
     * 
     */
    public boolean isEncrypted() {
        return encrypted;
    }

    /**
     * Sets the value of the encrypted property.
     * 
     */
    public void setEncrypted(boolean value) {
        this.encrypted = value;
    }

    /**
     * Gets the value of the multicastSessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMulticastSessionId() {
        return multicastSessionId;
    }

    /**
     * Sets the value of the multicastSessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMulticastSessionId(String value) {
        this.multicastSessionId = value;
    }

    /**
     * Gets the value of the ssrc property.
     * 
     */
    public long getSSRC() {
        return ssrc;
    }

    /**
     * Sets the value of the ssrc property.
     * 
     */
    public void setSSRC(long value) {
        this.ssrc = value;
    }

    /**
     * Gets the value of the privacyMask property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrivacyMask() {
        return privacyMask;
    }

    /**
     * Sets the value of the privacyMask property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrivacyMask(String value) {
        this.privacyMask = value;
    }

    /**
     * Gets the value of the privacyMaskVersion property.
     * 
     */
    public int getPrivacyMaskVersion() {
        return privacyMaskVersion;
    }

    /**
     * Sets the value of the privacyMaskVersion property.
     * 
     */
    public void setPrivacyMaskVersion(int value) {
        this.privacyMaskVersion = value;
    }

    /**
     * Gets the value of the success property.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

}
