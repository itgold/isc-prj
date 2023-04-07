package com.iscweb.service;

import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.service.utils.GitMeta;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.iscweb.common.security.ApplicationSecurity.PERMIT_ALL;

@Service
public class SystemService implements IApplicationSecuredService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    protected @NonNull GitMeta gitMeta;

    @Getter(onMethod = @__({@PreAuthorize(PERMIT_ALL)}))
    @Value("${isc.server.environment:local}")
    protected @NonNull String environment;
}
