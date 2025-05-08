package loginRegister.com;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
        public ResponseEntity<String> register(@RequestBody @Valid RegisterReguest request){
        authService.register(request);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest request) {
        UserEntity user = authService.authenticate(request);
        String token = jwtUtil.generateToken(user.getName());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}