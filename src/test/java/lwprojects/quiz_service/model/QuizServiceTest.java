package lwprojects.quiz_service.model;

import lwprojects.quiz_service.Quiz;
import lwprojects.quiz_service.QuizResp;
import lwprojects.quiz_service.security.UserEntity;
import lwprojects.quiz_service.security.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {
    private final static String EMAIL = "test@gmail.com";

    private static String title;
    private static String text;
    private static List<String> options;
    private static List<Integer> answers;
    private static String[] optionsArray;
    private static int[] answersArray;

    private static UserEntity userEntity;
    private static Quiz quiz;
    private static QuizEntity entity;

    private static Principal principal;

    @Mock
    private QuizRepository quizRepository;
    @Mock
    private CompletionsRepository completionsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private QuizMapper mapper;

    @InjectMocks
    @Autowired
    private QuizService service;

    @BeforeAll
    static void setUp() {
        title = "title";
        text = "text";
        options = List.of("one", "two");
        answers = List.of(0);
        optionsArray = new String[]{"one","two"};
        answersArray = new int[]{0};
        userEntity = new UserEntity();
        userEntity.setEmail(EMAIL);
        quiz = new Quiz(title, text, options, answers);
        entity = new QuizEntity(title, text, optionsArray, answersArray, userEntity);
        principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn(EMAIL);
    }

    @Test
    void saveQuizTest() {
        when(mapper.mapToEntity(quiz, userEntity)).thenReturn(entity);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(userEntity));
        service.saveQuiz(quiz, principal);
        verify(quizRepository, times(1)).save(entity);
        verify(userRepository, times(1)).findByEmail(EMAIL);
    }

    @Test
    void deleteQuizTest() {
        when(quizRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(principal.getName()).thenReturn(EMAIL,"asd@gmail.com");
        assertTrue(service.deleteQuiz(entity.getId(), principal));
        /* With wrong email */
        assertFalse(service.deleteQuiz(entity.getId(), principal));
        verify(quizRepository, times(2)).findById(any());
        verify(quizRepository, times(1)).deleteById(entity.getId());
    }

    @Test
    void addCompletionsOfQuizTest() {
        when(quizRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(mapper.mapToDto(entity)).thenReturn(quiz);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(userEntity));

        assertEquals(QuizResp.CORRECT, service.addCompletionsOfQuiz(entity.getId(), answers, principal));
        verify(completionsRepository, times(1)).save(any());
        verify(quizRepository, times(2)).findById(entity.getId());
        verify(userRepository, times(1)).findByEmail(EMAIL);
    }
}