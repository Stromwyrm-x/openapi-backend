package com.weng.openapibackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weng.openapibackend.model.entity.InterfaceInfo;
import com.weng.openapibackend.model.entity.User;

/**
* @author weng
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2024-01-01 16:53:33
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void isQualified(Long id);
}
