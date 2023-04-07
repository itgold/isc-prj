
package com.mip.recorderCommand;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for anonymous complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="PTZGetSessionInfoResult" type="{http://video.net/2/XProtectCSRecorderCommand}PtzSessionInfo" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ptzGetSessionInfoResult"
})
@XmlRootElement(name = "PTZGetSessionInfoResponse")
public class PTZGetSessionInfoResponse {

    @XmlElement(name = "PTZGetSessionInfoResult")
    protected PtzSessionInfo ptzGetSessionInfoResult;

    /**
     * Gets the value of the ptzGetSessionInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link PtzSessionInfo }
     *     
     */
    public PtzSessionInfo getPTZGetSessionInfoResult() {
        return ptzGetSessionInfoResult;
    }

    /**
     * Sets the value of the ptzGetSessionInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link PtzSessionInfo }
     *     
     */
    public void setPTZGetSessionInfoResult(PtzSessionInfo value) {
        this.ptzGetSessionInfoResult = value;
    }

}
