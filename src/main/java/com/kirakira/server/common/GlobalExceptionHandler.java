package com.kirakira.server.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理：把 Java 异常转成前端认识的 {code, message, data}。
 * 没有它，报错时 Spring 会返回默认的错误页，前端拿不到统一格式。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** 业务异常：Service 主动抛的，文案可以直接给用户看 */
    @ExceptionHandler(BusinessException.class)
    public CustomResponse handleBusiness(BusinessException e) {
        return CustomResponse.fail(e.getCode(), e.getMessage());
    }

    /** 数据库唯一键冲突：比如两个请求同时注册同一个用户名 */
    @ExceptionHandler(DuplicateKeyException.class)
    public CustomResponse handleDuplicate(DuplicateKeyException e) {
        log.warn("唯一键冲突", e);
        return CustomResponse.fail(400, "数据已存在（用户名可能被占用）");
    }

    /** 兜底：任何没预料到的异常，不要把堆栈抛给前端 */
    @ExceptionHandler(Exception.class)
    public CustomResponse handleOther(Exception e) {
        log.error("未处理的异常", e);
        return CustomResponse.fail(500, "服务器开小差了");
    }
}
