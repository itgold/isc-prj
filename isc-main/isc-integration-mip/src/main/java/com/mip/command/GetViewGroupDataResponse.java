
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
 *         &amp;lt;element name="GetViewGroupDataResult" type="{http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server}ViewGroupInternal" minOccurs="0"/&amp;gt;
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
    "getViewGroupDataResult"
})
@XmlRootElement(name = "GetViewGroupDataResponse")
public class GetViewGroupDataResponse {

    @XmlElement(name = "GetViewGroupDataResult", nillable = true)
    protected ViewGroupInternal getViewGroupDataResult;

    /**
     * Gets the value of the getViewGroupDataResult property.
     * 
     * @return
     *     possible object is
     *     {@link ViewGroupInternal }
     *     
     */
    public ViewGroupInternal getGetViewGroupDataResult() {
        return getViewGroupDataResult;
    }

    /**
     * Sets the value of the getViewGroupDataResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ViewGroupInternal }
     *     
     */
    public void setGetViewGroupDataResult(ViewGroupInternal value) {
        this.getViewGroupDataResult = value;
    }

}
