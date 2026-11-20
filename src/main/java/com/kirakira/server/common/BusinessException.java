package com.kirakira.server.common;

/**
 * 业务异常：Service 里 throw 它，交给全局异常处理器转成统一响应。
 * 不要用 RuntimeException，否则分不清"业务失败"和"程序 bug"。
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(String message) {
        this(400, message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
