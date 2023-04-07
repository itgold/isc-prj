package com.iscweb.integration.cameras.mip.services.streaming.commands;

import com.iscweb.integration.cameras.mip.services.streaming.dto.LiveStatus;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "status"
})
@XmlRootElement(name = "livepackage")
public class LiveCommandStatus {

    @Getter @Setter
    @XmlElement(name = "status")
    private LiveStatus status;
}
