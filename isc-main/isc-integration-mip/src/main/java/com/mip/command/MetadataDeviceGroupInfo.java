
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for MetadataDeviceGroupInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MetadataDeviceGroupInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}GroupInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="MetadataDeviceGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfMetadataDeviceGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="MetadataDevices" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetadataDeviceGroupInfo", propOrder = {
    "metadataDeviceGroups",
    "metadataDevices"
})
public class MetadataDeviceGroupInfo
    extends GroupInfo
{

    @XmlElement(name = "MetadataDeviceGroups", nillable = true)
    protected ArrayOfMetadataDeviceGroupInfo metadataDeviceGroups;
    @XmlElement(name = "MetadataDevices", nillable = true)
    protected ArrayOfguid metadataDevices;

    /**
     * Gets the value of the metadataDeviceGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMetadataDeviceGroupInfo }
     *     
     */
    public ArrayOfMetadataDeviceGroupInfo getMetadataDeviceGroups() {
        return metadataDeviceGroups;
    }

    /**
     * Sets the value of the metadataDeviceGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMetadataDeviceGroupInfo }
     *     
     */
    public void setMetadataDeviceGroups(ArrayOfMetadataDeviceGroupInfo value) {
        this.metadataDeviceGroups = value;
    }

    /**
     * Gets the value of the metadataDevices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getMetadataDevices() {
        return metadataDevices;
    }

    /**
     * Sets the value of the metadataDevices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setMetadataDevices(ArrayOfguid value) {
        this.metadataDevices = value;
    }

}
