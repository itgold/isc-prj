package com.iscweb.integration.cameras.mip.services.streaming.commands;

import com.iscweb.integration.cameras.mip.services.streaming.adapters.BooleanYesNoAdapter;
import com.iscweb.integration.cameras.mip.services.streaming.dto.AdaptiveStreaming;
import com.iscweb.integration.cameras.mip.services.streaming.dto.LiveAttributes;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "compressionRate",
        "sendInitialImage",
        "adaptiveStreaming",
        "attributes"
})
@XmlRootElement(name = "methodcall")
public class LiveCommand extends ImageServerCommand {

    private static final String CMD = "live";

    @Getter @Setter
    @XmlElement(name = "compressionrate")
    protected Integer compressionRate; // 100 ???

    @Getter @Setter
    @XmlJavaTypeAdapter(BooleanYesNoAdapter.class)
    @XmlElement(name = "sendinitialimage")
    protected Boolean sendInitialImage;

    @Getter @Setter
    @XmlElement(name = "attributes")
    protected LiveAttributes attributes;

    @Getter @Setter
    @XmlElement(name = "adaptivestreaming")
    protected AdaptiveStreaming adaptiveStreaming;

    public LiveCommand() {
        super();
        this.setMethodName(CMD);
    }
}
