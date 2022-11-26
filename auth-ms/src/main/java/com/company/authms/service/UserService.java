package com.company.authms.service;

import com.company.authms.model.AuthResponse;
import com.company.authms.model.LoginRequest;
import com.company.authms.model.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<AuthResponse> register(RegisterRequest request);

    ResponseEntity<AuthResponse> login(LoginRequest request);
}
