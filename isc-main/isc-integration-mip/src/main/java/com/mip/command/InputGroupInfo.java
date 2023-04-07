
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for InputGroupInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="InputGroupInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}GroupInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="InputGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfInputGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Inputs" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputGroupInfo", propOrder = {
    "inputGroups",
    "inputs"
})
public class InputGroupInfo
    extends GroupInfo
{

    @XmlElement(name = "InputGroups", nillable = true)
    protected ArrayOfInputGroupInfo inputGroups;
    @XmlElement(name = "Inputs", nillable = true)
    protected ArrayOfguid inputs;

    /**
     * Gets the value of the inputGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInputGroupInfo }
     *     
     */
    public ArrayOfInputGroupInfo getInputGroups() {
        return inputGroups;
    }

    /**
     * Sets the value of the inputGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInputGroupInfo }
     *     
     */
    public void setInputGroups(ArrayOfInputGroupInfo value) {
        this.inputGroups = value;
    }

    /**
     * Gets the value of the inputs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getInputs() {
        return inputs;
    }

    /**
     * Sets the value of the inputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setInputs(ArrayOfguid value) {
        this.inputs = value;
    }

}
