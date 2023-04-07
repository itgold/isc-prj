
package com.mip.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ValidateResult complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ValidateResult"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="ErrorResults" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}ArrayOfErrorResult" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ResultItem" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}ConfigurationItem" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ValidatedOk" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateResult", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", propOrder = {
    "errorResults",
    "resultItem",
    "validatedOk"
})
public class ValidateResult {

    @XmlElement(name = "ErrorResults", nillable = true)
    protected ArrayOfErrorResult errorResults;
    @XmlElement(name = "ResultItem", nillable = true)
    protected ConfigurationItem resultItem;
    @XmlElement(name = "ValidatedOk")
    protected Boolean validatedOk;

    /**
     * Gets the value of the errorResults property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfErrorResult }
     *     
     */
    public ArrayOfErrorResult getErrorResults() {
        return errorResults;
    }

    /**
     * Sets the value of the errorResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfErrorResult }
     *     
     */
    public void setErrorResults(ArrayOfErrorResult value) {
        this.errorResults = value;
    }

    /**
     * Gets the value of the resultItem property.
     * 
     * @return
     *     possible object is
     *     {@link ConfigurationItem }
     *     
     */
    public ConfigurationItem getResultItem() {
        return resultItem;
    }

    /**
     * Sets the value of the resultItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigurationItem }
     *     
     */
    public void setResultItem(ConfigurationItem value) {
        this.resultItem = value;
    }

    /**
     * Gets the value of the validatedOk property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValidatedOk() {
        return validatedOk;
    }

    /**
     * Sets the value of the validatedOk property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValidatedOk(Boolean value) {
        this.validatedOk = value;
    }

}
