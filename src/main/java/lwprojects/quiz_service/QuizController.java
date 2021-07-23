package lwprojects.quiz_service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lwprojects.quiz_service.model.QuizCompletion;
import lwprojects.quiz_service.model.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class QuizController {
    private final QuizService quizService;
    private static final String NOT_FOUND_MESSAGE = "Element with such id doesn't exist.";

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(path = "/api/quizzes")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz, Principal principal) {
        long id = quizService.saveQuiz(quiz, principal);
        quiz.setId(id);
        return quiz;
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable long id) {
        return getQuiz(id);
    }

    @GetMapping(path = "/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam Optional<Integer> page) {
        return quizService.getAllQuizzes(page.orElse(0));
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public QuizResp solveQuiz(@PathVariable long id, @RequestBody AnswerWrapper answerWrapper, Principal principal) {
        return quizService.addCompletionsOfQuiz(id, answerWrapper.answer, principal);
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable long id, Principal principal) {
        if (quizService.deleteQuiz(id, principal)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = NOT_FOUND_MESSAGE)
    private HashMap<String, String> handleIndexOutOfBound(Exception e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", NOT_FOUND_MESSAGE);
        response.put("error", e.getClass().getSimpleName());
        return response;
    }

    @GetMapping(path = "/api/quizzes/completed")
    public Page<QuizCompletion> getQuizCompletions(@RequestParam Optional<Integer> page, Principal principal) {
        int pageToGet = page.orElse(0);
        return quizService.getCompletionsOfUser(pageToGet, principal);
    }

    private Quiz getQuiz(long id) {
        return quizService.getQuiz(id)
                .orElseThrow(IndexOutOfBoundsException::new);
    }

    private static class AnswerWrapper {
        private final List<Integer> answer;

        @JsonCreator
        public AnswerWrapper(@JsonProperty("answer") List<Integer> answer) {
            this.answer = answer;
        }
    }
}
