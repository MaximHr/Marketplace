package com.fmi.springcourse.marketplace.user;

import com.fmi.springcourse.marketplace.user.dto.AuthResponseDTO;
import com.fmi.springcourse.marketplace.user.dto.LoginRequestDTO;
import com.fmi.springcourse.marketplace.user.dto.RegistrationRequestDTO;
import com.fmi.springcourse.marketplace.user.dto.UserResponseDTO;
import com.fmi.springcourse.marketplace.user.dto.UserUpdateRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDTO registerUser(@Valid @RequestBody RegistrationRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/auth/login")
    public AuthResponseDTO loginUser(@Valid @RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @GetMapping("/users/{id}")
    public UserResponseDTO getUser(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}")
    public UserResponseDTO updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequestDTO request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
