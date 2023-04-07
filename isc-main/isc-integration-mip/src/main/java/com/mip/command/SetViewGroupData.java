
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
 *         &amp;lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="viewGroupId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="viewGroupdata" type="{http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server}ViewGroupInternalData" minOccurs="0"/&amp;gt;
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
    "token",
    "viewGroupId",
    "viewGroupdata"
})
@XmlRootElement(name = "SetViewGroupData")
public class SetViewGroupData {

    @XmlElement(nillable = true)
    protected String token;
    protected String viewGroupId;
    @XmlElement(nillable = true)
    protected ViewGroupInternalData viewGroupdata;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the viewGroupId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViewGroupId() {
        return viewGroupId;
    }

    /**
     * Sets the value of the viewGroupId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViewGroupId(String value) {
        this.viewGroupId = value;
    }

    /**
     * Gets the value of the viewGroupdata property.
     * 
     * @return
     *     possible object is
     *     {@link ViewGroupInternalData }
     *     
     */
    public ViewGroupInternalData getViewGroupdata() {
        return viewGroupdata;
    }

    /**
     * Sets the value of the viewGroupdata property.
     * 
     * @param value
     *     allowed object is
     *     {@link ViewGroupInternalData }
     *     
     */
    public void setViewGroupdata(ViewGroupInternalData value) {
        this.viewGroupdata = value;
    }

}
