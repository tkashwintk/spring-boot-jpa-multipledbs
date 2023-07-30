package com.example.multipledbs;

import java.util.HashMap;

import javax.sql.DataSource;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * By default, the persistence-multiple-db.properties file is read for
 * non auto configuration in PersistenceProductConfiguration.
 * <p>
 * If we need to use persistence-multiple-db-boot.properties and auto configuration
 * then uncomment the below @Configuration class and comment out PersistenceProductConfiguration.
 */
@Configuration
//@PropertySource({"classpath:persistence-multiple-db-boot.properties"})
@EnableJpaRepositories(basePackages = "com.example.multipledbs.dao.resume",
        entityManagerFactoryRef = "resumeEntityManager",
        transactionManagerRef = "resumeTransactionManager")
public class PostgreAutoConfiguration {
    @Autowired
    private Environment env;

    public PostgreAutoConfiguration() {
        super();
    }

    //

    @Bean
    public LocalContainerEntityManagerFactoryBean resumeEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(resumeDataSource());
        em.setPackagesToScan("com.example.multipledbs.dao.resume");
        final HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", PostgreSQL95Dialect.class.getName());
        properties.put("hibernate.default_schema", "workflow");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @ConfigurationProperties(prefix="spring.second-datasource")
    public DataSource resumeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public PlatformTransactionManager resumeTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(resumeEntityManager().getObject());
        return transactionManager;
    }

}
