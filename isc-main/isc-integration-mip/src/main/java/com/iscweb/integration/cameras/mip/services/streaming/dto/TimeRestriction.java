package com.iscweb.integration.cameras.mip.services.streaming.dto;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "startTime",
        "endTime"
})
@XmlRootElement(name = "timerestriction")
public class TimeRestriction {

    @Getter
    @Setter
    @XmlElement(name = "starttime")
    protected Long startTime;       // milliseconds since epoc

    @Getter
    @Setter
    @XmlElement(name = "endtime")
    protected Long endTime;         // milliseconds since epoc
}
