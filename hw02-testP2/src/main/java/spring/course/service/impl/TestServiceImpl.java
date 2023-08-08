package spring.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.course.dao.QuestionDao;
import spring.course.model.Question;
import spring.course.service.IOService;
import spring.course.service.TestService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final QuestionDao questionDaoImpl;
    private final IOService ioServiceImpl;
    @Value("${questionsToBeProcessed}")
    private String questionsToBeProcessed;

    public void run() {
        List<Question> questions = questionDaoImpl.getAllQuestions().subList(0, Integer.parseInt(questionsToBeProcessed));
        AtomicInteger correctAnswers = new AtomicInteger();

        ioServiceImpl.print("Welcome to the test.\nPlease, print your name:");
        String name = ioServiceImpl.readLine();
        ioServiceImpl.print("Please, enter your surname:");
        String surname = ioServiceImpl.readLine();

        questions.forEach(c -> {
            ioServiceImpl.print(c.getQuestion());
            int i = 1;
            for (String s : c.getAnswers()) {
                ioServiceImpl.print(i + ") " + s);
                i++;
            }
            if (ioServiceImpl.readLine().equalsIgnoreCase(c.getCorrectAnswer())) {
                correctAnswers.getAndIncrement();
            }
            ioServiceImpl.print("");
        });

        ioServiceImpl.print(String.format("%s %s, your results are:\nCorrect answers - %s \nMistakes - %s"
                , name, surname, correctAnswers, Integer.parseInt(questionsToBeProcessed) - correctAnswers.get()));
    }
}
