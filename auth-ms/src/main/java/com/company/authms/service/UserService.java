package com.company.authms.service;

import com.company.authms.model.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<AuthResponse> register(RegisterRequest request);

    ResponseEntity<AuthResponse> login(LoginRequest request);

    UserInfo addRole(AddRoleRequest request);
}
