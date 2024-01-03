package com.weng.openapibackend.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record LoginRequest(
        @NotBlank(message = "用户名不能为空")
        @Pattern(regexp = "^[a-zA-Z0-9_]{4,16}$", message = "用户名格式不正确")//太优雅了！elegant!
        String username,

        @NotBlank(message = "密码不能为空")
        @Pattern(regexp = "^[a-zA-Z0-9_]{4,16}$", message = "密码格式不正确")
        String password
) {
}
