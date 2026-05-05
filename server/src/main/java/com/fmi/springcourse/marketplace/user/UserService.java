package com.fmi.springcourse.marketplace.user;

import com.fmi.springcourse.marketplace.exception.UserNotActiveException;
import com.fmi.springcourse.marketplace.exception.UserNotFoundException;
import com.fmi.springcourse.marketplace.user.dto.UserResponseDTO;
import com.fmi.springcourse.marketplace.user.dto.UserUpdateRequestDTO;
import com.fmi.springcourse.marketplace.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;

    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getActive());
    }

    public UserResponseDTO getUserByEmail(String email) {
        return repo.findByEmail(email)
                .map(UserResponseDTO::new)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " was not found"));
    }

    public UserResponseDTO getUserById(UUID id) {
        Optional<User> userOpt = repo.findById(id);


        User user = userOpt.orElseThrow(
                () -> new UsernameNotFoundException("User with ID: " + id + " was not found")
        );

        return mapToResponseDTO(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        return repo.findAllByActiveIsTrue()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

//    @Transactional
    public UserResponseDTO updateUser(UUID id, UserUpdateRequestDTO request) {
        Optional<User> userOpt = repo.findById(id);


        User user = userOpt.orElseThrow(
                () -> new UserNotFoundException("User with ID: " + id + " was not found")
        );

        if (!user.getActive()) {
            throw new UserNotActiveException("Cannot update an inactive user account");
        }

        if (request.profileName() != null) user.setProfileName(request.profileName());
        if (request.email() != null) user.setEmail(request.email());

        User updated = repo.save(user);
        return mapToResponseDTO(updated);
    }

//    @Transactional
    public void deleteUser(UUID id) {
        repo.findById(id).ifPresentOrElse(
                user -> user.setActive(false),
                () -> { throw new UsernameNotFoundException("User with ID: " + id + " was not found"); }
        );
    }

    // ??

}
