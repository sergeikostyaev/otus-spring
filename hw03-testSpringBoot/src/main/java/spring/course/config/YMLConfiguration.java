package spring.course.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.stereotype.Component;


@ToString
@ConfigurationProperties(prefix = "test")
@Getter
@Component
public class YMLConfiguration {

    private final String filepath;
    private final String questionsToBeProcessed;

    @ConstructorBinding
    public YMLConfiguration(String filepath, String questionsToBeProcessed) {
        this.filepath = filepath;
        this.questionsToBeProcessed = questionsToBeProcessed;
    }
}
