
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for OutputInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="OutputInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}DeviceInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="OutputSecurity" type="{http://video.net/2/XProtectCSServerCommand}OutputSecurityInfo" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputInfo", propOrder = {
    "outputSecurity"
})
public class OutputInfo
    extends DeviceInfo
{

    @XmlElement(name = "OutputSecurity", nillable = true)
    protected OutputSecurityInfo outputSecurity;

    /**
     * Gets the value of the outputSecurity property.
     * 
     * @return
     *     possible object is
     *     {@link OutputSecurityInfo }
     *     
     */
    public OutputSecurityInfo getOutputSecurity() {
        return outputSecurity;
    }

    /**
     * Sets the value of the outputSecurity property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputSecurityInfo }
     *     
     */
    public void setOutputSecurity(OutputSecurityInfo value) {
        this.outputSecurity = value;
    }

}
