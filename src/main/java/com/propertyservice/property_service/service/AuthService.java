package com.propertyservice.property_service.service;

import com.propertyservice.property_service.domain.office.OfficeUser;
import com.propertyservice.property_service.dto.auth.LoginRequest;
import com.propertyservice.property_service.dto.auth.LoginResponse;
import com.propertyservice.property_service.repository.office.OfficeUserRepository;
import com.propertyservice.property_service.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final OfficeUserRepository officeUserRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            OfficeUser user = officeUserRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            String token = jwtTokenProvider.createToken(
                    user.getEmail(),
                    user.getRole().name()
            );

            return new LoginResponse(
                    token,
                    user.getEmail(),
                    user.getName(),
                    user.getRole().getLabel()
            );

        } catch (AuthenticationException e) {
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
    }
}