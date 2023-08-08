package spring.course;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import spring.course.service.TestService;
import spring.course.service.impl.TestServiceImpl;

@ComponentScan
@PropertySource("classpath:application.properties")
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        TestService testService = context.getBean(TestServiceImpl.class);
        testService.run();
    }
}
