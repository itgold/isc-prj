
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for MarkedDataResult complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MarkedDataResult"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="FaultDevices" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfFaultDevice" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MarkedData" type="{http://video.net/2/XProtectCSServerCommand}MarkedData" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Status" type="{http://schemas.datacontract.org/2004/07/VideoOS.WebService.Common.ServerCommandService}ResultStatus" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="WarningDevices" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfWarningDevice" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MarkedDataResult", propOrder = {
    "faultDevices",
    "markedData",
    "status",
    "warningDevices"
})
public class MarkedDataResult {

    @XmlElement(name = "FaultDevices", nillable = true)
    protected ArrayOfFaultDevice faultDevices;
    @XmlElement(name = "MarkedData", nillable = true)
    protected MarkedData markedData;
    @XmlElement(name = "Status")
    @XmlSchemaType(name = "string")
    protected ResultStatus status;
    @XmlElement(name = "WarningDevices", nillable = true)
    protected ArrayOfWarningDevice warningDevices;

    /**
     * Gets the value of the faultDevices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFaultDevice }
     *     
     */
    public ArrayOfFaultDevice getFaultDevices() {
        return faultDevices;
    }

    /**
     * Sets the value of the faultDevices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFaultDevice }
     *     
     */
    public void setFaultDevices(ArrayOfFaultDevice value) {
        this.faultDevices = value;
    }

    /**
     * Gets the value of the markedData property.
     * 
     * @return
     *     possible object is
     *     {@link MarkedData }
     *     
     */
    public MarkedData getMarkedData() {
        return markedData;
    }

    /**
     * Sets the value of the markedData property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkedData }
     *     
     */
    public void setMarkedData(MarkedData value) {
        this.markedData = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ResultStatus }
     *     
     */
    public ResultStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultStatus }
     *     
     */
    public void setStatus(ResultStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the warningDevices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWarningDevice }
     *     
     */
    public ArrayOfWarningDevice getWarningDevices() {
        return warningDevices;
    }

    /**
     * Sets the value of the warningDevices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWarningDevice }
     *     
     */
    public void setWarningDevices(ArrayOfWarningDevice value) {
        this.warningDevices = value;
    }

}
