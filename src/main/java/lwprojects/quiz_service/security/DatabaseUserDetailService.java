package lwprojects.quiz_service.security;

import lwprojects.quiz_service.login.User;
import lwprojects.quiz_service.login.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public DatabaseUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(DatabaseUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Such user doesn't exists"));
    }

    public void registerNewUser(User user) {
        if (userRepository.existsUserEntityByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        UserEntity entity = new UserEntity();
        entity.setEmail(user.getEmail());
        entity.setPassword(encoder.encode(user.getPassword()));
        entity.setActive(true);
        entity.setRoles("USER");
        userRepository.save(entity);
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }
}
