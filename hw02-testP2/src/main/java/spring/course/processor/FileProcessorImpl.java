package spring.course.processor;

import org.springframework.stereotype.Component;
import spring.course.dao.impl.QuestionDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileProcessorImpl implements FileProcessor{
    public List<String> processFile() throws IOException {
        ClassLoader classLoader = QuestionDaoImpl.class.getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream("test.csv");

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
