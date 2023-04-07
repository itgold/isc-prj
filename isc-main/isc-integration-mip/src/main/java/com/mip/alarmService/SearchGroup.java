
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for SearchGroup complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SearchGroup"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="GroupOperator" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}LogicalOperator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SearchElements" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}ArrayOfSearchElement" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SearchGroups" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}ArrayOfSearchGroup" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchGroup", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", propOrder = {
    "groupOperator",
    "searchElements",
    "searchGroups"
})
public class SearchGroup {

    @XmlElement(name = "GroupOperator")
    @XmlSchemaType(name = "string")
    protected LogicalOperator groupOperator;
    @XmlElement(name = "SearchElements", nillable = true)
    protected ArrayOfSearchElement searchElements;
    @XmlElement(name = "SearchGroups", nillable = true)
    protected ArrayOfSearchGroup searchGroups;

    /**
     * Gets the value of the groupOperator property.
     * 
     * @return
     *     possible object is
     *     {@link LogicalOperator }
     *     
     */
    public LogicalOperator getGroupOperator() {
        return groupOperator;
    }

    /**
     * Sets the value of the groupOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogicalOperator }
     *     
     */
    public void setGroupOperator(LogicalOperator value) {
        this.groupOperator = value;
    }

    /**
     * Gets the value of the searchElements property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSearchElement }
     *     
     */
    public ArrayOfSearchElement getSearchElements() {
        return searchElements;
    }

    /**
     * Sets the value of the searchElements property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSearchElement }
     *     
     */
    public void setSearchElements(ArrayOfSearchElement value) {
        this.searchElements = value;
    }

    /**
     * Gets the value of the searchGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSearchGroup }
     *     
     */
    public ArrayOfSearchGroup getSearchGroups() {
        return searchGroups;
    }

    /**
     * Sets the value of the searchGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSearchGroup }
     *     
     */
    public void setSearchGroups(ArrayOfSearchGroup value) {
        this.searchGroups = value;
    }

}
