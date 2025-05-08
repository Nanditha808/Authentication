package loginRegister.com;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void register(RegisterReguest reguest) {
        if (userRepository.findByName(reguest.getName()).isPresent()) {
            throw new RuntimeException("User already present");
        }
        UserEntity user = new UserEntity(null, reguest.getName(), passwordEncoder.encode(reguest.getPassword()));
        userRepository.save(user);
    }

    public UserEntity authenticate(LoginRequest request) {
        UserEntity user = userRepository.findByName(request.getName()).orElseThrow(() -> new RuntimeException("Invalid Username"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return user;

    }
}
