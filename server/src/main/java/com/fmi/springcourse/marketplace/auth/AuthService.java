package com.fmi.springcourse.marketplace.auth;

import com.fmi.springcourse.marketplace.auth.dto.AuthResponseDTO;
import com.fmi.springcourse.marketplace.auth.dto.LoginRequestDTO;
import com.fmi.springcourse.marketplace.auth.dto.RegistrationRequestDTO;
import com.fmi.springcourse.marketplace.exception.UserAlreadyExistsException;
import com.fmi.springcourse.marketplace.user.UserRepository;
import com.fmi.springcourse.marketplace.user.dto.UserResponseDTO;
import com.fmi.springcourse.marketplace.user.entity.User;
import com.fmi.springcourse.marketplace.user.entity.UserRole;
import com.fmi.springcourse.marketplace.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repo;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getActive());
    }

    private User mapToUser(RegistrationRequestDTO dto) {
        return new User(dto.profileName(), dto.email(), dto.password(), UserRole.USER);
    }

    private LoginRequestDTO mapToLoginRequest(User user) {
        return new LoginRequestDTO(user.getEmail(), user.getPassword());
    }

//    @Transactional
    public void register(RegistrationRequestDTO request) {
        if (repo.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        User user = mapToUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));

        repo.save(user);
    }

//    @Transactional
    public AuthResponseDTO login(LoginRequestDTO request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails userDetails = userDetailsService.
                loadUserByUsername(request.email());

        String jwt = jwtService.generateToken(userDetails.getUsername());

        return new AuthResponseDTO(jwt);
    }
}
