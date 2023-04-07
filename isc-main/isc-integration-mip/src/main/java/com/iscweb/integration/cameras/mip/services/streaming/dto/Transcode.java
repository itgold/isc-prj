package com.iscweb.integration.cameras.mip.services.streaming.dto;

import com.iscweb.integration.cameras.mip.services.streaming.adapters.BooleanYesNoAdapter;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "allFrames",
        "height",
        "width",
        "keepAspectRatio",
        "allowUpSizing"
})
@XmlRootElement(name = "transcode")
public class Transcode {
    @Getter
    @Setter
    @XmlJavaTypeAdapter(BooleanYesNoAdapter.class)
    @XmlElement(name = "allframes")
    protected Boolean allFrames;

    @Getter @Setter
    @XmlElement(name = "height")
    protected Integer height;

    @Getter @Setter
    @XmlElement(name = "width")
    protected Integer width;

    @Getter
    @Setter
    @XmlJavaTypeAdapter(BooleanYesNoAdapter.class)
    @XmlElement(name = "keepaspectratio")
    protected Boolean keepAspectRatio;

    @Getter
    @Setter
    @XmlJavaTypeAdapter(BooleanYesNoAdapter.class)
    @XmlElement(name = "allowupsizing")
    protected Boolean allowUpSizing;
}
