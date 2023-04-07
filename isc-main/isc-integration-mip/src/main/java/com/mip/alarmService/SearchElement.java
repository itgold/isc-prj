
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SearchElement complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SearchElement"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="SearchOperator" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}SearchOperator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SearchTarget" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SearchValue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchElement", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", propOrder = {
    "searchOperator",
    "searchTarget",
    "searchValue"
})
public class SearchElement {

    @XmlElement(name = "SearchOperator")
    @XmlSchemaType(name = "string")
    protected SearchOperator searchOperator;
    @XmlElement(name = "SearchTarget", nillable = true)
    protected String searchTarget;
    @XmlElement(name = "SearchValue", nillable = true)
    protected Object searchValue;

    /**
     * Gets the value of the searchOperator property.
     * 
     * @return
     *     possible object is
     *     {@link SearchOperator }
     *     
     */
    public SearchOperator getSearchOperator() {
        return searchOperator;
    }

    /**
     * Sets the value of the searchOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchOperator }
     *     
     */
    public void setSearchOperator(SearchOperator value) {
        this.searchOperator = value;
    }

    /**
     * Gets the value of the searchTarget property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchTarget() {
        return searchTarget;
    }

    /**
     * Sets the value of the searchTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchTarget(String value) {
        this.searchTarget = value;
    }

    /**
     * Gets the value of the searchValue property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getSearchValue() {
        return searchValue;
    }

    /**
     * Sets the value of the searchValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setSearchValue(Object value) {
        this.searchValue = value;
    }

}
