package com.fmi.springcourse.marketplace.user;

import com.fmi.springcourse.marketplace.user.dto.AuthResponseDTO;
import com.fmi.springcourse.marketplace.user.dto.LoginRequestDTO;
import com.fmi.springcourse.marketplace.user.dto.RegistrationRequestDTO;
import com.fmi.springcourse.marketplace.user.dto.UserResponseDTO;
import com.fmi.springcourse.marketplace.user.entity.User;
import com.fmi.springcourse.marketplace.user.entity.UserRole;
import com.fmi.springcourse.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repo;

    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getActive());
    }

//    @Transactional
    public AuthResponseDTO register(RegistrationRequestDTO request) {
        throw new NoSuchElementException("To be implemented");
    }

//    @Transactional
    public AuthResponseDTO login(LoginRequestDTO request) {
        throw new NoSuchElementException("To be implemented");
    }
}
