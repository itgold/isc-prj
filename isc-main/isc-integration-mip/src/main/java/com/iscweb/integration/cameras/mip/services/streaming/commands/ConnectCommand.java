package com.iscweb.integration.cameras.mip.services.streaming.commands;

import com.iscweb.integration.cameras.mip.services.streaming.adapters.BooleanYesNoAdapter;
import com.iscweb.integration.cameras.mip.services.streaming.dto.ClientCapabilities;
import com.iscweb.integration.cameras.mip.services.streaming.dto.TimeRestriction;
import com.iscweb.integration.cameras.mip.services.streaming.dto.Transcode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "username",
        "password",
        "cameraId",
        "connectParam",
        "alwaysStdJpeg",
        "clientCapabilities",
        "transcode",
        "timeRestriction"
})
@XmlRootElement(name = "methodcall")
public class ConnectCommand extends ImageServerCommand {

    private static final String CMD = "connect";
    private static final String CONN_PARAMS = "id=%s&streamid=%s&connectiontoken=%s";
    private static final String CONN_PARAMS2 = "id=%s&connectiontoken=%s";

    @Getter @Setter
    @XmlElement(name = "username", required = true)
    protected String username;

    @Getter @Setter
    @XmlElement(name = "password", required = true)
    protected String password;

    @Getter @Setter
    @XmlElement(name = "cameraid")
    protected String cameraId;

    @Getter @Setter
    @XmlElement(name = "connectparam")
    protected String connectParam;

    @Getter @Setter
    @XmlJavaTypeAdapter(BooleanYesNoAdapter.class)
    @XmlElement(name = "alwaysstdjpeg")
    protected Boolean alwaysStdJpeg;

    @Getter @Setter
    @XmlElement(name = "clientcapabilities")
    protected ClientCapabilities clientCapabilities;

    @Getter @Setter
    @XmlElement(name = "transcode")
    protected Transcode transcode;

    @Getter @Setter
    @XmlElement(name = "timerestriction")
    protected TimeRestriction timeRestriction;

    public ConnectCommand() {
        super();
        this.setMethodName(CMD);

        this.username = "dummy";
        this.password = "dummy";
    }

    public void setConnectParameters(String cameraId, String streamId, String token) {
        this.connectParam = streamId != null ? String.format(CONN_PARAMS, cameraId, streamId, token)
                : String.format(CONN_PARAMS2, cameraId, token);
        this.cameraId = cameraId;
    }
}
