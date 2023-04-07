
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for GetEventLines2Params complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="GetEventLines2Params"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Page" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PageSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SearchGroup" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}SearchGroup" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SearchId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SearchOrders" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}SearchOrders" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TruePaging" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetEventLines2Params", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", propOrder = {
    "page",
    "pageSize",
    "searchGroup",
    "searchId",
    "searchOrders",
    "truePaging"
})
public class GetEventLines2Params {

    @XmlElement(name = "Page")
    protected Integer page;
    @XmlElement(name = "PageSize")
    protected Integer pageSize;
    @XmlElement(name = "SearchGroup", nillable = true)
    protected SearchGroup searchGroup;
    @XmlElement(name = "SearchId")
    protected String searchId;
    @XmlElement(name = "SearchOrders", nillable = true)
    protected SearchOrders searchOrders;
    @XmlElement(name = "TruePaging")
    protected Boolean truePaging;

    /**
     * Gets the value of the page property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPage(Integer value) {
        this.page = value;
    }

    /**
     * Gets the value of the pageSize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Sets the value of the pageSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPageSize(Integer value) {
        this.pageSize = value;
    }

    /**
     * Gets the value of the searchGroup property.
     * 
     * @return
     *     possible object is
     *     {@link SearchGroup }
     *     
     */
    public SearchGroup getSearchGroup() {
        return searchGroup;
    }

    /**
     * Sets the value of the searchGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchGroup }
     *     
     */
    public void setSearchGroup(SearchGroup value) {
        this.searchGroup = value;
    }

    /**
     * Gets the value of the searchId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchId() {
        return searchId;
    }

    /**
     * Sets the value of the searchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchId(String value) {
        this.searchId = value;
    }

    /**
     * Gets the value of the searchOrders property.
     * 
     * @return
     *     possible object is
     *     {@link SearchOrders }
     *     
     */
    public SearchOrders getSearchOrders() {
        return searchOrders;
    }

    /**
     * Sets the value of the searchOrders property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchOrders }
     *     
     */
    public void setSearchOrders(SearchOrders value) {
        this.searchOrders = value;
    }

    /**
     * Gets the value of the truePaging property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTruePaging() {
        return truePaging;
    }

    /**
     * Sets the value of the truePaging property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTruePaging(Boolean value) {
        this.truePaging = value;
    }

}
