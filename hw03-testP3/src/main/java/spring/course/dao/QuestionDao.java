package spring.course.dao;

import spring.course.model.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getAllQuestions();
}
