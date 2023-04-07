package com.iscweb.integration.cameras.mip.services.streaming.dto;

import com.iscweb.common.model.dto.IDto;
import com.iscweb.integration.cameras.mip.services.streaming.adapters.BooleanYesNoAdapter;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "privacyMask",
        "privacyMaskVersion",
        "multiPartData",
        "dataRestriction"
})
@XmlRootElement(name = "clientcapabilities")
public class ClientCapabilities implements IDto {

    public static final int MASK_VERSION_0 = 0;
    public static final int MASK_VERSION_1 = 1;

    @Getter
    @Setter
    @XmlJavaTypeAdapter(BooleanYesNoAdapter.class)
    @XmlElement(name = "privacymask")
    protected Boolean privacyMask;

    @Getter
    @Setter
    @XmlElement(name = "privacymaskversion")
    protected Integer privacyMaskVersion;

    @Getter
    @Setter
    @XmlJavaTypeAdapter(BooleanYesNoAdapter.class)
    @XmlElement(name = "multipartdata")
    protected Boolean multiPartData;

    @Getter
    @Setter
    @XmlJavaTypeAdapter(BooleanYesNoAdapter.class)
    @XmlElement(name = "datarestriction")
    protected Boolean dataRestriction;
}
