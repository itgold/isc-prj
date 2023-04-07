
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
 *         &amp;lt;element name="MarkedDataCreateResult" type="{http://video.net/2/XProtectCSServerCommand}MarkedDataResult" minOccurs="0"/&amp;gt;
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
    "markedDataCreateResult"
})
@XmlRootElement(name = "MarkedDataCreateResponse")
public class MarkedDataCreateResponse {

    @XmlElement(name = "MarkedDataCreateResult", nillable = true)
    protected MarkedDataResult markedDataCreateResult;

    /**
     * Gets the value of the markedDataCreateResult property.
     * 
     * @return
     *     possible object is
     *     {@link MarkedDataResult }
     *     
     */
    public MarkedDataResult getMarkedDataCreateResult() {
        return markedDataCreateResult;
    }

    /**
     * Sets the value of the markedDataCreateResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkedDataResult }
     *     
     */
    public void setMarkedDataCreateResult(MarkedDataResult value) {
        this.markedDataCreateResult = value;
    }

}
