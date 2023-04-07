package com.iscweb.service.api.v1.v10;

import com.iscweb.common.exception.ResourceNotFoundException;
import com.iscweb.common.model.entity.IUser;
import com.iscweb.service.api.BasePublicApiService;
import com.iscweb.service.entity.IndexService;
import com.iscweb.service.entity.user.UserEntityService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Service
@PreAuthorize(IS_AUTHENTICATED)
public class PublicApiServiceV10 extends BasePublicApiService {

    private static final String VERSION = "1.0";

    /**
     * Default limit for maximum correlations from the API.
     */
    private static final int DEFAULT_MAX_CORRELATIONS_LIMIT = 1000;
    private static final String SUCCESS_STATUS = "success";
    private static final String NULL_UTF8_CHARACTER = String.valueOf(Character.MIN_VALUE); // \u0000

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UserEntityService userEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IndexService indexService;

    @Override
    public String getVersion() {
        return VERSION;
    }

    /**
     * Find the IUser corresponding to the given principal.
     */
    public IUser getUser(Principal principal) throws ResourceNotFoundException {
        return getUserEntityService().findUser(principal.getName());
    }

    /**
     * Limits the limit parameter to the default value.
     *
     * @param limit limit parameter value.
     * @return limit or Max limit value.
     */
    public Integer limitTheLimit(Integer limit) {
        return Math.min(limit, DEFAULT_MAX_CORRELATIONS_LIMIT);
    }
}
