
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
 *         &amp;lt;element name="SequencesGetAroundWithSpanResult" type="{http://video.net/2/XProtectCSRecorderCommand}ArrayOfSequenceEntry" minOccurs="0"/&amp;gt;
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
    "sequencesGetAroundWithSpanResult"
})
@XmlRootElement(name = "SequencesGetAroundWithSpanResponse")
public class SequencesGetAroundWithSpanResponse {

    @XmlElement(name = "SequencesGetAroundWithSpanResult")
    protected ArrayOfSequenceEntry sequencesGetAroundWithSpanResult;

    /**
     * Gets the value of the sequencesGetAroundWithSpanResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSequenceEntry }
     *     
     */
    public ArrayOfSequenceEntry getSequencesGetAroundWithSpanResult() {
        return sequencesGetAroundWithSpanResult;
    }

    /**
     * Sets the value of the sequencesGetAroundWithSpanResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSequenceEntry }
     *     
     */
    public void setSequencesGetAroundWithSpanResult(ArrayOfSequenceEntry value) {
        this.sequencesGetAroundWithSpanResult = value;
    }

}
