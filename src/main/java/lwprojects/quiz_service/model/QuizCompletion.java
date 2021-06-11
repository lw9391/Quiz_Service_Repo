package lwprojects.quiz_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

/* DTO */
public class QuizCompletion {
    private final long id;
    @JsonIgnore
    private final String userEmail;
    private final LocalDateTime completedAt;

    public QuizCompletion(long id, String userEmail) {
        this.id = id;
        this.userEmail = userEmail;
        this.completedAt = LocalDateTime.now();
    }

    public QuizCompletion(long id, String userEmail, LocalDateTime completedAt) {
        this.id = id;
        this.userEmail = userEmail;
        this.completedAt = completedAt;
    }

    public QuizCompletion(CompletionsEntity entity) {
        this.id = entity.getQuiz().getId();
        this.userEmail = entity.getUser().getEmail();
        this.completedAt = entity.getCreatedAt();
    }

    /* Getters */
    public long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
