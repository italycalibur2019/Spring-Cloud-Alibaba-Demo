package com.italycalibur.ciallo.common.domain;

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
}
