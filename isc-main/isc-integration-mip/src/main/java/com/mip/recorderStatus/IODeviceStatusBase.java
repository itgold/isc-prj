
package com.mip.recorderStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for IODeviceStatusBase complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="IODeviceStatusBase"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSRecorderStatus2}DeviceStatusBase"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="State" type="{http://video.net/2/XProtectCSRecorderStatus2}IOState"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IODeviceStatusBase", propOrder = {
    "state"
})
@XmlSeeAlso({
    OutputDeviceStatus.class,
    InputDeviceStatus.class
})
public class IODeviceStatusBase
    extends DeviceStatusBase
{

    @XmlElement(name = "State", required = true)
    @XmlSchemaType(name = "string")
    protected IOState state;

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link IOState }
     *     
     */
    public IOState getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link IOState }
     *     
     */
    public void setState(IOState value) {
        this.state = value;
    }

}
