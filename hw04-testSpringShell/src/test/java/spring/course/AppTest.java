package spring.course;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import spring.course.config.YMLConfiguration;
import spring.course.dao.QuestionDao;
import spring.course.dao.impl.QuestionDaoImpl;
import spring.course.processor.FileProcessor;
import spring.course.service.IOService;
import spring.course.service.TestService;
import spring.course.service.impl.LocalizationServiceImpl;
import spring.course.service.impl.TestServiceImpl;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
public class AppTest {

    @Autowired
    private FileProcessor fileProcessor;
    @Autowired
    private  YMLConfiguration ymlConfiguration;
    @Autowired
    private  LocalizationServiceImpl localizationService;
    @MockBean
    private FileProcessor fileProcessorMock;
    @MockBean
    private QuestionDao questionDao;
    @MockBean
    private IOService ioService;

    @Test
    public void questionDaoGetAllQuestions_correctProcessing() throws IOException {

        FileProcessor fileProcessor = fileProcessorMock;

        when(fileProcessor.processFile()).thenReturn(List.of("Test question?;TestQ1,TestQ2;TestAnswer"));

        QuestionDao questionDao = new QuestionDaoImpl(fileProcessor);

        Assertions.assertThat(questionDao.getAllQuestions().get(0).getCorrectAnswer()).isEqualTo("TestAnswer");
    }

    @Test
    public void testServiceRun_correctProcessing() throws IOException {

        QuestionDao questionDaoImpl = questionDao;
        IOService ioServiceImpl = ioService;

        TestService testService = new TestServiceImpl(questionDaoImpl, ioServiceImpl, ymlConfiguration, localizationService);

        ReflectionTestUtils.setField(ymlConfiguration, "questionsToBeProcessed","0");

        testService.run();

        Mockito.verify(questionDaoImpl, Mockito.times(1)).getAllQuestions();
    }

}
