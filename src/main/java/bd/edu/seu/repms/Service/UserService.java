package bd.edu.seu.repms.Service;

import bd.edu.seu.repms.Entity.User;
import bd.edu.seu.repms.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {

        // Password encode করে database-এ save করা
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        // Default role USER
        user.setRole(
                bd.edu.seu.repms.Entity.Role.USER
        );

        return userRepository.save(user);
    }
}