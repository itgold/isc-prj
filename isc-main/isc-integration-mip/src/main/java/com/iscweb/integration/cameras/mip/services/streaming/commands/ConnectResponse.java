package com.iscweb.integration.cameras.mip.services.streaming.commands;

import com.iscweb.integration.cameras.mip.services.streaming.adapters.BooleanYesNoAdapter;
import lombok.Getter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "connected",
        "errorReason"
})
@XmlRootElement(name = "methodresponse")
public class ConnectResponse extends ImageServerResponse {

    @Getter
    @XmlJavaTypeAdapter(BooleanYesNoAdapter.class)
    @XmlElement(name = "connected")
    private Boolean connected;

    @Getter
    @XmlElement(name = "errorreason")
    private String errorReason;

    public boolean isConnected() {
        return connected != null ? connected.booleanValue() : false;
    }
}
