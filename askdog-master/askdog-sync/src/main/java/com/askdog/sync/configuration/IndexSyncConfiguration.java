package com.askdog.sync.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "askdog.job.index-sync")
public class IndexSyncConfiguration {

    private DataSourceConfiguration datasource;

    private Map<String, String> jpa;

    public DataSourceConfiguration getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSourceConfiguration datasource) {
        this.datasource = datasource;
    }

    public Map<String, String> getJpa() {
        return jpa;
    }

    public void setJpa(Map<String, String> jpa) {
        this.jpa = jpa;
    }

    public static class DataSourceConfiguration {

        private String url;
        private String driverClassName;
        private String username;
        private String password;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
