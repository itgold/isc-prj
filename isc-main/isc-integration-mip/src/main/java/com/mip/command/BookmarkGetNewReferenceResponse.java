
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
 *         &amp;lt;element name="BookmarkGetNewReferenceResult" type="{http://video.net/2/XProtectCSServerCommand}BookmarkReference" minOccurs="0"/&amp;gt;
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
    "bookmarkGetNewReferenceResult"
})
@XmlRootElement(name = "BookmarkGetNewReferenceResponse")
public class BookmarkGetNewReferenceResponse {

    @XmlElement(name = "BookmarkGetNewReferenceResult", nillable = true)
    protected BookmarkReference bookmarkGetNewReferenceResult;

    /**
     * Gets the value of the bookmarkGetNewReferenceResult property.
     * 
     * @return
     *     possible object is
     *     {@link BookmarkReference }
     *     
     */
    public BookmarkReference getBookmarkGetNewReferenceResult() {
        return bookmarkGetNewReferenceResult;
    }

    /**
     * Sets the value of the bookmarkGetNewReferenceResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookmarkReference }
     *     
     */
    public void setBookmarkGetNewReferenceResult(BookmarkReference value) {
        this.bookmarkGetNewReferenceResult = value;
    }

}
