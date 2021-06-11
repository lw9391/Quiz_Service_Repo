package lwprojects.quiz_service;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum QuizResp {
    CORRECT(true, "Congratulations, you're right!"),
    INCORRECT(false, "Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;

    QuizResp(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
