package com.fmi.springcourse.marketplace.service;

import com.fmi.springcourse.marketplace.dto.user.AuthResponseDTO;
import com.fmi.springcourse.marketplace.dto.user.LoginRequestDTO;
import com.fmi.springcourse.marketplace.dto.user.RegistrationRequestDTO;
import com.fmi.springcourse.marketplace.dto.user.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(RegistrationRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
}
