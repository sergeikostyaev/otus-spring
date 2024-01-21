package ru.otus.spring;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.sql.SQLException;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) throws SQLException {
        System.out.println("http://localhost:8080");
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        Resource schemaScript = new ClassPathResource("schema.sql");
        Resource dataScript = new ClassPathResource("data.sql");
        ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator(schemaScript);
        ResourceDatabasePopulator dataPopulator = new ResourceDatabasePopulator(dataScript);
        CompositeDatabasePopulator populator = new CompositeDatabasePopulator(schemaPopulator, dataPopulator);
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

}
