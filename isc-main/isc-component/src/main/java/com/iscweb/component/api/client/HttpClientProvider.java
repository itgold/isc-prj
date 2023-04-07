package com.iscweb.component.api.client;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.iscweb.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;

/**
 * Service that can be used to obtain new {@link org.apache.http.client.HttpClient}s to
 * retrieve external data.
 * The clients are all configured based on a Pooled connection manager that is configured
 * only once here. This class can be extended to provide secure layer connections when
 * there is need for it.
 */
@Slf4j
@Component
public class HttpClientProvider {

    public static final int CONNECT_TIMEOUT = 3_000;

    /**
     * @see java.net.SocketOptions#SO_TIMEOUT
     */
    public static final int SO_TIMEOUT = 10_000;

    public static final int MAX_PER_ROUTE = 10;
    public static final int MAX_TOTAL = 100;

    private PoolingHttpClientConnectionManager connectionManager;

    /**
     * The HTTP client provider is capable of storing all the requested HTTP clients
     * in this map for re-usability and faster access.
     */
    private Map<String, CloseableHttpClient> httpClients = Maps.newHashMap();

    /**
     * A factory method to initialize a new reference to the connection builder.
     *
     * @return connection builder reference.
     */
    public ConnectionBuilder builder() {
        return new ConnectionBuilder();
    }

    /**
     * Invoked by Spring once wiring is finished. Sets up the {@link PoolingHttpClientConnectionManager}
     */
    @PostConstruct
    public void setupConnectionManager() {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        connectionManager.setMaxTotal(MAX_TOTAL);
        connectionManager.setDefaultSocketConfig(
                SocketConfig.custom()
                            .setSoTimeout(SO_TIMEOUT)
                            .build());
    }

    public ConnectionExecutor forService(String serviceKey) {
        return forService(serviceKey, null);
    }

    /**
     * Creates an instance of the HTTP client and stores it in the internal map for caching and reuse.
     * If the client instance has been created under the given key, this returns it back to client.
     *
     * @param serviceKey the key to be used for getting HTTP client.
     * @return new instance or already opened instance of the HTTP client.
     */
    public ConnectionExecutor forService(String serviceKey, ConnectionBuilder builder) {
        ConnectionExecutor result;
        CloseableHttpClient httpClient = getHttpClients().get(serviceKey);
        if (httpClient == null) {
            ConnectionBuilder connectionBuilder = builder;
            if (builder == null) {
                connectionBuilder = new ConnectionBuilder();
            }
            result = connectionBuilder.build();
            getHttpClients().put(serviceKey, result.getClient());
        } else {
            result = new ConnectionExecutor(httpClient);
        }

        return result;
    }

