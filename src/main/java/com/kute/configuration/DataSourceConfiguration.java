package com.kute.configuration;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * created by kute on 2018/07/23 22:01
 */
@Configuration
@PropertySource(value = {
        "classpath:db.properties"
}, encoding = "utf-8")
public class DataSourceConfiguration {

    @Autowired
    private Environment environment;

    private Properties buildProperties() {
        Properties properties = new Properties();
        properties.setProperty("driverClassName", environment.getProperty("driverClassName"));
        properties.setProperty("url", environment.getProperty("url"));
        properties.setProperty("username", environment.getProperty("username"));
        properties.setProperty("password", environment.getProperty("password"));
        properties.setProperty("minIdle", environment.getProperty("minIdle"));
        properties.setProperty("mzxIdle", environment.getProperty("mzxIdle"));
        properties.setProperty("maxActive", environment.getProperty("maxActive"));
        properties.setProperty("initialSize", environment.getProperty("initialSize"));
        properties.setProperty("logAbandoned", environment.getProperty("logAbandoned"));
        properties.setProperty("removeAbandoned", environment.getProperty("removeAbandoned"));
        properties.setProperty("maxWait", environment.getProperty("maxWait"));
        properties.setProperty("timeBetweenEvictionRunsMillis", environment.getProperty("timeBetweenEvictionRunsMillis"));
        properties.setProperty("testWhileIdle", environment.getProperty("testWhileIdle"));
        properties.setProperty("testOnBorrow", environment.getProperty("testOnBorrow"));
        properties.setProperty("testOnReturn", environment.getProperty("testOnReturn"));
        properties.setProperty("poolPreparedStatements", environment.getProperty("poolPreparedStatements"));
        properties.setProperty("maxOpenPreparedStatements", environment.getProperty("maxOpenPreparedStatements"));
        properties.setProperty("filters", environment.getProperty("filters"));
        return properties;
    }

    /**
     * commons dbcp2
     * @return
     * @throws Exception
     */
    @Bean
    public DataSource basicDataSource() throws Exception {
        BasicDataSource dataSource = BasicDataSourceFactory.createDataSource(buildProperties());
        return dataSource;
    }

    /**
     * druid
     * @return
     * @throws Exception
     */
    @Bean
    @Primary
    public DataSource druidDataSource() throws Exception {
        DataSource dataSource = DruidDataSourceFactory.createDataSource(buildProperties());
        return dataSource;
    }

}
