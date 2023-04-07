package com.iscweb.component.web.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Request Matcher that can be used to skip certain paths.
 */
public class SkipPathRequestMatcher implements RequestMatcher {

    @Getter
    @Setter
    private OrRequestMatcher matchers;

    @Getter
    @Setter
    private RequestMatcher processingMatcher;

    public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
        List<RequestMatcher> matchers = pathsToSkip.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        setMatchers(new OrRequestMatcher(matchers));
        setProcessingMatcher(new AntPathRequestMatcher(processingPath));
    }

    /**
     * @see RequestMatcher#matcher(HttpServletRequest)
     */
    @Override
    public boolean matches(HttpServletRequest request) {
        boolean result = false;
        if (!getMatchers().matches(request)) {
            result = getProcessingMatcher().matches(request);
        }

        return result;
    }
}
