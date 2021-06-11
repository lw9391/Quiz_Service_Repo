package lwprojects.quiz_service.model;

import lwprojects.quiz_service.security.UserEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "Quiz")
public final class QuizEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String title;

    private String text;

    @SuppressWarnings("all")
    private String[] options;

    @SuppressWarnings("all")
    private int[] answers;

    @ManyToOne
    @JoinColumn
    private UserEntity creator;

    protected QuizEntity() {}

    public QuizEntity(String title, String text, String[] options, int[] answers) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answers = answers;
    }

    public QuizEntity(String title, String text, String[] options, int[] answers, UserEntity userEntity) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answers = answers;
        this.creator = userEntity;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public int[] getAnswers() {
        return answers;
    }

    public UserEntity getUser() {
        return creator;
    }
}
