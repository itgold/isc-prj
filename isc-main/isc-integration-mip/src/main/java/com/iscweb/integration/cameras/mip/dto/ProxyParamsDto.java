package com.iscweb.integration.cameras.mip.dto;

import com.iscweb.common.model.dto.IDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProxyParamsDto implements IDto {
    public final boolean secure;
    public final String address;
    public final int port;
    public final String endpoint;
    public final String username;
    public final String password;
}
