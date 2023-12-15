package com.weng.openapibackend.common;

import lombok.Getter;

/**
 * 返回结果枚举类
 */
@Getter//4.提供实例变量的get方法
public enum ResultCodeEnum
{
    //1.创建对象(默认修饰符public static final ResultCodeEnum)
    SUCCESS(0,"success"),//0表示成功，其它都表示错误。通常情况下，return 0；表示成功，所以0表示成功
    PARAMS_ERROR(40000,"请求参数错误"),//400 Bad Request 客户端错误，客户端发送的请求无效
    NULL_ERROR(40001,"请求数据为空"),
    NOT_LOGIN_ERROR(40100,"未登录"),//401 Unauthorized 没有权限
    NO_AUTH_ERROR(40101,"没有权限"),
    FORBIDDEN_ERROR(40300, "禁止访问"),//403 Forbidden 禁止访问，表示服务器理解请求客户端的请求，但是拒绝执行这个请求
    NOT_FOUND_ERROR(40400, "请求数据不存在"),//404 Not Found 服务器无法找到请求的资源
    SYSTEM_ERROR(50000,"系统内部异常"),//500 Internal Server Error 服务器错误
    OPERATION_ERROR(50001, "操作失败");

    //2.声明当前类的对象的实例变量
    private final Integer code;
    private final String message;

    //3.私有化的构造器(默认修饰符private)
    ResultCodeEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
