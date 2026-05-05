package com.fmi.springcourse.marketplace.auth;

import com.fmi.springcourse.marketplace.auth.dto.AuthResponseDTO;
import com.fmi.springcourse.marketplace.auth.dto.LoginRequestDTO;
import com.fmi.springcourse.marketplace.auth.dto.RegistrationRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequestDTO request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO user = authService.login(request);
        return ResponseEntity.ok(user);
    }
}
