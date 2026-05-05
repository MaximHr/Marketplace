package com.fmi.springcourse.marketplace.auth.dto;

public record RegistrationRequestDTO(String profileName,
                                     String email,
                                     String password) {
}
