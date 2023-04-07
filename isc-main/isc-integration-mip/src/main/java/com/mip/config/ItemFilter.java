
package com.mip.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ItemFilter complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ItemFilter"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="EnableFilter" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}EnableFilter" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ItemType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PropertyFilters" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}ArrayOfPropertyFilter" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemFilter", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", propOrder = {
    "enableFilter",
    "itemType",
    "propertyFilters"
})
public class ItemFilter {

    @XmlElement(name = "EnableFilter")
    @XmlSchemaType(name = "string")
    protected EnableFilter enableFilter;
    @XmlElement(name = "ItemType", nillable = true)
    protected String itemType;
    @XmlElement(name = "PropertyFilters", nillable = true)
    protected ArrayOfPropertyFilter propertyFilters;

    /**
     * Gets the value of the enableFilter property.
     * 
     * @return
     *     possible object is
     *     {@link EnableFilter }
     *     
     */
    public EnableFilter getEnableFilter() {
        return enableFilter;
    }

    /**
     * Sets the value of the enableFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnableFilter }
     *     
     */
    public void setEnableFilter(EnableFilter value) {
        this.enableFilter = value;
    }

    /**
     * Gets the value of the itemType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * Sets the value of the itemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemType(String value) {
        this.itemType = value;
    }

    /**
     * Gets the value of the propertyFilters property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPropertyFilter }
     *     
     */
    public ArrayOfPropertyFilter getPropertyFilters() {
        return propertyFilters;
    }

    /**
     * Sets the value of the propertyFilters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPropertyFilter }
     *     
     */
    public void setPropertyFilters(ArrayOfPropertyFilter value) {
        this.propertyFilters = value;
    }

}
