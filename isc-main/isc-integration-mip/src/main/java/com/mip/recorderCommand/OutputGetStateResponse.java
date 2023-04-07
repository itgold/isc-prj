
package com.mip.recorderCommand;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for anonymous complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="OutputGetStateResult" type="{http://video.net/2/XProtectCSRecorderCommand}IOState"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "outputGetStateResult"
})
@XmlRootElement(name = "OutputGetStateResponse")
public class OutputGetStateResponse {

    @XmlElement(name = "OutputGetStateResult", required = true)
    @XmlSchemaType(name = "string")
    protected IOState outputGetStateResult;

    /**
     * Gets the value of the outputGetStateResult property.
     * 
     * @return
     *     possible object is
     *     {@link IOState }
     *     
     */
    public IOState getOutputGetStateResult() {
        return outputGetStateResult;
    }

    /**
     * Sets the value of the outputGetStateResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IOState }
     *     
     */
    public void setOutputGetStateResult(IOState value) {
        this.outputGetStateResult = value;
    }

}
