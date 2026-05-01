package com.fmi.springcourse.marketplace.service.impl;

import com.fmi.springcourse.marketplace.dto.user.AuthResponseDTO;
import com.fmi.springcourse.marketplace.dto.user.LoginRequestDTO;
import com.fmi.springcourse.marketplace.dto.user.RegistrationRequestDTO;
import com.fmi.springcourse.marketplace.dto.user.UserResponseDTO;
import com.fmi.springcourse.marketplace.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthStubServiceImpl implements AuthService {
    @Override
//    @Transactional
    public UserResponseDTO register(RegistrationRequestDTO request) {
        return new UserResponseDTO(
                UUID.randomUUID(),
                request.username(),
                request.email()
        );
    }

    @Override
//    @Transactional
    public AuthResponseDTO login(LoginRequestDTO request) {
        UserResponseDTO fakeUser = new UserResponseDTO(
                UUID.randomUUID(),
                "StubUser",
                request.email()
        );

        String fakeJwt = "eyJhbGciOiJIUzI1NiJ9.stub-token-not-real";

        return new AuthResponseDTO(fakeJwt, fakeUser);
    }
}
