package com.askdog.sync.configuration;

import com.askdog.sync.configuration.IndexSyncConfiguration.DataSourceConfiguration;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchDataSource {

    private javax.sql.DataSource dataSource;

    public javax.sql.DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    protected void dataSource(IndexSyncConfiguration indexSyncConfiguration) {
        DataSourceConfiguration configuration = indexSyncConfiguration.getDatasource();
        DataSource dataSource = new DataSource();
        dataSource.setUrl(configuration.getUrl());
        dataSource.setDriverClassName(configuration.getDriverClassName());
        dataSource.setUsername(configuration.getUsername());
        dataSource.setPassword(configuration.getPassword());
        this.dataSource = dataSource;
    }

}
