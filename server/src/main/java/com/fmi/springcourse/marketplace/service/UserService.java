package com.fmi.springcourse.marketplace.service;

import com.fmi.springcourse.marketplace.dto.user.UserResponseDTO;
import com.fmi.springcourse.marketplace.dto.user.UserUpdateRequestDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO getUserById(UUID id);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(UUID id, UserUpdateRequestDTO request);

    void deleteUser(UUID id);
}
