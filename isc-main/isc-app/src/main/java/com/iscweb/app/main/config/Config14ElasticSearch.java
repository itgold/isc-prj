package com.iscweb.app.main.config;

import com.google.common.collect.Lists;
import com.iscweb.search.CustomConverters;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

/**
 * Elastic Search client configuration.
 *
 * We can register custom marshallers/unmarshallers like following:
 * <code>
 *     @Bean
 *     public ElasticsearchCustomConversions elasticsearchCustomConversions() {
 *         return new ElasticsearchCustomConversions(
 *                 List.of(
 *                         new ZonedDateTimeToStringConverter(),
 *                         new StringToZonedDateTimeConverter()
 *                 )
 *         );
 *     }
 *
 *     and you can define custom unmarshaller like following:
 *     @ReadingConverter
 *     public class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {
 *
 *         @Override
 *         public ZonedDateTime convert(String source) {
 *             return ZonedDateTime.parse(source, DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault()));
 *         }
 *     }
 *
 *     and marshaller can be defined like following:
 *     @WritingConverter
 *     public class ZonedDateTimeToStringConverter implements Converter<ZonedDateTime, String> {
 *
 *         @Override
 *         public String convert(ZonedDateTime source) {
 *             return source.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
 *         }
 *     }
 * </code>
 *
 */
@Slf4j
@Order(14)
@Configuration
@ComponentScan(basePackages = {"com.iscweb.search"})
@EnableElasticsearchRepositories(basePackages = "com.iscweb.search.repositories")
public class Config14ElasticSearch extends ElasticsearchConfigurationSupport {

    /**
     * Custom converter for elasticsearch date types.
     * @return custom converter instance.
     */
    @Bean
    @Override
    public @NonNull
    ElasticsearchCustomConversions elasticsearchCustomConversions() {
        List<Converter<?, ?>> converters = Lists.newArrayList(CustomConverters.StringToZonedDateTimeConverter.INSTANCE,
                CustomConverters.ZonedDateTimeToStringConverter.INSTANCE,
                CustomConverters.IntegerToDateConverter.INSTANCE);

        return new ElasticsearchCustomConversions(Collections.unmodifiableList(converters));
    }

    /**
     * Create ES client.
     *
     * Example of config with authentication:
     * RestClientBuilder restBuilder = RestClient
     *                     .builder(new HttpHost(environment.getProperty("zselastic.host").toString(),
     *                             Integer.valueOf(environment.getProperty("zselastic.port").toString()),
     *                             environment.getProperty("zselastic.protocol").toString()));
     *             String username = new String(environment.getProperty("zselastic.username").toString());
     *             String password = new String(environment.getProperty("zselastic.password").toString());
     *             if (username != null & password != null) {
     *                 final CredentialsProvider credential = new BasicCredentialsProvider();
     *                 credential.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
     *                 restBuilder.setHttpClientConfigCallback(new HttpClientConfigCallback() {
     *                     @Override
     *                     public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
     *
     *                         return httpClientBuilder.setDefaultCredentialsProvider(credential)
     *                                 .setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(1).build());
     *                     }
     *                 });
     *
     *                 restBuilder.setRequestConfigCallback(requestConfigBuilder ->
     *                 requestConfigBuilder.setConnectTimeout(10000) // time until a connection with the server is established.
     *                         .setSocketTimeout(60000) // time of inactivity to wait for packets[data] to receive.
     *                         .setConnectionRequestTimeout(0)); // time to fetch a connection from the connection pool 0 for infinite.
     *
     *                 client = new RestHighLevelClient(restBuilder);
     *                 return client;
     *             }
     *
     * @param clusterNodes
     * @return
     */

    @Bean
    public RestHighLevelClient client(
            @Value("${spring.data.elasticsearch.cluster-nodes:#{'localhost:9200'}}") final String clusterNodes,
            @Value("${spring.data.elasticsearch.connectionTimeout:#{5000}}") final long connectionTimeout) {
        assert !ObjectUtils.isEmpty(clusterNodes);

        String[] hostAndPort = clusterNodes.split(",");
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(hostAndPort)
                .withConnectTimeout(connectionTimeout)
                .withSocketTimeout(connectionTimeout)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
