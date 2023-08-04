package spring.course;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.course.service.TestService;
import spring.course.service.impl.TestServiceImpl;

public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestService testService = context.getBean(TestServiceImpl.class);
        testService.run();
    }
}
