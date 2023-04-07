
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for BookmarkSettingInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="BookmarkSettingInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="DefaultPostTimeSec" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DefaultPreTimeSec" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookmarkSettingInfo", propOrder = {
    "defaultPostTimeSec",
    "defaultPreTimeSec"
})
public class BookmarkSettingInfo {

    @XmlElement(name = "DefaultPostTimeSec")
    protected Integer defaultPostTimeSec;
    @XmlElement(name = "DefaultPreTimeSec")
    protected Integer defaultPreTimeSec;

    /**
     * Gets the value of the defaultPostTimeSec property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDefaultPostTimeSec() {
        return defaultPostTimeSec;
    }

    /**
     * Sets the value of the defaultPostTimeSec property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDefaultPostTimeSec(Integer value) {
        this.defaultPostTimeSec = value;
    }

    /**
     * Gets the value of the defaultPreTimeSec property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDefaultPreTimeSec() {
        return defaultPreTimeSec;
    }

    /**
     * Sets the value of the defaultPreTimeSec property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDefaultPreTimeSec(Integer value) {
        this.defaultPreTimeSec = value;
    }

}
