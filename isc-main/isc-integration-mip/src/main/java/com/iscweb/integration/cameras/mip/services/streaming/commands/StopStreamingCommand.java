package com.iscweb.integration.cameras.mip.services.streaming.commands;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
})
@XmlRootElement(name = "methodcall")
public class StopStreamingCommand extends ImageServerCommand {

    private static final String CMD = "stop";

    public StopStreamingCommand() {
        super();
        this.setMethodName(CMD);
    }
}
