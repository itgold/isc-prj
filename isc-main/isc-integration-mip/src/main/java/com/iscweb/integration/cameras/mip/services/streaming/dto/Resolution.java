package com.iscweb.integration.cameras.mip.services.streaming.dto;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "widthHint",
        "heightHint"
})
@XmlRootElement(name = "resolution")
public class Resolution {
    @Getter
    @Setter
    @XmlElement(name = "widthhint")
    protected Integer widthHint;

    @Getter @Setter
    @XmlElement(name = "heighthint")
    protected Integer heightHint;
}
