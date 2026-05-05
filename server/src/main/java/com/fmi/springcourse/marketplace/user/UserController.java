package com.fmi.springcourse.marketplace.user;

import com.fmi.springcourse.marketplace.auth.AuthService;
import com.fmi.springcourse.marketplace.auth.dto.AuthResponseDTO;
import com.fmi.springcourse.marketplace.auth.dto.LoginRequestDTO;
import com.fmi.springcourse.marketplace.auth.dto.RegistrationRequestDTO;
import com.fmi.springcourse.marketplace.exception.UserNotFoundException;
import com.fmi.springcourse.marketplace.user.dto.UserResponseDTO;
import com.fmi.springcourse.marketplace.user.dto.UserUpdateRequestDTO;
import com.fmi.springcourse.marketplace.util.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    // TO BE DELETED
    @GetMapping("/")
    public String home() {
        return "<h1>Welcome</h1>";
    }

    @GetMapping("/user")
    public ResponseEntity<?> user(@RequestHeader("Authorization") String authToken) {
        String email = jwtService.extractUsername(authToken.substring(7));
        UserResponseDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin")
    public String admin() {
        return "<h1>Welcome Admin</h1>";
    }

    // Still not decided if should delete
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
