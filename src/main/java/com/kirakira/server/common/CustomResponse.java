package com.kirakira.server.common;

/**
 * 统一响应体，和前端约定一致：{ code, message, data }
 * code = 200 表示成功，其它表示业务错误。
 */
public class CustomResponse {

    private int code;
    private String message;
    private Object data;

    public CustomResponse() {
    }

    public CustomResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static CustomResponse ok(Object data) {
        return new CustomResponse(200, "OK", data);
    }

    public static CustomResponse fail(int code, String message) {
        return new CustomResponse(code, message, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
