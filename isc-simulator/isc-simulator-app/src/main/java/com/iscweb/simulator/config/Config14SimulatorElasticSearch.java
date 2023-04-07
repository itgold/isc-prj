package com.iscweb.simulator.config;

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
public class Config14SimulatorElasticSearch extends ElasticsearchConfigurationSupport {

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
