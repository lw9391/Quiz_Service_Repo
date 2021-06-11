package lwprojects.quiz_service.security;

import lwprojects.quiz_service.model.QuizEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "User")
public class UserEntity {

    @Id
    private String email;

    private String password;

    private boolean active;

    private String roles;

    @OneToMany(mappedBy = "creator")
    private List<QuizEntity> createdQuizzes;

    /* Getters and setters */
    public String getEmail() {
        return email;
    }

    public void setEmail(String userName) {
        this.email = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<QuizEntity> getCreatedQuizzes() {
        return createdQuizzes;
    }

    public void setCreatedQuizzes(List<QuizEntity> createdQuizzes) {
        this.createdQuizzes = createdQuizzes;
    }
}
