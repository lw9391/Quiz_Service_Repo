package lwprojects.quiz_service.model;

import lwprojects.quiz_service.Quiz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizMapperTest {
    private static QuizMapper mapper;
    private static long id;
    private static String title;
    private static String text;
    private static List<String> options;
    private static List<Integer> answers;
    private static String[] optionsArray;
    private static int[] answersArray;

    @BeforeAll
    static void beforeAll() {
        mapper = new QuizMapper();
        id = 1;
        title = "title";
        text = "text";
        options = List.of("one", "two");
        answers = List.of(0);
        optionsArray = new String[]{"one","two"};
        answersArray = new int[]{0};
    }

    @Test
    void mapToEntityTest() {
        Quiz quiz = new Quiz(id, title, text, options, answers);
        QuizEntity entity = mapper.mapToEntity(quiz);

        assertEquals(title, entity.getTitle());
        assertEquals(text, entity.getText());
        assertEquals(text, entity.getText());
        assertArrayEquals(optionsArray, entity.getOptions());
        assertArrayEquals(answersArray, entity.getAnswers());
    }

    @Test
    void mapToDtoTest() {
        QuizEntity entity = new QuizEntity(title, text, optionsArray, answersArray);
        Quiz quiz = mapper.mapToDto(entity);

        assertEquals(title, quiz.getTitle());
        assertEquals(text, quiz.getText());
        assertEquals(text, quiz.getText());
        assertEquals(options, quiz.getOptions());
        assertEquals(answers, quiz.getAnswer());
    }
}