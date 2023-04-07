
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for IpixInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="IpixInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CeilingMounted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Hemisphere" type="{http://video.net/2/XProtectCSServerCommand}HemisphereInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Homeposition" type="{http://video.net/2/XProtectCSServerCommand}PositionInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IpixEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IpixInfo", propOrder = {
    "ceilingMounted",
    "hemisphere",
    "homeposition",
    "ipixEnabled"
})
public class IpixInfo {

    @XmlElement(name = "CeilingMounted")
    protected Boolean ceilingMounted;
    @XmlElement(name = "Hemisphere", nillable = true)
    protected HemisphereInfo hemisphere;
    @XmlElement(name = "Homeposition", nillable = true)
    protected PositionInfo homeposition;
    @XmlElement(name = "IpixEnabled")
    protected Boolean ipixEnabled;

    /**
     * Gets the value of the ceilingMounted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCeilingMounted() {
        return ceilingMounted;
    }

    /**
     * Sets the value of the ceilingMounted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCeilingMounted(Boolean value) {
        this.ceilingMounted = value;
    }

    /**
     * Gets the value of the hemisphere property.
     * 
     * @return
     *     possible object is
     *     {@link HemisphereInfo }
     *     
     */
    public HemisphereInfo getHemisphere() {
        return hemisphere;
    }

    /**
     * Sets the value of the hemisphere property.
     * 
     * @param value
     *     allowed object is
     *     {@link HemisphereInfo }
     *     
     */
    public void setHemisphere(HemisphereInfo value) {
        this.hemisphere = value;
    }

    /**
     * Gets the value of the homeposition property.
     * 
     * @return
     *     possible object is
     *     {@link PositionInfo }
     *     
     */
    public PositionInfo getHomeposition() {
        return homeposition;
    }

    /**
     * Sets the value of the homeposition property.
     * 
     * @param value
     *     allowed object is
     *     {@link PositionInfo }
     *     
     */
    public void setHomeposition(PositionInfo value) {
        this.homeposition = value;
    }

    /**
     * Gets the value of the ipixEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIpixEnabled() {
        return ipixEnabled;
    }

    /**
     * Sets the value of the ipixEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIpixEnabled(Boolean value) {
        this.ipixEnabled = value;
    }

}
