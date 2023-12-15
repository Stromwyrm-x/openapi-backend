package com.weng.openapibackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weng.openapibackend.model.entity.InterfaceInfo;
import com.weng.openapibackend.model.entity.InterfaceInfo;

/**
* @author weng
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2023-12-14 18:18:42
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo);

    void isQualified(Long id);
}
