package com.iscweb.integration.cameras.mip.services.streaming.commands;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "requestId",
        "methodName"
})
public class ImageServerResponse {

    @Getter
    @XmlElement(name = "requestid", required = true)
    private String requestId;

    @Getter @Setter
    @XmlElement(name = "methodname", required = true)
    private String methodName;
}
