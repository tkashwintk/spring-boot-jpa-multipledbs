package com.example.multipledbs;

import java.util.HashMap;

import javax.sql.DataSource;

import org.hibernate.dialect.MySQL8Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * By default, the persistence-multiple-db.properties file is read for 
 * non auto configuration in PersistenceUserConfiguration. 
 * <p>
 * If we need to use persistence-multiple-db-boot.properties and auto configuration 
 * then uncomment the below @Configuration class and comment out PersistenceUserConfiguration. 
 */
//@Configuration
//@PropertySource({"classpath:persistence-multiple-db-boot.properties"})
@Configuration
@EnableJpaRepositories(basePackages = "com.example.multipledbs.dao.student",
        entityManagerFactoryRef = "studentEntityManager",
        transactionManagerRef = "studentTransactionManager")
public class MySQLAutoConfiguration {
    @Autowired
    private Environment env;

    public MySQLAutoConfiguration() {
        super();
    }


    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean studentEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(studentDataSource());
        em.setPackagesToScan("com.example.multipledbs.dao.student");
        final HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", MySQL8Dialect.class.getName());
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource studentDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager studentTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(studentEntityManager().getObject());
        return transactionManager;
    }

}
