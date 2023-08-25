package spring.course.processor.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring.course.config.YMLConfiguration;
import spring.course.processor.FileProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileProcessorImpl implements FileProcessor {

    private final YMLConfiguration ymlConfiguration;
    @Override
    public List<String> processFile() throws IOException {
        ClassLoader classLoader = FileProcessorImpl.class.getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(ymlConfiguration.getFilepath());

        List<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
