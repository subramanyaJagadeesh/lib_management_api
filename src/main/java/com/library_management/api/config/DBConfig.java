package com.library_management.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DBConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.hikari")
    public DataSource readDataSource() {
        return readDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.hikari")
    public DataSource writeDataSource() {
        return writeDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties readDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties writeDataSourceProperties() { return new DataSourceProperties(); }

//    @Bean
//    @ConfigurationProperties(prefix = "spring.liquibase")
//    public LiquibaseProperties primaryLiquibaseProperties() {
//        return new LiquibaseProperties();
//    }

//    @Bean
//    public SpringLiquibase primaryLiquibase() throws SQLException {
//        return springLiquibase(writeDataSource(), primaryLiquibaseProperties());
//    }

//    private  SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setDataSource(dataSource);
//        liquibase.setChangeLog(properties.getChangeLog());
//        liquibase.setContexts(properties.getContexts());
//        liquibase.setDefaultSchema(properties.getDefaultSchema());
//        liquibase.setDropFirst(properties.isDropFirst());
//        liquibase.setShouldRun(properties.isEnabled());
//        liquibase.setLabels(properties.getLabels());
//        liquibase.setChangeLogParameters(properties.getParameters());
//        liquibase.setRollbackFile(properties.getRollbackFile());
//        return liquibase;
//    }
//    @Bean
//    public JdbcTemplate readJdbcTemplate(@Qualifier("readDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Bean
//    public JdbcTemplate writeJdbcTemplate(@Qualifier("writeDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }

    @Bean
    public NamedParameterJdbcTemplate readNamedJdbcTemplate(@Qualifier("readDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate writeNamedJdbcTemplate(@Qualifier("writeDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }


}
