package lwprojects.quiz_service.model;

import lwprojects.quiz_service.security.UserEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity(name = "Completions")
public class CompletionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuizEntity quiz;

    @ManyToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    private LocalDateTime createdAt;

    public CompletionsEntity(long id, QuizEntity quiz, UserEntity user, LocalDateTime createdAt) {
        this.id = id;
        this.quiz = quiz;
        this.user = user;
        this.createdAt = createdAt;
    }

    public CompletionsEntity(QuizEntity quiz, UserEntity user, LocalDateTime createdAt) {
        this.quiz = quiz;
        this.user = user;
        this.createdAt = createdAt;
    }

    protected CompletionsEntity() {}

    /* Getters & Setters */
    public long getId() {
        return id;
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public UserEntity getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
