
package com.mip.alarmService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for TPolygon complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TPolygon"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Closed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Color" type="{urn:milestone-systems}TColor" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="FillColor" type="{urn:milestone-systems}TColor" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PointList" type="{urn:milestone-systems}PointList" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPolygon", namespace = "urn:milestone-systems", propOrder = {
    "closed",
    "color",
    "fillColor",
    "pointList"
})
public class TPolygon {

    @XmlElement(name = "Closed")
    protected Boolean closed;
    @XmlElement(name = "Color", nillable = true)
    protected TColor color;
    @XmlElement(name = "FillColor", nillable = true)
    protected TColor fillColor;
    @XmlElement(name = "PointList", nillable = true)
    protected PointList pointList;

    /**
     * Gets the value of the closed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isClosed() {
        return closed;
    }

    /**
     * Sets the value of the closed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setClosed(Boolean value) {
        this.closed = value;
    }

    /**
     * Gets the value of the color property.
     * 
     * @return
     *     possible object is
     *     {@link TColor }
     *     
     */
    public TColor getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value
     *     allowed object is
     *     {@link TColor }
     *     
     */
    public void setColor(TColor value) {
        this.color = value;
    }

    /**
     * Gets the value of the fillColor property.
     * 
     * @return
     *     possible object is
     *     {@link TColor }
     *     
     */
    public TColor getFillColor() {
        return fillColor;
    }

    /**
     * Sets the value of the fillColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link TColor }
     *     
     */
    public void setFillColor(TColor value) {
        this.fillColor = value;
    }

    /**
     * Gets the value of the pointList property.
     * 
     * @return
     *     possible object is
     *     {@link PointList }
     *     
     */
    public PointList getPointList() {
        return pointList;
    }

    /**
     * Sets the value of the pointList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PointList }
     *     
     */
    public void setPointList(PointList value) {
        this.pointList = value;
    }

}
