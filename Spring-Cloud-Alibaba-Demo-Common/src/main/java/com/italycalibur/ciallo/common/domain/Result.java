package com.italycalibur.ciallo.common.domain;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.italycalibur.ciallo.common.constants.ResultCode;

/**
 * @description: 统一返回结果
 * @author dhr
 * @date 2025-02-06 14:26:42
 * @version 1.0
 */
public record Result<T>(Boolean success, Integer code, String message, T data) {
    public static <T> Result<T> ok(T data) {
        return new Result<>(Boolean.TRUE, ResultCode.SUCCESS, "操作成功", data);
    }

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(Boolean.FALSE, code, message, null);
    }

    public static <T> Result<T> error(String message) {
        return error(ResultCode.FAILURE, message);
    }

    public static <T> Result<T> unauthorized() {
        return error(ResultCode.UNAUTHORIZED, "用户未登录或登录超时，请重新登录！");
    }

    public static <T> Result<T> forbidden() {
        return error(ResultCode.FORBIDDEN, "用户无权限，禁止访问！");
    }

    public String asJsonString() {
        return JSON.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}
