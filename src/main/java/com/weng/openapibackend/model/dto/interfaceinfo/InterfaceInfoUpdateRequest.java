package com.weng.openapibackend.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
public record InterfaceInfoUpdateRequest(
        /**
         * 主键
         */
        @NotNull(message = "id不能为空")
        @Min(value = 1, message = "id必须大于0")
        Long id,

        /**
         * 接口名称
         */
        @Size(max = 50)
        String name,

        /**
         * 接口url地址
         */
        @Size(max = 200)
        String url,

        /**
         * 接口描述
         */
        @Size(max = 200)
        String description,

        /**
         * 请求类型
         */
        @Size(max = 10)
        String method,

        /**
         * 请求头
         */
        @Size(max = 500)
        String requestHeader,

        /**
         * 响应头
         */
        @Size(max = 500)
        String responseHeader,

        /**
         * 接口状态（0-默认关闭，1-开启）
         */
        @Min(0)
        @Max(1)
        Integer status
) {
}