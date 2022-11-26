package com.company.authms.controller;

import com.company.authms.model.AuthResponse;
import com.company.authms.model.LoginRequest;
import com.company.authms.model.RegisterRequest;
import com.company.authms.service.UserService;
import com.company.authms.util.HttpUtil;
import com.company.authms.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpUtil httpUtil;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/refresh/token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = jwtUtil.generateAccessTokenFromHeader(request.getHeader("X-refresh-token"));
        response.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        httpUtil.printResponse(response, new HashMap<>(), HttpServletResponse.SC_OK);
    }
}
