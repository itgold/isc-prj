
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for Reference complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="Reference"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="FQID" type="{urn:milestone-systems}FQID" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reference", namespace = "urn:milestone-systems", propOrder = {
    "fqid"
})
public class Reference {

    @XmlElement(name = "FQID", nillable = true)
    protected FQID fqid;

    /**
     * Gets the value of the fqid property.
     * 
     * @return
     *     possible object is
     *     {@link FQID }
     *     
     */
    public FQID getFQID() {
        return fqid;
    }

    /**
     * Sets the value of the fqid property.
     * 
     * @param value
     *     allowed object is
     *     {@link FQID }
     *     
     */
    public void setFQID(FQID value) {
        this.fqid = value;
    }

}
