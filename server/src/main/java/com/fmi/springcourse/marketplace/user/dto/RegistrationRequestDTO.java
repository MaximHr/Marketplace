package com.fmi.springcourse.marketplace.user.dto;

public record RegistrationRequestDTO(String username,
                                     String email,
                                     String password) {
}
