package spring.course.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import spring.course.service.TestService;

@ShellComponent
@RequiredArgsConstructor
public class TestShellController {

    private final TestService testService;

    @ShellMethod(key = {"run", "r"})
    public void run(){
        testService.run();
    }

}
