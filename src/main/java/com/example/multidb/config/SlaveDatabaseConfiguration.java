package com.example.multidb.config;

import com.example.multidb.model.Employee;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.multidb.slave.repo", entityManagerFactoryRef = "slaveEntityManager", transactionManagerRef = "slaveTransactionManager")
@EnableTransactionManagement
public class SlaveDatabaseConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSourceProperties slaveSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties("spring.datasource.slave")
    public HikariDataSource slaveDataSource() {
        return slaveSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "slaveEntityManager")
    public LocalContainerEntityManagerFactoryBean postgresqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(slaveDataSource())
                .properties(hibernateProperties())
                .packages(Employee.class)
                .persistenceUnit("slavePU")
                .build();
    }

    @Bean(name = "slaveTransactionManager")
    public PlatformTransactionManager postgresqlTransactionManager(@Qualifier("slaveEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map hibernateProperties() {
        Resource resource = new ClassPathResource("hibernate.properties");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            return properties.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue())
                    );
        } catch (IOException e) {
            return new HashMap();
        }
    }
}
