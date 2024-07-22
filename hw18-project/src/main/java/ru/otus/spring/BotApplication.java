package ru.otus.spring;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@EnableAdminServer
@SpringBootApplication
public class BotApplication {

	public static void main(String[] args) throws SQLException {
		Console.main(args);
		SpringApplication.run(BotApplication.class, args);
	}

}
