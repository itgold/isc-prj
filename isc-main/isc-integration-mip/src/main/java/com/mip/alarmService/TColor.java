
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for TColor complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TColor"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="A" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&amp;gt;
 *         &amp;lt;element name="R" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&amp;gt;
 *         &amp;lt;element name="G" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&amp;gt;
 *         &amp;lt;element name="B" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TColor", namespace = "urn:milestone-systems", propOrder = {
    "a",
    "r",
    "g",
    "b"
})
public class TColor {

    @XmlElement(name = "A")
    @XmlSchemaType(name = "unsignedByte")
    protected short a;
    @XmlElement(name = "R")
    @XmlSchemaType(name = "unsignedByte")
    protected short r;
    @XmlElement(name = "G")
    @XmlSchemaType(name = "unsignedByte")
    protected short g;
    @XmlElement(name = "B")
    @XmlSchemaType(name = "unsignedByte")
    protected short b;

    /**
     * Gets the value of the a property.
     * 
     */
    public short getA() {
        return a;
    }

    /**
     * Sets the value of the a property.
     * 
     */
    public void setA(short value) {
        this.a = value;
    }

    /**
     * Gets the value of the r property.
     * 
     */
    public short getR() {
        return r;
    }

    /**
     * Sets the value of the r property.
     * 
     */
    public void setR(short value) {
        this.r = value;
    }

    /**
     * Gets the value of the g property.
     * 
     */
    public short getG() {
        return g;
    }

    /**
     * Sets the value of the g property.
     * 
     */
    public void setG(short value) {
        this.g = value;
    }

    /**
     * Gets the value of the b property.
     * 
     */
    public short getB() {
        return b;
    }

    /**
     * Sets the value of the b property.
     * 
     */
    public void setB(short value) {
        this.b = value;
    }

}
