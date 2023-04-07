package com.iscweb.service.api.v1.v11;

import com.iscweb.service.api.v1.v10.PublicApiServiceV10;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Service
@PreAuthorize(IS_AUTHENTICATED)
public class PublicApiServiceV11 extends PublicApiServiceV10 {

    public static final String EXTERNAL_ID_TYPE = "external";
    public static final String INTERNAL_ID_TYPE = "internal";
    private static final String VERSION = "1.1";
    /**
     * A limit on the number of returned file records that contain a given set of indicators.
     */
    private static final Integer FILE_LIMIT = 1000;

    @Override
    public String getVersion() {
        return VERSION;
    }
}
