
package com.mip.recorderCommand;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ClientCapabilities complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ClientCapabilities"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="PrivacyMask" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *         &amp;lt;element name="PrivacyMaskVersion" type="{http://www.w3.org/2001/XMLSchema}int"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientCapabilities", propOrder = {
    "privacyMask",
    "privacyMaskVersion"
})
public class ClientCapabilities {

    @XmlElement(name = "PrivacyMask")
    protected boolean privacyMask;
    @XmlElement(name = "PrivacyMaskVersion")
    protected int privacyMaskVersion;

    /**
     * Gets the value of the privacyMask property.
     * 
     */
    public boolean isPrivacyMask() {
        return privacyMask;
    }

    /**
     * Sets the value of the privacyMask property.
     * 
     */
    public void setPrivacyMask(boolean value) {
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

}
