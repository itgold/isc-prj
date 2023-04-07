
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &amp;lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="instanceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="integrationType" type="{http://video.net/2/XProtectCSServerCommand}IntegrationType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="pluginIntegrations" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfPluginIntegration" minOccurs="0"/&amp;gt;
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
    "token",
    "instanceId",
    "integrationType",
    "pluginIntegrations"
})
@XmlRootElement(name = "RegisterPluginIntegrations")
public class RegisterPluginIntegrations {

    @XmlElement(nillable = true)
    protected String token;
    @XmlElement(nillable = true)
    protected String instanceId;
    @XmlSchemaType(name = "string")
    protected IntegrationType integrationType;
    @XmlElement(nillable = true)
    protected ArrayOfPluginIntegration pluginIntegrations;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the instanceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * Sets the value of the instanceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstanceId(String value) {
        this.instanceId = value;
    }

    /**
     * Gets the value of the integrationType property.
     * 
     * @return
     *     possible object is
     *     {@link IntegrationType }
     *     
     */
    public IntegrationType getIntegrationType() {
        return integrationType;
    }

    /**
     * Sets the value of the integrationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegrationType }
     *     
     */
    public void setIntegrationType(IntegrationType value) {
        this.integrationType = value;
    }

    /**
     * Gets the value of the pluginIntegrations property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPluginIntegration }
     *     
     */
    public ArrayOfPluginIntegration getPluginIntegrations() {
        return pluginIntegrations;
    }

    /**
     * Sets the value of the pluginIntegrations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPluginIntegration }
     *     
     */
    public void setPluginIntegrations(ArrayOfPluginIntegration value) {
        this.pluginIntegrations = value;
    }

}