    /**
     * Closes the HTTP client that is stored under the given key.
     *
     * @param serviceKey service key to be used for getting HTTP client instance.
     */
    public void closeForService(String serviceKey) {
        CloseableHttpClient httpClient = getHttpClients().get(serviceKey);
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("Failed to close HTTP client.", e);
            }
        }
    }

    public PoolingHttpClientConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public Map<String, CloseableHttpClient> getHttpClients() {
        return httpClients;
    }

    /**
     * Used to build a connection with the remote host.
     */
    public class ConnectionBuilder {

        private CredentialsProvider credentialsProvider;

        private Collection<Header> defaultHeaders;

        /**
         * Private constructor with parameter initialization.
         */
        private ConnectionBuilder() {
        }

        public ConnectionBuilder credentialsProvider(CredentialsProvider credentialsProvider) {
            this.credentialsProvider = credentialsProvider;
            return this;
        }

        public ConnectionBuilder defaultHeaders(Collection<Header> defaultHeaders) {
            this.defaultHeaders = defaultHeaders;
            return this;
        }

        /**
         * Creates a new {@link CloseableHttpClient} managed by the underlying connection manager.
         * Please note that this client can be reused to issue as many HTTP requests as necessary;
         * there is no need to get a new client per request (as long as the code is thread safe).
         *
         * @return {@link CloseableHttpClient}
         */
        public ConnectionExecutor build() {
            HttpClientBuilder clientBuilder = defaultBuilder();

            if (this.credentialsProvider != null) {
                clientBuilder.setDefaultCredentialsProvider(this.credentialsProvider);
            }

            if (this.defaultHeaders != null) {
                clientBuilder.setDefaultHeaders(this.defaultHeaders);
            }

            CloseableHttpClient client = clientBuilder.build();

            return new ConnectionExecutor(client);
        }

        /**
         * Creates a {@link HttpClientBuilder} with default settings which can be extended if needed.
         * If no configuration is necessary, use buildHttpClient() instead.
         */
        private HttpClientBuilder defaultBuilder() {
            RequestConfig requestConfig = RequestConfig.custom()
                                                       .setConnectTimeout(CONNECT_TIMEOUT)
                                                       .setSocketTimeout(SO_TIMEOUT)
                                                       .build();
            return HttpClients.custom()
                              .setConnectionManager(getConnectionManager())
                              .setConnectionManagerShared(true)
                              .setDefaultRequestConfig(requestConfig)
                              .setRedirectStrategy(new LaxRedirectStrategy());
        }
    }

    /**
     * Used to establish connection with the remote host.
     */
    public class ConnectionExecutor {

        /**
         * HTTP client to be used for making connections.
         */
        private CloseableHttpClient client;

        /**
         * Type of request (get or post).
         */
        private HttpRequestBase request;

        /**
         * Result of the operation.
         */
        private String result;

        /**
         * Private constructor of the executor.
         *
         * @param client HTTP connection to be used.
         */
        private ConnectionExecutor(CloseableHttpClient client) {
            if (client == null) {
                throw new IllegalArgumentException("Unable to initialize HTTP connection executor");
            }
            this.client = client;
        }

        /**
         * To be used for GET requests.
         *
         * @param targetUrl url to invoke.
         * @return this reference.
         */
        public ConnectionExecutor get(String targetUrl) {
            this.request = new HttpGet(targetUrl);
            return this;
        }

        /**
         * To be used for POST requests.
         *
         * @param targetUrl url to invoke.
         * @return this reference.
         */
        public ConnectionExecutor post(String targetUrl) {
            post(targetUrl, null);
            return this;
        }

        /**
         * POST request with HTTP entity parameter initialization.
         *
         * @param targetUrl  target post url.
         * @param httpEntity HTTP entity to send as part of post.
         * @return this instance.
         */
        public ConnectionExecutor post(String targetUrl, HttpEntity httpEntity) {
            HttpPost post = new HttpPost(targetUrl);
            post.setEntity(httpEntity);
            this.request = post;

            return this;
        }

        /**
         * PUT request with HTTP entity parameter initialization.
         *
         * @param targetUrl  target post url.
         * @param httpEntity HTTP entity to send as part of post.
         * @return this instance.
         */
        public ConnectionExecutor put(String targetUrl, HttpEntity httpEntity) {
            HttpPut put = new HttpPut(targetUrl);
            put.setEntity(httpEntity);
            this.request = put;

            return this;
        }

        /**
         * Initialization of OAuth2 authorization.
         *
         * @param clientId oauth2 client id.
         * @param secret   oauth2 client secret.
         * @return this reference.
         */
        public ConnectionExecutor auth2(String clientId, String secret) {
            String base64Credentials = Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes());
            this.request.addHeader("Authorization", "Basic " + base64Credentials);
            return this;
        }

        /**
         * Authorization with the security token.
         *
         * @param token token value.
         * @return this reference.
         */
        public ConnectionExecutor token(String token) {
            this.request.addHeader("Authorization", "Bearer " + token);
            return this;
        }

        /**
         * Performs a real remote call using parameters initialized in the methods above.
         *
         * @return this reference.
         * @throws ServiceException if operation failed.
         */
        public ConnectionExecutor exec() throws ServiceException {
            try {
                this.result = client.execute(request, new BasicResponseHandler());
            } catch (IOException e) {
                throw new ServiceException("Unable to make API call", e);
            }

            return this;
        }

        /**
         * Returns the result of the operation.
         *
         * @return operation result.
         */
        public String data() {
            return this.result;
        }

        /**
         * Returns result of the operation as JSON object.
         *
         * @return json object.
         */
        public JSONObject json() throws JSONException {
            JSONObject result = null;
            if (!Strings.isNullOrEmpty(this.result)) {
                result = new JSONObject(this.result);
            }
            return result;
        }

        /**
         * Retrieves result from JSON object by key.
         *
         * @param key to query resulting json.
         * @return key value.
         */
        public String json(String key) throws JSONException {
            String result = null;
            JSONObject jsonResult = json();
            if (!Strings.isNullOrEmpty(this.result)) {
                result = (String) jsonResult.get(key);
            }
            return result;
        }

        /**
         * A getter for the HTTP client.
         *
         * @return HTTP client instance.
         */
        public CloseableHttpClient getClient() {
            return client;
        }
    }
}
