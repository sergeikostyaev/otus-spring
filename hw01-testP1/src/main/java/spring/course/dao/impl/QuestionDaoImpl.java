package spring.course.dao.impl;

import spring.course.dao.QuestionDao;
import spring.course.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {

    public List<Question> getAllQuestions() {
        try {
            return parseLinesToQuestions(processFile());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Question> parseLinesToQuestions(List<String> lines) {
        List<Question> res = new ArrayList<>();

        for (String s : lines) {
            List<String> splitted = Arrays.stream(s.split(";")).toList();

            String question = splitted.get(0).trim();
            String correct = splitted.get(splitted.size() - 1).trim();
            ArrayList<String> answers = new ArrayList<>();
            splitted.forEach(c -> {
                if (!c.trim().equals(question) && !c.trim().equals(correct)) {
                    answers.addAll(Arrays.asList(c.split(",")));
                }
            });
            res.add(new Question(question, answers, correct));
        }
        return res;
    }


    private List<String> processFile() throws IOException {
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
