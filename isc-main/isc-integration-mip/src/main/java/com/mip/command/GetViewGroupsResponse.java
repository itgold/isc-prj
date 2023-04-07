
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
 *         &amp;lt;element name="GetViewGroupsResult" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfViewGroupInfo" minOccurs="0"/&amp;gt;
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
    "getViewGroupsResult"
})
@XmlRootElement(name = "GetViewGroupsResponse")
public class GetViewGroupsResponse {

    @XmlElement(name = "GetViewGroupsResult", nillable = true)
    protected ArrayOfViewGroupInfo getViewGroupsResult;

    /**
     * Gets the value of the getViewGroupsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfViewGroupInfo }
     *     
     */
    public ArrayOfViewGroupInfo getGetViewGroupsResult() {
        return getViewGroupsResult;
    }

    /**
     * Sets the value of the getViewGroupsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfViewGroupInfo }
     *     
     */
    public void setGetViewGroupsResult(ArrayOfViewGroupInfo value) {
        this.getViewGroupsResult = value;
    }

}
