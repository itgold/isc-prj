
package com.mip.command;

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
 *         &amp;lt;element name="GetClientProfile3Result" type="{http://video.net/2/XProtectCSServerCommand}ClientProfile" minOccurs="0"/&amp;gt;
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
    "getClientProfile3Result"
})
@XmlRootElement(name = "GetClientProfile3Response")
public class GetClientProfile3Response {

    @XmlElement(name = "GetClientProfile3Result", nillable = true)
    protected ClientProfile getClientProfile3Result;

    /**
     * Gets the value of the getClientProfile3Result property.
     * 
     * @return
     *     possible object is
     *     {@link ClientProfile }
     *     
     */
    public ClientProfile getGetClientProfile3Result() {
        return getClientProfile3Result;
    }

    /**
     * Sets the value of the getClientProfile3Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientProfile }
     *     
     */
    public void setGetClientProfile3Result(ClientProfile value) {
        this.getClientProfile3Result = value;
    }

}
