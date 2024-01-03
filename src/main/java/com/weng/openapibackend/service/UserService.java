package com.weng.openapibackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weng.openapibackend.model.dto.auth.LoginRequest;
import com.weng.openapibackend.model.dto.auth.RegisterRequest;
import com.weng.openapibackend.model.entity.User;

/**
* @author weng
* @description 针对表【user】的数据库操作Service
* @createDate 2024-01-01 16:53:33
*/
public interface UserService extends IService<User> {
    String login(LoginRequest loginRequest);

    Long register(RegisterRequest registerRequest);
}
