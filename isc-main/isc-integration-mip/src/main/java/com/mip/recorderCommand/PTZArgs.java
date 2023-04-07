
package com.mip.recorderCommand;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for PTZArgs complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PTZArgs"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="movement" type="{http://video.net/2/XProtectCSRecorderCommand}PTZPairDouble" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="speed" type="{http://video.net/2/XProtectCSRecorderCommand}PTZPairDouble" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="automatic" type="{http://video.net/2/XProtectCSRecorderCommand}PTZPairBoolean" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Normalized" type="{http://www.w3.org/2001/XMLSchema}boolean"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PTZArgs", propOrder = {
    "movement",
    "speed",
    "automatic",
    "normalized"
})
public class PTZArgs {

    protected List<PTZPairDouble> movement;
    protected List<PTZPairDouble> speed;
    protected List<PTZPairBoolean> automatic;
    @XmlElement(name = "Normalized")
    protected boolean normalized;

    /**
     * Gets the value of the movement property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the movement property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getMovement().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PTZPairDouble }
     * 
     * 
     */
    public List<PTZPairDouble> getMovement() {
        if (movement == null) {
            movement = new ArrayList<PTZPairDouble>();
        }
        return this.movement;
    }

    /**
     * Gets the value of the speed property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the speed property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSpeed().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PTZPairDouble }
     * 
     * 
     */
    public List<PTZPairDouble> getSpeed() {
        if (speed == null) {
            speed = new ArrayList<PTZPairDouble>();
        }
        return this.speed;
    }

    /**
     * Gets the value of the automatic property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the automatic property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAutomatic().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PTZPairBoolean }
     * 
     * 
     */
    public List<PTZPairBoolean> getAutomatic() {
        if (automatic == null) {
            automatic = new ArrayList<PTZPairBoolean>();
        }
        return this.automatic;
    }

    /**
     * Gets the value of the normalized property.
     * 
     */
    public boolean isNormalized() {
        return normalized;
    }

    /**
     * Sets the value of the normalized property.
     * 
     */
    public void setNormalized(boolean value) {
        this.normalized = value;
    }

}
