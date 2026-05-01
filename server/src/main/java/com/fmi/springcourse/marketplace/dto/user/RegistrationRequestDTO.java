package com.fmi.springcourse.marketplace.dto.user;

public record RegistrationRequestDTO(String username,
                                     String email,
                                     String password) {
}
