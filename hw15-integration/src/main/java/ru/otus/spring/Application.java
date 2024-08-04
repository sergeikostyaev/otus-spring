package ru.otus.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.services.ButterflyService;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

		ButterflyService butterflyService = ctx.getBean(ButterflyService.class);
		butterflyService.start();
	}
}
