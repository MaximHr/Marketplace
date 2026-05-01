package com.fmi.springcourse.marketplace.dto.user;

public record AuthResponseDTO(String token,
                              UserResponseDTO response) {
}
