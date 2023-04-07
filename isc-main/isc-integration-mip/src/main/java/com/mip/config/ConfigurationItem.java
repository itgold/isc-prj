
package com.mip.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ConfigurationItem complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ConfigurationItem"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Children" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}ArrayOfConfigurationItem" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ChildrenFilled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="EnableProperty" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}EnablePropertyInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ItemCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ItemType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MethodIds" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ParentPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Properties" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}ArrayOfProperty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ServerCookie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SortKey" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="UIHint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigurationItem", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", propOrder = {
    "children",
    "childrenFilled",
    "displayName",
    "enableProperty",
    "itemCategory",
    "itemType",
    "methodIds",
    "parentPath",
    "path",
    "properties",
    "serverCookie",
    "sortKey",
    "uiHint"
})
public class ConfigurationItem {

    @XmlElement(name = "Children", nillable = true)
    protected ArrayOfConfigurationItem children;
    @XmlElement(name = "ChildrenFilled")
    protected Boolean childrenFilled;
    @XmlElement(name = "DisplayName", nillable = true)
    protected String displayName;
    @XmlElement(name = "EnableProperty", nillable = true)
    protected EnablePropertyInfo enableProperty;
    @XmlElement(name = "ItemCategory", nillable = true)
    protected String itemCategory;
    @XmlElement(name = "ItemType", nillable = true)
    protected String itemType;
    @XmlElement(name = "MethodIds", nillable = true)
    protected ArrayOfstring methodIds;
    @XmlElement(name = "ParentPath", nillable = true)
    protected String parentPath;
    @XmlElement(name = "Path", nillable = true)
    protected String path;
    @XmlElement(name = "Properties", nillable = true)
    protected ArrayOfProperty properties;
    @XmlElement(name = "ServerCookie", nillable = true)
    protected String serverCookie;
    @XmlElement(name = "SortKey")
    protected Integer sortKey;
    @XmlElement(name = "UIHint", nillable = true)
    protected String uiHint;

    /**
     * Gets the value of the children property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfConfigurationItem }
     *     
     */
    public ArrayOfConfigurationItem getChildren() {
        return children;
    }

    /**
     * Sets the value of the children property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfConfigurationItem }
     *     
     */
    public void setChildren(ArrayOfConfigurationItem value) {
        this.children = value;
    }

    /**
     * Gets the value of the childrenFilled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isChildrenFilled() {
        return childrenFilled;
    }

    /**
     * Sets the value of the childrenFilled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setChildrenFilled(Boolean value) {
        this.childrenFilled = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the enableProperty property.
     * 
     * @return
     *     possible object is
     *     {@link EnablePropertyInfo }
     *     
     */
    public EnablePropertyInfo getEnableProperty() {
        return enableProperty;
    }

    /**
     * Sets the value of the enableProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnablePropertyInfo }
     *     
     */
    public void setEnableProperty(EnablePropertyInfo value) {
        this.enableProperty = value;
    }

    /**
     * Gets the value of the itemCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemCategory() {
        return itemCategory;
    }

    /**
     * Sets the value of the itemCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemCategory(String value) {
        this.itemCategory = value;
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
     * Gets the value of the methodIds property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfstring }
     *     
     */
    public ArrayOfstring getMethodIds() {
        return methodIds;
    }

    /**
     * Sets the value of the methodIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfstring }
     *     
     */
    public void setMethodIds(ArrayOfstring value) {
        this.methodIds = value;
    }

    /**
     * Gets the value of the parentPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentPath() {
        return parentPath;
    }

    /**
     * Sets the value of the parentPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentPath(String value) {
        this.parentPath = value;
    }

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
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProperty }
     *     
     */
    public ArrayOfProperty getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProperty }
     *     
     */
    public void setProperties(ArrayOfProperty value) {
        this.properties = value;
    }

    /**
     * Gets the value of the serverCookie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerCookie() {
        return serverCookie;
    }

    /**
     * Sets the value of the serverCookie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerCookie(String value) {
        this.serverCookie = value;
    }

    /**
     * Gets the value of the sortKey property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSortKey() {
        return sortKey;
    }

    /**
     * Sets the value of the sortKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSortKey(Integer value) {
        this.sortKey = value;
    }

    /**
     * Gets the value of the uiHint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUIHint() {
        return uiHint;
    }

    /**
     * Sets the value of the uiHint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUIHint(String value) {
        this.uiHint = value;
    }

}
