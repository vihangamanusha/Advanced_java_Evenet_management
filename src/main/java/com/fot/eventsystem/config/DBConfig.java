package com.fot.eventsystem.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Bean
    public DataSource dataSource() {

        try {
            HikariDataSource ds = new HikariDataSource();

            ds.setJdbcUrl("jdbc:mysql://localhost:3306/fot_event_system");
            ds.setUsername("root");
            ds.setPassword("801@Vihanga");
            
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

            return ds;

        } catch (Exception e) {
            System.out.println("Database Connection Error: " + e.getMessage());
            throw e;   // better than returning null
        }
    }
}