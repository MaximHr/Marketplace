package com.fmi.springcourse.marketplace.service;

import com.fmi.springcourse.marketplace.dto.user.UserResponseDTO;
import com.fmi.springcourse.marketplace.dto.user.UserUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(UUID id, UserUpdateDTO request);

    void deleteUser(UUID id);
}
