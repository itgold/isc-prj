
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
 *         &amp;lt;element name="MarkedDataGetNewReferenceResult" type="{http://video.net/2/XProtectCSServerCommand}MarkedDataReference" minOccurs="0"/&amp;gt;
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
    "markedDataGetNewReferenceResult"
})
@XmlRootElement(name = "MarkedDataGetNewReferenceResponse")
public class MarkedDataGetNewReferenceResponse {

    @XmlElement(name = "MarkedDataGetNewReferenceResult", nillable = true)
    protected MarkedDataReference markedDataGetNewReferenceResult;

    /**
     * Gets the value of the markedDataGetNewReferenceResult property.
     * 
     * @return
     *     possible object is
     *     {@link MarkedDataReference }
     *     
     */
    public MarkedDataReference getMarkedDataGetNewReferenceResult() {
        return markedDataGetNewReferenceResult;
    }

    /**
     * Sets the value of the markedDataGetNewReferenceResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkedDataReference }
     *     
     */
    public void setMarkedDataGetNewReferenceResult(MarkedDataReference value) {
        this.markedDataGetNewReferenceResult = value;
    }

}
