package spring.course.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import spring.course.config.YMLConfiguration;
import spring.course.service.TestService;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final TestService testService;

    @Override
    public void run(ApplicationArguments args) {
        testService.run();
    }
}
