
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
 *         &amp;lt;element name="itemFilter" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}ItemFilter" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="maxResult" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
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
    "itemFilter",
    "maxResult"
})
@XmlRootElement(name = "QueryItems")
public class QueryItems {

    @XmlElement(nillable = true)
    protected ItemFilter itemFilter;
    protected Integer maxResult;

    /**
     * Gets the value of the itemFilter property.
     * 
     * @return
     *     possible object is
     *     {@link ItemFilter }
     *     
     */
    public ItemFilter getItemFilter() {
        return itemFilter;
    }

    /**
     * Sets the value of the itemFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemFilter }
     *     
     */
    public void setItemFilter(ItemFilter value) {
        this.itemFilter = value;
    }

    /**
     * Gets the value of the maxResult property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxResult() {
        return maxResult;
    }

    /**
     * Sets the value of the maxResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxResult(Integer value) {
        this.maxResult = value;
    }

}
