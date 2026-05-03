package com.fmi.springcourse.marketplace.user.dto;

import com.fmi.springcourse.marketplace.user.entity.UserRole;

import java.util.UUID;

public record UserResponseDTO(UUID id,
                              String username,
                              String email,
                              UserRole role,
                              boolean active) {
}
