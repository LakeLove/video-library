package com.cashmovie.movielibrary;

import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("$postgres://shxvwedrjnchfl:0257ad7d98c790b1f5484bec8ce8e5838f87ef7c562348a30b66c629da085083@ec2-54-172-173-58.compute-1.amazonaws.com:5432/dfond77nbns5h0")
    private String dbUrl;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        return new HikariDataSource(config);
    }
}
