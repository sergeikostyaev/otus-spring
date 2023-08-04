package spring.course.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class Question {

    private final String question;

    private final List<String> answers;

    private final String correctAnswer;

}
