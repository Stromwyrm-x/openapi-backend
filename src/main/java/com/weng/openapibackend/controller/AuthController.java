package com.weng.openapibackend.controller;

import com.weng.openapibackend.common.Result;
import com.weng.openapibackend.model.dto.auth.LoginRequest;
import com.weng.openapibackend.model.dto.auth.RegisterRequest;
import com.weng.openapibackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final UserService userService;
    @PostMapping("/login")
    public Result<String> login(@RequestBody @Validated LoginRequest loginRequest)
    {
        String token = userService.login(loginRequest);
        return Result.success(token);
    }

    @PostMapping("/register")
    public Result<Long> register(@RequestBody @Validated RegisterRequest registerRequest)
    {
        Long id=userService.register(registerRequest);
        return Result.success(id);
    }

}
