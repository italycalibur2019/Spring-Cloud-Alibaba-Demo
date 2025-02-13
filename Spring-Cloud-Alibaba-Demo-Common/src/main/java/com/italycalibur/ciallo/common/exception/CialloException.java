package com.italycalibur.ciallo.common.exception;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-14 02:04:58
 * @description: 自定义异常
 */
public class CialloException extends RuntimeException {
    public CialloException() {
    }

    public CialloException(String message) {
        super(message);
    }

    public CialloException(String message, Throwable cause) {
        super(message, cause);
    }
}
