
package com.mip.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for Property complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="Property"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="IsSettable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ItemReference" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SelectedValueTypeInfoName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ServerValidation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="SortKey" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ToolTipTranslationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TranslationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="UIImportance" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ValueType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ValueTypeInfos" type="{http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI}ArrayOfValueTypeInfo" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Property", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", propOrder = {
    "displayName",
    "isSettable",
    "itemReference",
    "key",
    "selectedValueTypeInfoName",
    "serverValidation",
    "sortKey",
    "toolTipTranslationId",
    "translationId",
    "uiImportance",
    "value",
    "valueType",
    "valueTypeInfos"
})
public class Property {

    @XmlElement(name = "DisplayName", nillable = true)
    protected String displayName;
    @XmlElement(name = "IsSettable")
    protected Boolean isSettable;
    @XmlElement(name = "ItemReference")
    protected Boolean itemReference;
    @XmlElement(name = "Key", nillable = true)
    protected String key;
    @XmlElement(name = "SelectedValueTypeInfoName", nillable = true)
    protected String selectedValueTypeInfoName;
    @XmlElement(name = "ServerValidation")
    protected Boolean serverValidation;
    @XmlElement(name = "SortKey")
    protected Integer sortKey;
    @XmlElement(name = "ToolTipTranslationId", nillable = true)
    protected String toolTipTranslationId;
    @XmlElement(name = "TranslationId", nillable = true)
    protected String translationId;
    @XmlElement(name = "UIImportance")
    protected Integer uiImportance;
    @XmlElement(name = "Value", nillable = true)
    protected String value;
    @XmlElement(name = "ValueType", nillable = true)
    protected String valueType;
    @XmlElement(name = "ValueTypeInfos", nillable = true)
    protected ArrayOfValueTypeInfo valueTypeInfos;

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
     * Gets the value of the isSettable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSettable() {
        return isSettable;
    }

    /**
     * Sets the value of the isSettable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSettable(Boolean value) {
        this.isSettable = value;
    }

    /**
     * Gets the value of the itemReference property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isItemReference() {
        return itemReference;
    }

    /**
     * Sets the value of the itemReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setItemReference(Boolean value) {
        this.itemReference = value;
    }

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the selectedValueTypeInfoName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectedValueTypeInfoName() {
        return selectedValueTypeInfoName;
    }

    /**
     * Sets the value of the selectedValueTypeInfoName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectedValueTypeInfoName(String value) {
        this.selectedValueTypeInfoName = value;
    }

    /**
     * Gets the value of the serverValidation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isServerValidation() {
        return serverValidation;
    }

    /**
     * Sets the value of the serverValidation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setServerValidation(Boolean value) {
        this.serverValidation = value;
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
     * Gets the value of the toolTipTranslationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolTipTranslationId() {
        return toolTipTranslationId;
    }

    /**
     * Sets the value of the toolTipTranslationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolTipTranslationId(String value) {
        this.toolTipTranslationId = value;
    }

    /**
     * Gets the value of the translationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranslationId() {
        return translationId;
    }

    /**
     * Sets the value of the translationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranslationId(String value) {
        this.translationId = value;
    }

    /**
     * Gets the value of the uiImportance property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUIImportance() {
        return uiImportance;
    }

    /**
     * Sets the value of the uiImportance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUIImportance(Integer value) {
        this.uiImportance = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the valueType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueType() {
        return valueType;
    }

    /**
     * Sets the value of the valueType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueType(String value) {
        this.valueType = value;
    }

    /**
     * Gets the value of the valueTypeInfos property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfValueTypeInfo }
     *     
     */
    public ArrayOfValueTypeInfo getValueTypeInfos() {
        return valueTypeInfos;
    }

    /**
     * Sets the value of the valueTypeInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfValueTypeInfo }
     *     
     */
    public void setValueTypeInfos(ArrayOfValueTypeInfo value) {
        this.valueTypeInfos = value;
    }

}
