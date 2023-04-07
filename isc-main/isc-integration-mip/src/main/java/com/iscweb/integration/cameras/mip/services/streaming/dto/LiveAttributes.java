package com.iscweb.integration.cameras.mip.services.streaming.dto;

import com.iscweb.integration.cameras.mip.services.streaming.adapters.BooleanYesNoAdapter;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "frameRateRelative",
        "frameRateAbsolute",
        "frameRate",
        "motionOnly"
})
@XmlRootElement(name = "attributes")
public class LiveAttributes {

    @Getter
    @Setter
    @XmlAttribute(name = "frameraterelative")
    private String frameRateRelative;

    @Getter
    @Setter
    @XmlAttribute(name = "framerateabsolute")
    private String frameRateAbsolute;

    @Getter
    @Setter
    @XmlAttribute(name = "framerate")
    private String frameRate; // full/medium/low

    @Getter
    @Setter
    @XmlJavaTypeAdapter(BooleanYesNoAdapter.class)
    @XmlAttribute(name = "motiononly")
    private Boolean motionOnly;
}
