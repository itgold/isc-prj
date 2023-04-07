
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ViewGroupInternal complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ViewGroupInternal"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="ViewGroupData" type="{http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server}ViewGroupInternalData" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ViewGroupInfo" type="{http://video.net/2/XProtectCSServerCommand}ViewGroupInfo" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ViewGroupInternal", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", propOrder = {
    "viewGroupData",
    "viewGroupInfo"
})
public class ViewGroupInternal {

    @XmlElement(name = "ViewGroupData", nillable = true)
    protected ViewGroupInternalData viewGroupData;
    @XmlElement(name = "ViewGroupInfo", nillable = true)
    protected ViewGroupInfo viewGroupInfo;

    /**
     * Gets the value of the viewGroupData property.
     * 
     * @return
     *     possible object is
     *     {@link ViewGroupInternalData }
     *     
     */
    public ViewGroupInternalData getViewGroupData() {
        return viewGroupData;
    }

    /**
     * Sets the value of the viewGroupData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ViewGroupInternalData }
     *     
     */
    public void setViewGroupData(ViewGroupInternalData value) {
        this.viewGroupData = value;
    }

    /**
     * Gets the value of the viewGroupInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ViewGroupInfo }
     *     
     */
    public ViewGroupInfo getViewGroupInfo() {
        return viewGroupInfo;
    }

    /**
     * Sets the value of the viewGroupInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ViewGroupInfo }
     *     
     */
    public void setViewGroupInfo(ViewGroupInfo value) {
        this.viewGroupInfo = value;
    }

}
