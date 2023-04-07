package com.iscweb.integration.config;

import com.iscweb.integration.cameras.mip.dto.ProxyParamsDto;
import com.iscweb.integration.cameras.mip.services.MipCameraServiceFactory;
import com.iscweb.integration.cameras.mip.services.mip.IConfigurationService;
import com.iscweb.integration.cameras.mip.services.mip.IRecorderCommandService;
import com.iscweb.integration.cameras.mip.services.mip.IRecorderStatusService;
import com.iscweb.integration.cameras.mip.services.mip.IServerCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Slf4j
@Order(20)
@Configuration
@Profile("mip")
@ComponentScan(basePackages = {"com.iscweb.integration.cameras.mip.services"})
public class Config20MipIntegration {

    public Config20MipIntegration() {
        System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Slf4jLogger");

        try {
            Config20MipIntegration.disableCertificateVerification();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.error("Unable to disable MIP SSL certificate validation", e);
        }
    }

    public static void disableCertificateVerification() throws KeyManagementException, NoSuchAlgorithmException {
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[]{new CustomTrustManager()};

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new SecureRandom());
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
        final HostnameVerifier verifier = new HostnameVerifier() {
            @Override
            public boolean verify(final String hostname, final SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(verifier);
    }

    @Bean
    public IServerCommandService getServerCommandService(
            MipCameraServiceFactory serviceFactory,
            @Value("${integration.cameras.mip.commands.secured}") boolean secure,
            @Value("${integration.cameras.mip.commands.address}") String address,
            @Value("${integration.cameras.mip.commands.port}") int port,
            @Value("${integration.cameras.mip.commands.username}") String username,
            @Value("${integration.cameras.mip.commands.password}") String password) {

        return serviceFactory.createJaxWsProxy(
                new ProxyParamsDto(secure, address, port, "/ManagementServer/ServerCommandService.svc", username, password),
                IServerCommandService.class);
    }

    @Bean
    public IConfigurationService getConfigurationService(
            MipCameraServiceFactory serviceFactory,
            @Value("${integration.cameras.mip.config.secured}") boolean secure,
            @Value("${integration.cameras.mip.config.address}") String address,
            @Value("${integration.cameras.mip.config.port}") int port,
            @Value("${integration.cameras.mip.config.username}") String username,
            @Value("${integration.cameras.mip.config.password}") String password) {

        return serviceFactory.createJaxWsProxy(
                new ProxyParamsDto(secure, address, port, "/ManagementServer/ConfigurationApiService.svc", username, password),
                IConfigurationService.class);
    }

    @Bean
    public IRecorderCommandService getRecorderCommandService(
            MipCameraServiceFactory serviceFactory,
            @Value("${integration.cameras.mip.recorder.secured}") boolean secure,
            @Value("${integration.cameras.mip.recorder.address}") String address,
            @Value("${integration.cameras.mip.recorder.port}") int port,
            @Value("${integration.cameras.mip.recorder.username}") String username,
            @Value("${integration.cameras.mip.recorder.password}") String password) {

        return serviceFactory.createJaxWsProxy(
                new ProxyParamsDto(secure, address, port, "/RecorderCommandService/RecorderCommandService.asmx", username, password),
                IRecorderCommandService.class);
    }

    @Bean
    public IRecorderStatusService getRecorderStatusService(
            MipCameraServiceFactory serviceFactory,
            @Value("${integration.cameras.mip.recorderStatus.secured}") boolean secure,
            @Value("${integration.cameras.mip.recorderStatus.address}") String address,
            @Value("${integration.cameras.mip.recorderStatus.port}") int port,
            @Value("${integration.cameras.mip.recorderStatus.username}") String username,
            @Value("${integration.cameras.mip.recorderStatus.password}") String password) {

        return serviceFactory.createJaxWsProxy(
                new ProxyParamsDto(secure, address, port, "/RecorderStatusService/RecorderStatusService2.asmx", username, password),
                IRecorderStatusService.class);
    }

    /**
     * class CustomTrustManager.
     */
    private static class CustomTrustManager implements X509TrustManager {
        /**
         * @return certificate.
         */
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        /**
         * @param certs
         * @param authType
         */
        public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
            /* no op */
        }

        /**
         * @param certs
         * @param authType
         */
        public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
            /* no op */
        }
    }
}
