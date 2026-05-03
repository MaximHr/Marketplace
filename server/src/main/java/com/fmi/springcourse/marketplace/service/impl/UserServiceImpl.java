package com.fmi.springcourse.marketplace.service.impl;

import com.fmi.springcourse.marketplace.dto.user.UserResponseDTO;
import com.fmi.springcourse.marketplace.dto.user.UserUpdateRequestDTO;
import com.fmi.springcourse.marketplace.exception.UserNotActiveException;
import com.fmi.springcourse.marketplace.exception.UserNotFoundException;
import com.fmi.springcourse.marketplace.model.entity.User;
import com.fmi.springcourse.marketplace.repository.UserRepository;
import com.fmi.springcourse.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getActive());
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        Optional<User> userOpt = repo.findById(id);


        User user = userOpt.orElseThrow(
                () -> new UsernameNotFoundException("User with ID: " + id + " was not found")
        );

        return mapToResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return repo.findAllByActiveIsTrue()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
//    @Transactional
    public UserResponseDTO updateUser(UUID id, UserUpdateRequestDTO request) {
        Optional<User> userOpt = repo.findById(id);


        User user = userOpt.orElseThrow(
                () -> new UserNotFoundException("User with ID: " + id + " was not found")
        );

        if (!user.getActive()) {
            throw new UserNotActiveException("User is already deleted");
        }

        if (request.username() != null) user.setUsername(request.username());
        if (request.email() != null) user.setEmail(request.email());

        User updated = repo.save(user);
        return mapToResponseDTO(updated);
    }


    @Override
//    @Transactional
    public void deleteUser(UUID id) {
        repo.findById(id).ifPresentOrElse(
                user -> user.setActive(false),
                () -> { throw new UsernameNotFoundException("User with ID: " + id + " was not found"); }
        );
    }
}
