package com.weng.openapibackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weng.openapibackend.common.ResultCodeEnum;
import com.weng.openapibackend.context.UserContext;
import com.weng.openapibackend.exception.BusinessException;
import com.weng.openapibackend.mapper.InterfaceInfoMapper;
import com.weng.openapibackend.model.entity.InterfaceInfo;
import com.weng.openapibackend.model.entity.InterfaceInfo;
import com.weng.openapibackend.service.InterfaceInfoService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author weng
* @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
* @createDate 2023-12-14 18:18:42
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService
{
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo)
    {
        if (interfaceInfo == null) {
            throw new BusinessException(ResultCodeEnum.NULL_ERROR);
        }
        //todo 校验
//        ObjectUtils.anyNotNull(interfaceInfo);
//        String name = interfaceInfo.getName();
//        String url = interfaceInfo.getUrl();
//        String method = interfaceInfo.getMethod();
//        String description = interfaceInfo.getDescription();
//        String requestHeader = interfaceInfo.getRequestHeader();
//        String responseHeader = interfaceInfo.getResponseHeader();
//        Integer status = interfaceInfo.getStatus();
//
//
//
//        /* 所有参数必须非空 */
//        if (StringUtils.isAnyBlank(name, url, method, description, requestHeader, responseHeader) || ObjectUtils.anyNull(status)) {
//            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
//        }
    }

    @Override
    public void isQualified(Long id)
    {
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoMapper.selectById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ResultCodeEnum.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(UserContext.getUserId()) /*&& !userService.isAdmin(request)*/) {
            throw new BusinessException(ResultCodeEnum.NO_AUTH_ERROR);
        }
    }
}




