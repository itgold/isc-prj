package com.iscweb.integration.cameras.mip.services;

import com.iscweb.common.service.IApplicationService;
import com.iscweb.common.util.StringUtils;
import com.iscweb.integration.cameras.mip.services.mip.IServerCommandService;
import com.iscweb.integration.cameras.mip.utils.XmlUtils;
import com.mip.command.LoginInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Milestone security token management service.
 * <p>
 * There are two authentication servers provided by Milestone:
 * CServer http://<host>/ManagementServer/ServerCommandService.svc
 * EServer http://<host>/ServerApi/ServerCommandService.asmx?wsdl
 * <p>
 * EServer is using Basic HTTP header authentication, token timeout is 4 minutes
 * CServer is using X509 certificate, token timeout is 1 hour.
 * <p>
 * Note: We have implemented only Basic authentication.
 * Check the documentation: https://doc.developer.milestonesys.com/html/reference/protocols/imageserver_authenticate.html
 */
@Slf4j
@Service
@Profile("web | api")
public class MipTokenService implements IApplicationService {

    private static final long MIN_TOKEN_TIMEOUT = 30 * 1000L; // 30 seconds
    private static final long TOKEN_CHECK_TIMEOUT = MIN_TOKEN_TIMEOUT - 5000;

    private final String CLIENT_ID = UUID.randomUUID().toString();

    private final AtomicReference<LoginInfo> token = new AtomicReference<>(null);

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private MipScheduledTasksService scheduledTasksService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IServerCommandService serverCommandService;

    @PostConstruct
    public void initialize() {
        getScheduledTasksService().scheduleAtFixedRate(() -> {
            try {
                refreshLoginToken(false);
            } catch (Throwable e) {
                token.set(null);
                if (log.isTraceEnabled()) {
                    log.trace("Unable to refresh MIP token.", e);
                } else {
                    String errorMessage = e.getCause() != null && e.getCause() instanceof SocketException
                            ? e.getCause().getMessage() : e.getMessage();
                    log.warn("Unable to refresh MIP token. {}", errorMessage);
                }
            }
        }, 100, TOKEN_CHECK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public String currentToken() {
        return currentToken(false);
    }

    public String currentToken(boolean forceTokenRefresh) {
        LoginInfo tokenInfo = token.get();
        if (tokenInfo == null) {
            tokenInfo = refreshLoginToken(forceTokenRefresh);
        }

        return tokenInfo != null ? tokenInfo.getToken() : null;
    }

    public LoginInfo refreshLoginToken(boolean forceTokenRefresh) {
        log.debug("Refresh MIP token ...");

        LoginInfo lastToken = token.get();
        try {
            String currentToken = lastToken != null ? lastToken.getToken() : StringUtils.EMPTY;
            boolean needRefresh = lastToken == null || forceTokenRefresh;
            if (lastToken != null && !needRefresh) {
                try {
                    long timeToLive = lastToken.getTimeToLive().getMicroSeconds() / 1000L; // in milliseconds
                    timeToLive = Math.max(MIN_TOKEN_TIMEOUT, timeToLive - MIN_TOKEN_TIMEOUT);

                    XMLGregorianCalendar expirationTime = (XMLGregorianCalendar) lastToken.getRegistrationTime().clone();
                    Duration duration = XmlUtils.durationFromMilliSeconds(timeToLive - TOKEN_CHECK_TIMEOUT);
                    expirationTime.add(duration);

                    XMLGregorianCalendar currentTime = XmlUtils.now();
                    if (currentTime.compare(expirationTime) >= 0) {
                        needRefresh = true;
                    }
                } catch (DatatypeConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }

            if (needRefresh) {
                lastToken = getServerCommandService().login(CLIENT_ID, currentToken);
                token.set(lastToken);

                log.debug("MIP token refreshed: {}", lastToken.getToken());
            }
        } catch (Throwable e) {
            token.set(null);

            if (log.isDebugEnabled()) {
                log.debug("Unable to refresh MIP token.", e);
            } else {
                String errorMessage = e.getCause() != null && (e.getCause() instanceof SocketException || e.getCause() instanceof NoRouteToHostException)
                        ? e.getCause().getMessage() : e.getMessage();
                log.warn("Unable to refresh MIP token. {}", errorMessage);
            }
        }

        return lastToken;
    }
}
