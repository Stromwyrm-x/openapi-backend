package com.weng.openapibackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weng.openapibackend.common.PageRequest;
import com.weng.openapibackend.common.Result;
import com.weng.openapibackend.common.ResultCodeEnum;
import com.weng.openapibackend.context.UserContext;
import com.weng.openapibackend.exception.BusinessException;
import com.weng.openapibackend.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.weng.openapibackend.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.weng.openapibackend.model.entity.InterfaceInfo;
import com.weng.openapibackend.service.InterfaceInfoService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 帖子接口
 *
 * @author yupi
 */
@RestController
@RequestMapping("/interfaceInfos")
@Slf4j
public class InterfaceInfoController
{
    @Resource
    private InterfaceInfoService interfaceInfoService;

    // region 增删改查

    /**
     * 增加
     *
     * @param interfaceInfoAddRequest
     * @return
     */
    @PostMapping
    public Result<Long> addInterfaceInfo(@Validated @RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest) {
        /**
         * controller层倾向于对请求参数本身的校验(所有都要)，不涉及业务逻辑本身(越少越好)
         * service层是对业务逻辑的校验（有可能被controller 之外的类调用)
         */
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        //新增
        interfaceInfo.setUserId(UserContext.getUserId());
        interfaceInfoService.save(interfaceInfo);
        return Result.success(interfaceInfo.getId());
    }

    /**
     * 删除
     *
     * @return
     */
    @DeleteMapping("/{id}")//因为是路径参数，所以id一定会有值，不可能为null，所以这里校验是否大于0即可
    public Result<Boolean> deleteInterfaceInfo(@Min(1) @PathVariable Long id) {
//        if (id == null || id <= 0) {
//            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
//        }
        //判断是否能够删除
        interfaceInfoService.isQualified(id);

        boolean result = interfaceInfoService.removeById(id);
        return Result.success(result);
    }

    /**
     * 修改
     *
     * @param interfaceInfoUpdateRequest
     * @return
     */
    @PutMapping
    public Result<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        //参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo);
        //判断是否能够修改
        interfaceInfoService.isQualified(interfaceInfo.getId());

        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return Result.success(result);
    }

    /**
     * 根据 id 查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<InterfaceInfo> getInterfaceInfoById(@Min(1) @PathVariable Long id) {
      /*  if (id <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }*/
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        return Result.success(interfaceInfo);
    }

    /**
     * 获取列表
     *
     * @return
     */
//    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public Result<List<InterfaceInfo>> listInterfaceInfo() {
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list();
        return Result.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param pageRequest
     * @return
     */
    @GetMapping("/page")
    public Result<Page<InterfaceInfo>> listInterfaceInfoByPage(PageRequest pageRequest) {
        if (pageRequest == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        LambdaQueryWrapper<InterfaceInfo>interfaceInfoLambdaQueryWrapper=new LambdaQueryWrapper<>();
        interfaceInfoLambdaQueryWrapper.orderByDesc(InterfaceInfo::getCreateTime);
        Page<InterfaceInfo>interfaceInfoPage=new Page<>(pageRequest.getCurrent(),pageRequest.getSize());
        interfaceInfoService.page(interfaceInfoPage,interfaceInfoLambdaQueryWrapper);
        return Result.success(interfaceInfoPage);
    }

    // endregion

}
