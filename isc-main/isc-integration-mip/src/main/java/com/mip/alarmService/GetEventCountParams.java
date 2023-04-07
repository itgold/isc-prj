
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for GetEventCountParams complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="GetEventCountParams"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="SearchGroup" type="{http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm}SearchGroup" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetEventCountParams", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Platform.Proxy.Alarm", propOrder = {
    "searchGroup"
})
public class GetEventCountParams {

    @XmlElement(name = "SearchGroup", nillable = true)
    protected SearchGroup searchGroup;

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

}
