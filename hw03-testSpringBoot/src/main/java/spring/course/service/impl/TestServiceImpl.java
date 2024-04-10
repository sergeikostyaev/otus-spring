package spring.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.course.config.YMLConfiguration;
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
    private final YMLConfiguration ymlConfiguration;
    private final LocalizationServiceImpl localizationService;
    @Override
    public void run() {
        List<Question> questions = questionDaoImpl.getAllQuestions().subList(0, Integer.parseInt(ymlConfiguration.getQuestionsToBeProcessed()));
        AtomicInteger correctAnswers = new AtomicInteger();

        ioServiceImpl.print(localizationService.getMessage("greeting"));
        String name = ioServiceImpl.readLine();
        ioServiceImpl.print(localizationService.getMessage("greeting2"));
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

        ioServiceImpl.print(String.format(localizationService.getMessage("finalMessage")
                , name, surname, correctAnswers, Integer.parseInt(ymlConfiguration.getQuestionsToBeProcessed()) - correctAnswers.get()));
    }
}
