package lwprojects.quiz_service.model;

import lwprojects.quiz_service.Quiz;
import lwprojects.quiz_service.security.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* Mapper between Quiz DTO and Entities */
@Component
public class QuizMapper {
    public Quiz mapToDto(QuizEntity entity) {
        List<Integer> answers = Arrays.stream(entity.getAnswers())
                .boxed()
                .collect(Collectors.toList());
        return new Quiz(entity.getId(), entity.getTitle(), entity.getText(), Arrays.asList(entity.getOptions()), answers);
    }

    public QuizEntity mapToEntity(Quiz quiz) {
        String[] options = quiz.getOptions().toArray(new String[0]);
        int[] answers = quiz.getAnswer()
                .stream()
                .mapToInt(i -> i)
                .toArray();
        return new QuizEntity(quiz.getTitle(), quiz.getText(), options, answers);
    }

    public QuizEntity mapToEntity(Quiz quiz, UserEntity userEntity) {
        String[] options = quiz.getOptions()
                .toArray(new String[0]);
        int[] answers = quiz.getAnswer()
                .stream()
                .mapToInt(i -> i)
                .toArray();
        return new QuizEntity(quiz.getTitle(), quiz.getText(), options, answers, userEntity);
    }
}
