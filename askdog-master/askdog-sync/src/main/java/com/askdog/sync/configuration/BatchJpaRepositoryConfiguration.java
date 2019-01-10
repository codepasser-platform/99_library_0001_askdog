package com.askdog.sync.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "batchEntityManagerFactory",
        transactionManagerRef = "batchTransactionManager",
        basePackages = {"com.askdog.sync.model.repository"}
)
public class BatchJpaRepositoryConfiguration {

    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Autowired
    private BatchDataSource batchDataSource;

    @Autowired
    private IndexSyncConfiguration configuration;

    @Bean(name = "batchEntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(batchDataSource.getDataSource());
        lef.setJpaVendorAdapter(jpaVendorAdapter());
        lef.setPackagesToScan("com.askdog.sync.model.entity");
        lef.afterPropertiesSet();
        return lef.getObject();
    }

    @Bean(name = "batchTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

    private JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return jpaVendorAdapter;
    }

}
