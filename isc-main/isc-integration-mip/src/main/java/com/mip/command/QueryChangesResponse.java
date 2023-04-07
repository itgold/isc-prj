
package com.mip.command;

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
 *         &amp;lt;element name="QueryChangesResult" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfChange" minOccurs="0"/&amp;gt;
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
    "queryChangesResult"
})
@XmlRootElement(name = "QueryChangesResponse")
public class QueryChangesResponse {

    @XmlElement(name = "QueryChangesResult", nillable = true)
    protected ArrayOfChange queryChangesResult;

    /**
     * Gets the value of the queryChangesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChange }
     *     
     */
    public ArrayOfChange getQueryChangesResult() {
        return queryChangesResult;
    }

    /**
     * Sets the value of the queryChangesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChange }
     *     
     */
    public void setQueryChangesResult(ArrayOfChange value) {
        this.queryChangesResult = value;
    }

}
