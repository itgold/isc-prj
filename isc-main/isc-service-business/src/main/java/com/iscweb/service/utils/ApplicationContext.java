package com.iscweb.service.utils;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * Represents Loop context and environment.
 *
 * @author skurenkov
 */
@Repository
public class ApplicationContext {

    @Getter
    public IEnvironment environment;

    @Getter
    @Value("${isc.server.version:latest}")
    private String version;

    @Getter
    @Value("${isc.server.scheme:https}")
    private String serverScheme;

    @Getter
    @Value("${isc.server.host:app.iscweb.io}")
    private String serverHost;

    @Getter
    @Value("${server.port:#{443}}")
    private Integer serverPort;

    @Getter
    @Value("${spring.profiles.active}")
    private Set<String> activeProfiles = Sets.newHashSet();

    @Getter
    @Value("${isc.server.infra.monitoring:127.0.0.1}")
    private Set<String> infraHosts = Sets.newHashSet();

    /**
     * Initializes an environment object after the PhantasmContext construction.
     */
    @PostConstruct
    public void init() {
        Environment.init(this);
    }

    /**
     * Tests if execution profile is active.
     *
     * @param profileName profile name to check for activity.
     * @return true if profile is active.
     */
    public boolean isActiveProfile(String profileName) {
        boolean result = false;
        if (getActiveProfiles().contains(profileName)) {
            result = true;
        }

        return result;
    }

    @Value("${isc.server.environment:production}")
    public void setEnvironmentStr(String environmentStr) {
        this.environment = Environment.valueOf(environmentStr.toUpperCase());
    }

    public String getApplicationUrl(String uri) {
        return getEnvironment().getHostUrl() + uri;
    }

    public String getApplicationName() {
        return getEnvironment().getApplicationName();
    }

    /**
     * An interface for enforcing loop Environment object contract.
     */
    public interface IEnvironment {

        /**
         * Returns current Phantasm's instance host URL
         * that is specific to the particular environment.
         *
         * @return Phantasm's external URL.
         */
        String getHostUrl();

        String getHostUrl(String path);

        String getApplicationName();
    }

    /**
     * Environment enum that represents an environment on which
     * loop is currently running.
     */
    public enum Environment implements IEnvironment {

        PROD {
            /**
             * @see super#getHostUrl()
             */
            @Override
            public String getHostUrl() {
                if (getCachedUrl() == null) {
                    URL appUrl;
                    try {
                        appUrl = new URL(applicationContext.getServerScheme(),
                                            applicationContext.getServerHost(),
                                            "");
                    } catch (MalformedURLException e) {
                        throw new IllegalArgumentException("Unable to construct loop's URL.");
                    }
                    setCachedUrl(appUrl.toString());
                }
                return getCachedUrl();
            }

            /**
             * @see super#getApplicationName()
             */
            public String getApplicationName() {
                return "Loop";
            }
        },

        STAGING {
            /**
             * @see super#getHostUrl()
             */
            @Override
            public String getHostUrl() {
                if (getCachedUrl() == null) {
                    setCachedUrl(PROD.getHostUrl());
                }
                return getCachedUrl();
            }
            /**
             * @see super#getApplicationName()
             */
            public String getApplicationName() {
                return "Loop " + toString();
            }
        },

        QA {
            /**
             * @see super#getHostUrl()
             */
            @Override
            public String getHostUrl() {
                if (getCachedUrl() == null) {
                    setCachedUrl(PROD.getHostUrl());
                }
                return getCachedUrl();
            }
            /**
             * @see super#getApplicationName()
             */
            public String getApplicationName() {
                return "Loop " + toString();
            }
        },

        DEV {
            /**
             * @see super#getHostUrl()
             */
            @Override
            public String getHostUrl() {
                if (getCachedUrl() == null) {
                    setCachedUrl(PROD.getHostUrl());
                }
                return getCachedUrl();
            }
            /**
             * @see super#getApplicationName()
             */
            public String getApplicationName() {
                return "Loop " + toString();
            }
        },

        LOCAL {
            /**
             * @see super#getHostUrl()
             */
            @Override
            public String getHostUrl() {
                if (getCachedUrl() == null) {
                    setCachedUrl(PROD.getHostUrl());
                }
                return getCachedUrl();
            }
            /**
             * @see super#getApplicationName()
             */
            public String getApplicationName() {
                return "Loop " + toString();
            }
        };


        /**
         * ApplicationContext instance reference for accessing configuration parameters.
         */
        private static ApplicationContext applicationContext;

        @Getter
        @Setter
        private String cachedUrl;

        @Override
        public String getHostUrl(String path) {
            return getHostUrl() + path;
        }


        /**
         * Static initializer method that initializes PhantasmContext reference.
         *
         * @param outerReference Phantasm context.
         */
        public static void init(ApplicationContext outerReference) {
            applicationContext = outerReference;
        }

    }
}
