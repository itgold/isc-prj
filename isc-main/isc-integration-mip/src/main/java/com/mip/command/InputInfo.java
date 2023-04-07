
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for InputInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="InputInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}DeviceInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="InputSecurity" type="{http://video.net/2/XProtectCSServerCommand}InputSecurityInfo" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputInfo", propOrder = {
    "inputSecurity"
})
public class InputInfo
    extends DeviceInfo
{

    @XmlElement(name = "InputSecurity", nillable = true)
    protected InputSecurityInfo inputSecurity;

    /**
     * Gets the value of the inputSecurity property.
     * 
     * @return
     *     possible object is
     *     {@link InputSecurityInfo }
     *     
     */
    public InputSecurityInfo getInputSecurity() {
        return inputSecurity;
    }

    /**
     * Sets the value of the inputSecurity property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputSecurityInfo }
     *     
     */
    public void setInputSecurity(InputSecurityInfo value) {
        this.inputSecurity = value;
    }

}
