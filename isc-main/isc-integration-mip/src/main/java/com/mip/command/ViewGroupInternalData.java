
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ViewGroupInternalData complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ViewGroupInternalData"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="DataVersion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ViewGroupDataXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="XmlVersion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ViewGroupInternalData", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.Common.Proxy.Server", propOrder = {
    "dataVersion",
    "viewGroupDataXml",
    "xmlVersion"
})
public class ViewGroupInternalData {

    @XmlElement(name = "DataVersion")
    protected Integer dataVersion;
    @XmlElement(name = "ViewGroupDataXml", nillable = true)
    protected String viewGroupDataXml;
    @XmlElement(name = "XmlVersion")
    protected Integer xmlVersion;

    /**
     * Gets the value of the dataVersion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDataVersion() {
        return dataVersion;
    }

    /**
     * Sets the value of the dataVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDataVersion(Integer value) {
        this.dataVersion = value;
    }

    /**
     * Gets the value of the viewGroupDataXml property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViewGroupDataXml() {
        return viewGroupDataXml;
    }

    /**
     * Sets the value of the viewGroupDataXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViewGroupDataXml(String value) {
        this.viewGroupDataXml = value;
    }

    /**
     * Gets the value of the xmlVersion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getXmlVersion() {
        return xmlVersion;
    }

    /**
     * Sets the value of the xmlVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setXmlVersion(Integer value) {
        this.xmlVersion = value;
    }

}
