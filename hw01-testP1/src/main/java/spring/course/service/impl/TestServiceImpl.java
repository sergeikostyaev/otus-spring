package spring.course.service.impl;

import lombok.RequiredArgsConstructor;
import spring.course.dao.QuestionDao;
import spring.course.model.Question;
import spring.course.service.TestService;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final QuestionDao questionDaoImpl;

    public void run() {
        List<Question> questions = questionDaoImpl.getAllQuestions();
        questions.forEach(c -> {
            System.out.println(c.getQuestion());
            int i = 1;
            for (String s : c.getAnswers()) {
                System.out.println(i + ") " + s);
                i++;
            }
        });
    }
}
