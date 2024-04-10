package spring.course.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "test")
@Getter
@Setter
@AllArgsConstructor
public class YMLConfiguration {

    private final String filepath;
    private final String questionsToBeProcessed;

}
