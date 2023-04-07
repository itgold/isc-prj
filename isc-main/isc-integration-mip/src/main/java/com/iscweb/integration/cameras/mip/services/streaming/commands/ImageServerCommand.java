package com.iscweb.integration.cameras.mip.services.streaming.commands;

import com.iscweb.common.model.dto.IDto;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.concurrent.atomic.AtomicLong;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "requestId",
        "methodName"
})
public class ImageServerCommand implements IDto {

    private static final AtomicLong REQUEST_ID_GENERATOR = new AtomicLong(1L);

    @Getter @Setter
    @XmlElement(name = "requestid", required = true)
    private String requestId;

    @Getter @Setter
    @XmlElement(name = "methodname", required = true)
    private String methodName;

    public ImageServerCommand() {
        this.requestId = String.valueOf(REQUEST_ID_GENERATOR.incrementAndGet());
    }
}
