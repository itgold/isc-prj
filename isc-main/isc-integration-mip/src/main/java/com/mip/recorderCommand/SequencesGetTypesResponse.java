
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
 *         &amp;lt;element name="SequencesGetTypesResult" type="{http://video.net/2/XProtectCSRecorderCommand}ArrayOfSequenceType" minOccurs="0"/&amp;gt;
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
    "sequencesGetTypesResult"
})
@XmlRootElement(name = "SequencesGetTypesResponse")
public class SequencesGetTypesResponse {

    @XmlElement(name = "SequencesGetTypesResult")
    protected ArrayOfSequenceType sequencesGetTypesResult;

    /**
     * Gets the value of the sequencesGetTypesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSequenceType }
     *     
     */
    public ArrayOfSequenceType getSequencesGetTypesResult() {
        return sequencesGetTypesResult;
    }

    /**
     * Sets the value of the sequencesGetTypesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSequenceType }
     *     
     */
    public void setSequencesGetTypesResult(ArrayOfSequenceType value) {
        this.sequencesGetTypesResult = value;
    }

}
