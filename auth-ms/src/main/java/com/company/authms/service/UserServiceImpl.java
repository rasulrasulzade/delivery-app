package com.company.authms.service;

import com.company.authms.entity.RoleEntity;
import com.company.authms.entity.UserEntity;
import com.company.authms.enums.RoleName;
import com.company.authms.exception.CustomException;
import com.company.authms.model.*;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        user.addRole(getRole(RoleName.USER.name()));
        userRepository.save(user);

        AuthResponse authResponse = AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtUtil.generateAccessTokenFromUser(user));
        httpHeaders.add("X-refresh-token", jwtUtil.generateRefreshTokenFromUser(user));
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
                .header("X-refresh-token", "Bearer " + jwtUtil.generateRefreshTokenFromUser(user))
                .body(authResponse);
    }

    private Boolean userHasThisRole(AddRoleRequest request){
        Optional<UserEntity> userEnt = userRepository.findById(request.getUserId());
        if (userEnt.isEmpty()) return Boolean.FALSE;

        return userEnt.get().getRoles().stream()
                .map(r -> r.getName().equals(request.getRoleName()))
                .filter(b -> b.equals(Boolean.TRUE))
                .findFirst()
                .orElse(Boolean.FALSE);
    }

    private RoleEntity getRole(String roleName) {
        return Optional.ofNullable(roleRepository.findByName(roleName))
                .orElseThrow(() -> new CustomException("Invalid role name", HttpStatus.BAD_REQUEST));
    }

    @Override
    public UserInfo addRole(AddRoleRequest request) {
        UserEntity userEnt = Optional.of(userRepository.findById(request.getUserId()))
                .get()
                .orElseThrow(() -> new CustomException("User Not found", HttpStatus.NOT_FOUND));
        if (userHasThisRole(request)) {
            throw new CustomException("User has already '" + request.getRoleName() + "' role", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        RoleEntity roleEnt = getRole(request.getRoleName());
        userEnt.addRole(roleEnt);
        userRepository.save(userEnt);
        return UserInfo.builder()
                .id(userEnt.getId())
                .firstName(userEnt.getFirstName())
                .lastName(userEnt.getLastName())
                .email(userEnt.getEmail())
                .roles(userEnt.getRoles().stream()
                        .map(RoleEntity::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
