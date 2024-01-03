package com.weng.openapibackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weng.openapibackend.common.ResultCodeEnum;
import com.weng.openapibackend.exception.BusinessException;
import com.weng.openapibackend.mapper.UserMapper;
import com.weng.openapibackend.model.dto.auth.LoginRequest;
import com.weng.openapibackend.model.dto.auth.RegisterRequest;
import com.weng.openapibackend.model.entity.User;
import com.weng.openapibackend.service.UserService;
import com.weng.openapibackend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author weng
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-01-01 16:53:33
*/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService
{
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;


    @Override
    public String login(LoginRequest loginRequest)
    {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        User user = (User) authenticationResponse.getPrincipal();
        return jwtUtil.generateToken(user);
    }

    @Override
    public Long register(RegisterRequest registerRequest)
    {
        //1.5密码和校验密码相同
        if (!Objects.equals(registerRequest.password(), registerRequest.checkPassword()))
        {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR,"两次密码输入不一致");
        }
        //1.6账号不能重复(查数据库)
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, registerRequest.username());
        Long count = userMapper.selectCount(userLambdaQueryWrapper);
        if (count > 0)
        {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR,"账号重复");
        }
        //3.存储到数据库
        User user = User.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))//todo role为null，是否会插入到数据库中
                .role(registerRequest.role()==null?null:registerRequest.role().name())//这里的role是枚举类型，name()方法返回枚举常量的名称
                .build();
        userMapper.insert(user);//如果插入失败，它会抛出异常.而不是返回一个负数

        return user.getId();
    }
}




