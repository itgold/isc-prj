package com.iscweb.app.main.config;

import com.iscweb.app.main.util.UserNameAuditorAware;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;
import java.util.Properties;

/**
 * Persistence configuration class for JPA (using Postgres)
 * Enables transaction management and defines jdbc connection pool parameters.
 */
@Slf4j
@Order(3)
@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableTransactionManagement
@EntityScan("com.iscweb.persistence.model.jpa")
@ComponentScan(basePackages = {"com.iscweb.service"})
@EnableJpaRepositories(basePackages = "com.iscweb.persistence.repositories.impl",
                       transactionManagerRef = "jpaTransactionManager")
public class Config03JpaPersistence implements TransactionManagementConfigurer {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    protected Environment environment;

    /**
     * Log message with after successful configuration initialization.
     */
    @PostConstruct
    public void constructed() {
        log.info("█████████████████████████████ Persistence Component Enabled ████████████████████████████████");
    }

    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("com.iscweb.persistence.model.jpa");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaProperties(hibernateProperties());

        return factory;
    }

    @Bean
    public AuditorAware<String> createAuditorProvider() {
        return new UserNameAuditorAware();
    }

    /**
     * Initializes list of hibernate specific properties.
     *
     * @return properties object.
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect",
                       getEnvironment().getRequiredProperty("spring.jpa.hibernate.dialect"));
        properties.put("hibernate.show_sql",
                       getEnvironment().getRequiredProperty("spring.jpa.show-sql"));
        properties.put("hibernate.hbm2ddl.auto",
                       getEnvironment().getRequiredProperty("spring.jpa.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.default_schema",
                       getEnvironment().getRequiredProperty("hibernate.default_schema"));
        properties.put("hibernate.connection.release_mode",
                       getEnvironment().getRequiredProperty("hibernate.connection.release_mode"));
        return properties;
    }

    /**
     * Hikari pool configuration object initialization.
     *
     * @return HikariConfig object reference.
     */
    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig result = new HikariConfig();
        result.setPoolName("hikariPool");
        result.setDriverClassName(getEnvironment().getProperty("spring.datasource.driver-class-name"));
        result.setJdbcUrl(getEnvironment().getProperty("spring.datasource.url"));

        String maxPoolSize = getEnvironment().getProperty("hibernate.hikari.maximumPoolSize", "25");
        result.setMaximumPoolSize(Integer.parseInt(maxPoolSize));

        String idleTimeOut = getEnvironment().getProperty("hibernate.hikari.idleTimeOut", "30000");
        result.setIdleTimeout(Integer.parseInt(idleTimeOut));

        Properties dataSourceProps = new Properties();
        dataSourceProps.setProperty("user", getEnvironment().getProperty("spring.datasource.username"));
        dataSourceProps.setProperty("password", getEnvironment().getProperty("spring.datasource.password"));

        result.setDataSourceProperties(dataSourceProps);
        return result;
    }

    @Override
    @Nonnull
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    /**
     * Support for ZonedDateTime jpa fields conversion.
     * @return date time provider class implementation.
     */
    @Bean(name = "dateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return new DateTimeProvider() {
            @Override
            @Nonnull
            public Optional<TemporalAccessor> getNow() {
                return Optional.of(ZonedDateTime.now());
            }
        };
    }
}
