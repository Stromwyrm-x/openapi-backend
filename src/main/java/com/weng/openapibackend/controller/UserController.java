package com.weng.openapibackend.controller;

import com.weng.openapibackend.common.Result;
import com.weng.openapibackend.model.entity.User;
import com.weng.openapibackend.model.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController
{
    /**
     * 获取当前登录用户
     *
     * @param
     * @return
     */
    @GetMapping("/current")
    public Result<UserVO> getCurrentUser(@AuthenticationPrincipal User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return Result.success(userVO);
    }
}
