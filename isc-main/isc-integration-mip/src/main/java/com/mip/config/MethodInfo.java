
package com.mip.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for MethodInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MethodInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Bitmap" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MethodId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="TranslationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MethodInfo", namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", propOrder = {
    "bitmap",
    "displayName",
    "methodId",
    "translationId"
})
public class MethodInfo {

    @XmlElement(name = "Bitmap", nillable = true)
    protected byte[] bitmap;
    @XmlElement(name = "DisplayName", nillable = true)
    protected String displayName;
    @XmlElement(name = "MethodId", nillable = true)
    protected String methodId;
    @XmlElement(name = "TranslationId", nillable = true)
    protected String translationId;

    /**
     * Gets the value of the bitmap property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBitmap() {
        return bitmap;
    }

    /**
     * Sets the value of the bitmap property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBitmap(byte[] value) {
        this.bitmap = value;
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
     * Gets the value of the methodId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethodId() {
        return methodId;
    }

    /**
     * Sets the value of the methodId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethodId(String value) {
        this.methodId = value;
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

}
