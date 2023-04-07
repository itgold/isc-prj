
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for OutputGroupInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="OutputGroupInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}GroupInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="OutputGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfOutputGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Outputs" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputGroupInfo", propOrder = {
    "outputGroups",
    "outputs"
})
public class OutputGroupInfo
    extends GroupInfo
{

    @XmlElement(name = "OutputGroups", nillable = true)
    protected ArrayOfOutputGroupInfo outputGroups;
    @XmlElement(name = "Outputs", nillable = true)
    protected ArrayOfguid outputs;

    /**
     * Gets the value of the outputGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOutputGroupInfo }
     *     
     */
    public ArrayOfOutputGroupInfo getOutputGroups() {
        return outputGroups;
    }

    /**
     * Sets the value of the outputGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOutputGroupInfo }
     *     
     */
    public void setOutputGroups(ArrayOfOutputGroupInfo value) {
        this.outputGroups = value;
    }

    /**
     * Gets the value of the outputs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getOutputs() {
        return outputs;
    }

    /**
     * Sets the value of the outputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setOutputs(ArrayOfguid value) {
        this.outputs = value;
    }

}
