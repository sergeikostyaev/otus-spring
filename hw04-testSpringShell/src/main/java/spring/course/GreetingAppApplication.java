package spring.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import spring.course.config.YMLConfiguration;


@EnableConfigurationProperties(YMLConfiguration.class)
@SpringBootApplication
public class GreetingAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(GreetingAppApplication.class, args);
	}

}
