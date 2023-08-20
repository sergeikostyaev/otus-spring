package spring.course.processor.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring.course.processor.FileProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileProcessorImpl implements FileProcessor {
    @Value("${filepath}")
    private String filePath;

    public List<String> processFile() throws IOException {
        ClassLoader classLoader = FileProcessorImpl.class.getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(filePath);

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
