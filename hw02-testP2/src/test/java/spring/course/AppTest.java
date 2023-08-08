package spring.course;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import spring.course.dao.QuestionDao;
import spring.course.dao.impl.QuestionDaoImpl;
import spring.course.processor.FileProcessor;
import spring.course.service.IOService;
import spring.course.service.TestService;
import spring.course.service.impl.TestServiceImpl;

import java.io.IOException;
import java.util.List;

public class AppTest {

    @Test
    public void questionDaoGetAllQuestions_correctProcessing() throws IOException {

        FileProcessor fileProcessor = Mockito.mock(FileProcessor.class);

        Mockito.when(fileProcessor.processFile()).thenReturn(List.of("Test question?;TestQ1,TestQ2;TestAnswer"));

        QuestionDao questionDao = new QuestionDaoImpl(fileProcessor);

        Assertions.assertThat(questionDao.getAllQuestions().get(0).getCorrectAnswer()).isEqualTo("TestAnswer");
    }

    @Test
    public void testServiceRun_correctProcessing() throws IOException {

        QuestionDao questionDaoImpl = Mockito.mock(QuestionDao.class);
        IOService ioServiceImpl = Mockito.mock(IOService.class);

        TestService testService = new TestServiceImpl(questionDaoImpl, ioServiceImpl);

        ReflectionTestUtils.setField(testService, "questionsToBeProcessed","0");

        testService.run();

        Mockito.verify(questionDaoImpl, Mockito.times(1)).getAllQuestions();
    }

}
