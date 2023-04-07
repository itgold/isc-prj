
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for AlertTypeGroupInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="AlertTypeGroupInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}GroupInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="AlertTypeGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfAlertTypeGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="AlertTypes" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlertTypeGroupInfo", propOrder = {
    "alertTypeGroups",
    "alertTypes"
})
public class AlertTypeGroupInfo
    extends GroupInfo
{

    @XmlElement(name = "AlertTypeGroups", nillable = true)
    protected ArrayOfAlertTypeGroupInfo alertTypeGroups;
    @XmlElement(name = "AlertTypes", nillable = true)
    protected ArrayOfguid alertTypes;

    /**
     * Gets the value of the alertTypeGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAlertTypeGroupInfo }
     *     
     */
    public ArrayOfAlertTypeGroupInfo getAlertTypeGroups() {
        return alertTypeGroups;
    }

    /**
     * Sets the value of the alertTypeGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAlertTypeGroupInfo }
     *     
     */
    public void setAlertTypeGroups(ArrayOfAlertTypeGroupInfo value) {
        this.alertTypeGroups = value;
    }

    /**
     * Gets the value of the alertTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getAlertTypes() {
        return alertTypes;
    }

    /**
     * Sets the value of the alertTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setAlertTypes(ArrayOfguid value) {
        this.alertTypes = value;
    }

}
