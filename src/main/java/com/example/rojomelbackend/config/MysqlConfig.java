package com.example.rojomelbackend.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.ValidationMode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
        "com.example.rojomelbackend.repository"
},
entityManagerFactoryRef = "entityManagerFactory",
basePackageClasses = {JpaRepository.class},
transactionManagerRef = "transactionManager")
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
@Slf4j
public class MysqlConfig {

    public static final String ROJOMEL_BACKEND_DB = "rojomel";

    public static final long MAX_LIFE_TIME = 3600000;

    private static final String[] PACKAGES_TO_SCAN = {
            "com.example.rojomelbackend"
    };

    @Autowired
    private Environment env;

    @Qualifier("delegatingInterceptor")
    @Autowired
    private Interceptor delegatingInterceptor;

    @Bean("entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManager() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(env.getProperty("generateDdl", Boolean.class, false));
        vendorAdapter.setShowSql(true);
        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(PACKAGES_TO_SCAN);
        factory.setDataSource(rojomelDataSource());
        factory.setPersistenceUnitName(ROJOMEL_BACKEND_DB);

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.session_factory.interceptor", delegatingInterceptor);
        factory.setJpaProperties(jpaProperties);

        if (null != env.getProperty("startup.mode") && env.getProperty("startup.mode")
                .equals("fast")) {
            vendorAdapter.setGenerateDdl(false);
            factory.setValidationMode(ValidationMode.NONE);
        }
        return factory;
    }

    /**
     * HikariDataSource setup
     */
    @Primary
    @Bean
    public HikariDataSource rojomelDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(env.getProperty("rojomel.jdbc.driverClassName"));
        hikariDataSource.setJdbcUrl(env.getProperty("rojomel.jdbc.url"));
        hikariDataSource.setUsername(env.getProperty("rojomel.jdbc.username"));
        hikariDataSource.setPassword(env.getProperty("rojomel.jdbc.password"));
        hikariDataSource.setMaximumPoolSize(env.getProperty("rojomel.jdbc.max.connections", Integer.class));
        hikariDataSource.setMinimumIdle(20);
        hikariDataSource.setMaxLifetime(MAX_LIFE_TIME);

        return hikariDataSource;
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager rojomelTransactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManager().getObject());
        return txManager;
    }}
