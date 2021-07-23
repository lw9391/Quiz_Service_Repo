package lwprojects.quiz_service.model;


import lwprojects.quiz_service.Quiz;
import lwprojects.quiz_service.QuizResp;
import lwprojects.quiz_service.security.UserEntity;
import lwprojects.quiz_service.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizMapper mapper;
    private final UserRepository userRepository;
    private final CompletionsRepository completionsRepository;

    @Autowired
    public QuizService(QuizRepository repository, QuizMapper mapper, UserRepository userRepository, CompletionsRepository completionsRepository) {
        this.quizRepository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.completionsRepository = completionsRepository;
    }

    public long saveQuiz(Quiz quiz, Principal principal) {
        String email = principal.getName();
        UserEntity userEntity = userRepository.findByEmail(email).get();
        QuizEntity entity = mapper.mapToEntity(quiz, userEntity);
        quizRepository.save(entity);
        return entity.getId();
    }

    public Optional<Quiz> getQuiz(long id) {
        return quizRepository.findById(id)
                .map(mapper::mapToDto);
    }

    public Page<Quiz> getAllQuizzes(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return quizRepository.findAll(pageable).map(mapper::mapToDto);
    }

    public boolean deleteQuiz(long id, Principal principal) {
        QuizEntity entity = quizRepository.findById(id)
                .orElseThrow(IndexOutOfBoundsException::new);

        if (!entity.getUser().getEmail().equals(principal.getName())) {
            return false;
        } else {
            quizRepository.deleteById(id);
            return true;
        }
    }

    public Page<QuizCompletion> getCompletionsOfUser(int page, Principal principal) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, 10, sort);
        String email = principal.getName();
        return completionsRepository.findAllByUserEmail(email, pageable)
                .map(QuizCompletion::new);
    }

    public QuizResp addCompletionsOfQuiz(long id, List<Integer> answer, Principal principal) {
        List<Integer> answerChecked = Objects.requireNonNullElseGet(answer, ArrayList::new);
        Quiz quiz = getQuiz(id).orElseThrow(IndexOutOfBoundsException::new);
        List<Integer> correctAnswer = quiz.getAnswer();
        if (correctAnswer.containsAll(answerChecked) && answerChecked.containsAll(correctAnswer)) {
            String email = principal.getName();
            QuizEntity quizEntity = quizRepository.findById(id).get();
            UserEntity user = userRepository.findByEmail(email).get();
            LocalDateTime completedAt = LocalDateTime.now();
            CompletionsEntity quizCompletion = new CompletionsEntity(quizEntity, user, completedAt);
            completionsRepository.save(quizCompletion);
            return QuizResp.CORRECT;
        } else {
            return QuizResp.INCORRECT;
        }

    }
}
