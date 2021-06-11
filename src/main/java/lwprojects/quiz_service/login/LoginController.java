package lwprojects.quiz_service.login;

import lwprojects.quiz_service.security.DatabaseUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;


@RestController
public class LoginController {
    private final DatabaseUserDetailService service;

    private static final String ALREADY_EXISTS = "User with that email already exists.";

    @Autowired
    public LoginController(DatabaseUserDetailService service) {
        this.service = service;
    }

    @PostMapping(path = "/api/register")
    public void registerUser(@Valid @RequestBody User user) {
        service.registerNewUser(user);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ALREADY_EXISTS)
    private HashMap<String, String> handleUserAlreadyExistsException(Exception e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", ALREADY_EXISTS);
        response.put("error", e.getClass().getSimpleName());
        return response;
    }

}
