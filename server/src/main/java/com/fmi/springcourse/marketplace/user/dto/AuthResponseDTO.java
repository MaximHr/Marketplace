package com.fmi.springcourse.marketplace.user.dto;

public record AuthResponseDTO(String token,
                              UserResponseDTO response) {
}
