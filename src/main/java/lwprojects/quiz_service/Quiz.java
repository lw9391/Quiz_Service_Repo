package lwprojects.quiz_service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* DTO */
public class Quiz {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @NotBlank(message = "Title cannot be empty.")
    private String title;

    @NotBlank(message = "Text cannot be empty.")
    private String text;

    @Size(min = 2, message = "Quiz must contains at least 2 options.")
    @NotNull
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;


    public Quiz(long id, String title, String text, List<String> options, List<Integer> answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    @JsonCreator
    public Quiz(@JsonProperty("title") String title, @JsonProperty("text") String text, @JsonProperty("options") List<String> options, @JsonProperty("answer") List<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = Objects.requireNonNullElseGet(answer, ArrayList::new);
    }

    /* Getters & Setters */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = Objects.requireNonNullElseGet(answer, ArrayList::new);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
