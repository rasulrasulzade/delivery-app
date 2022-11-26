package com.company.authms.service;

import com.company.authms.entity.RoleEntity;
import com.company.authms.entity.UserEntity;
import com.company.authms.exception.CustomException;
import com.company.authms.model.AuthResponse;
import com.company.authms.model.LoginRequest;
import com.company.authms.model.RegisterRequest;
import com.company.authms.repository.RoleRepository;
import com.company.authms.repository.UserRepository;
import com.company.authms.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new CustomException(request.getEmail() + " is already taken!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        RoleEntity role = roleRepository.findByName("USER");
        user.addRole(role);

        userRepository.save(user);

        AuthResponse authResponse = AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtUtil.generateAccessTokenFromUser(user));
        httpHeaders.add("X-refresh-token",jwtUtil.generateRefreshTokenFromUser(user));
        return new ResponseEntity<>(authResponse, httpHeaders, HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        UserEntity foundUser = userRepository.findByEmail(request.getEmail());

        if (foundUser == null) throw new CustomException("Invalid email address", HttpStatus.INTERNAL_SERVER_ERROR);

        if (!passwordEncoder.matches(request.getPassword(), foundUser.getPassword())) {
            throw new CustomException("Invalid password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);

        UserEntity user = (UserEntity) auth.getPrincipal();

        AuthResponse authResponse = AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtUtil.generateAccessTokenFromUser(user))
                .header("X-refresh-token","Bearer " + jwtUtil.generateRefreshTokenFromUser(user))
                .body(authResponse);
    }
}
