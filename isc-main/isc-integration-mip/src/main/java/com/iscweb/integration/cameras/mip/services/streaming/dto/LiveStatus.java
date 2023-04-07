package com.iscweb.integration.cameras.mip.services.streaming.dto;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "statusTime",
        "statusItems"
})
@XmlRootElement(name = "status")
public class LiveStatus {

    @Getter
    @Setter
    @XmlElement(name = "statustime")
    private Long statusTime;

    @Getter
    @Setter
    @XmlElement(name = "statusitem")
    private List<LiveStatusItem> statusItems;
}
