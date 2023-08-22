package spring.course.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
@Configuration
public class LocaleProvider {
    private final Locale locale;

    public LocaleProvider(@Value("${otus.custom.locale}") String localeTag) {
        this.locale = Locale.forLanguageTag(localeTag);
    }

    public Locale getCurrent() {
        return locale;
    }

}
