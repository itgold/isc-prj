
package com.mip.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ErrorResult complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ErrorResult"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="ErrorProperty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ErrorText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ErrorTextId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorResult", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", propOrder = {
    "errorProperty",
    "errorText",
    "errorTextId"
})
public class ErrorResult {

    @XmlElement(name = "ErrorProperty", nillable = true)
    protected String errorProperty;
    @XmlElement(name = "ErrorText", nillable = true)
    protected String errorText;
    @XmlElement(name = "ErrorTextId", nillable = true)
    protected String errorTextId;

    /**
     * Gets the value of the errorProperty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorProperty() {
        return errorProperty;
    }

    /**
     * Sets the value of the errorProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorProperty(String value) {
        this.errorProperty = value;
    }

    /**
     * Gets the value of the errorText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorText() {
        return errorText;
    }

    /**
     * Sets the value of the errorText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorText(String value) {
        this.errorText = value;
    }

    /**
     * Gets the value of the errorTextId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorTextId() {
        return errorTextId;
    }

    /**
     * Sets the value of the errorTextId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorTextId(String value) {
        this.errorTextId = value;
    }

}
