
package com.mip.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &amp;lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="itemTypes" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="itemFilters" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}ArrayOfItemFilter" minOccurs="0"/&amp;gt;
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
    "path",
    "itemTypes",
    "itemFilters"
})
@XmlRootElement(name = "GetChildItemsHierarchy")
public class GetChildItemsHierarchy {

    @XmlElement(nillable = true)
    protected String path;
    @XmlElement(nillable = true)
    protected ArrayOfstring itemTypes;
    @XmlElement(nillable = true)
    protected ArrayOfItemFilter itemFilters;

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the itemTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfstring }
     *     
     */
    public ArrayOfstring getItemTypes() {
        return itemTypes;
    }

    /**
     * Sets the value of the itemTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfstring }
     *     
     */
    public void setItemTypes(ArrayOfstring value) {
        this.itemTypes = value;
    }

    /**
     * Gets the value of the itemFilters property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfItemFilter }
     *     
     */
    public ArrayOfItemFilter getItemFilters() {
        return itemFilters;
    }

    /**
     * Sets the value of the itemFilters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfItemFilter }
     *     
     */
    public void setItemFilters(ArrayOfItemFilter value) {
        this.itemFilters = value;
    }

}
