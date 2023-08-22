package spring.course.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "test")
@Getter
@Setter
@AllArgsConstructor
public class YMLConfiguration {

    private final String filepath;
    private final String questionsToBeProcessed;

}
