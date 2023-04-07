
package com.mip.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for CameraGroupInfo complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CameraGroupInfo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://video.net/2/XProtectCSServerCommand}GroupInfo"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="CameraGroups" type="{http://video.net/2/XProtectCSServerCommand}ArrayOfCameraGroupInfo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Cameras" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfguid" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CameraGroupInfo", propOrder = {
    "cameraGroups",
    "cameras"
})
public class CameraGroupInfo
    extends GroupInfo
{

    @XmlElement(name = "CameraGroups", nillable = true)
    protected ArrayOfCameraGroupInfo cameraGroups;
    @XmlElement(name = "Cameras", nillable = true)
    protected ArrayOfguid cameras;

    /**
     * Gets the value of the cameraGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCameraGroupInfo }
     *     
     */
    public ArrayOfCameraGroupInfo getCameraGroups() {
        return cameraGroups;
    }

    /**
     * Sets the value of the cameraGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCameraGroupInfo }
     *     
     */
    public void setCameraGroups(ArrayOfCameraGroupInfo value) {
        this.cameraGroups = value;
    }

    /**
     * Gets the value of the cameras property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfguid }
     *     
     */
    public ArrayOfguid getCameras() {
        return cameras;
    }

    /**
     * Sets the value of the cameras property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfguid }
     *     
     */
    public void setCameras(ArrayOfguid value) {
        this.cameras = value;
    }

}
