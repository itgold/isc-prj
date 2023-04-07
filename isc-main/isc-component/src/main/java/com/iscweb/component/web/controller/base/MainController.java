package com.iscweb.component.web.controller.base;

import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.component.web.controller.IApplicationController;
import com.iscweb.service.utils.ApplicationContext;
import com.iscweb.service.utils.GitMeta;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

import static com.iscweb.service.utils.ApplicationContext.Environment.PROD;

/**
 * Primary controller for handling static page requests.
 */
@Slf4j
@Controller
// @CrossOrigin(origins = "${spring.profiles.dev-ui.url}", allowedHeaders = "*", allowCredentials = "true")
public class MainController implements IApplicationController {

    public static final String DO_ANALYTICS = "doAnalytics";
    public static final String STATIC_PATH = "staticContentPath";
    public static final String IS_ADMIN = "isAdmin";

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private @NonNull ApplicationContext applicationContext;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private @NonNull GitMeta gitMeta;

    @Getter
    @Value("${spring.profiles.dev-ui.url:#{null}}")
    private String devUiServerUrl;

    /**
     * Initializes doAnalytics flag that is used for generating analytics code on the front-end.
     * We only need to use this on the Production system.
     *
     * @return true if the system is running in production mode.
     */
    @ModelAttribute(DO_ANALYTICS)
    public Boolean initAnalyticsFlag() {
        return PROD.equals(getApplicationContext().getEnvironment());
    }

    /**
     * Initializes frontEndUrl variable that is used to get static resources from the separate UI server.
     *
     * @return initialized location of the js resources.
     */
    @ModelAttribute(STATIC_PATH)
    public String initScriptPath() {
        String result = "/static";
        if (getApplicationContext().isActiveProfile("dev-ui")) {
            if (getDevUiServerUrl() != null) {
                result = getDevUiServerUrl();
            } else {
                log.error("dev-ui profile has been set but dev.server.ui.url property is not defined. Ignoring dev-ui profile.");
            }
        }
        return result;
    }

    @ModelAttribute(IS_ADMIN)
    public Boolean initIsAdmin() {
        boolean result = Boolean.FALSE;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (ApplicationSecurity.ROLE_SYSTEM_ADMINISTRATOR.equals(authority.getAuthority())
                        || ApplicationSecurity.ROLE_DISTRICT_ADMINISTRATOR.equals(authority.getAuthority())) {
                    result = Boolean.TRUE;
                    break;
                }
            }
        }
        return result;
    }

    @RequestMapping(value = {
            "/",
            "/undefined",
            "/login",
            "/ui/**",
            "/index.html**"
    })
    public String rootContext() {
        return "index-page";
    }

    /**
     * Agreement page handler.
     *
     * @return agreement page.
     */
    @RequestMapping({"/agreement.html",
                     "/agreement",
                     "/privacy-policy"})
    public String agreementHandler() {
        return "privacy-page";
    }

    @RequestMapping(value = {
            "/management"
    })
    public String management(ModelMap model) {
        model.addAttribute("environmentName", getApplicationContext().getEnvironment());
        model.addAttribute("applicationVersion", getApplicationContext().getVersion());
        model.addAttribute("hostList", getApplicationContext().getInfraHosts());
        return "management-page";
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
