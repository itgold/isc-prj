package com.iscweb.integration.cameras.mip.services.streaming.dto;

import com.iscweb.integration.cameras.mip.services.streaming.adapters.BooleanYesNoAdapter;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "resolution",
        "maxResolution",
        "disabled"
})
@XmlRootElement(name = "adaptivestreaming")
public class AdaptiveStreaming {

    @Getter
    @Setter
    @XmlElement(name = "resolution")
    protected Resolution resolution;

    @Getter @Setter
    @XmlElement(name = "maxresolution")
    protected Integer maxResolution;

    @Getter @Setter
    @XmlJavaTypeAdapter(BooleanYesNoAdapter.class)
    @XmlElement(name = "disabled")
    protected Boolean disabled;
}
