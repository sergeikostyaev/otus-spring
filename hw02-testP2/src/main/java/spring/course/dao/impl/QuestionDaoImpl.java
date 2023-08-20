package spring.course.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spring.course.dao.QuestionDao;
import spring.course.model.Question;
import spring.course.processor.FileProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final FileProcessor fileProcessorImpl;

    public List<Question> getAllQuestions() {
        try {
            return parseLinesToQuestions(fileProcessorImpl.processFile());
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

}
