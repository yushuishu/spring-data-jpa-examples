package com.shuishu.demo.jpa.common.config.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:32
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class ApiResponse<T> implements Serializable {
    /**
     * 解释各字段的意思
     */
    @ApiModelProperty(value = "响应码", dataType = "String", example = "0")
    private int code;

    @ApiModelProperty(value = "提示信息", dataType = "String", example = "请求成功")
    private String message;
    
    @ApiModelProperty(value = "返回值", dataType = "String")
    private T data;

    public ApiResponse() {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ApiResponse(Type type, String message) {
        this.code = type.value;
        this.message = message;
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public ApiResponse(Type type, String message, T data) {
        this.code = type.value;
        this.message = message;
        if (data != null) {
            this.data = data;
        }

    }

    public static <T> ApiResponse<T> instance(int code, String message) {
        return new ApiResponse<T>(code, message);
    }

    public static <T> ApiResponse<T> instance(T data) {
        return new ApiResponse<T>(data, 200, "操作成功");
    }

    public static <T> ApiResponse<T> instance(T data, int code, String message) {
        return new ApiResponse<T>(data, code, message);
    }

    public static <T> ApiResponse<T> of(int code, String message) {
        return new ApiResponse<T>(code, message);
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<T>(data, 200, "操作成功");
    }

    public static <T> ApiResponse<T> of(T data, int code, String message) {
        return new ApiResponse<T>(data, code, message);
    }


    /**
     * 静态方法要使用泛型参数的话，要声明其为泛型方法
     *
     * @param <T> T
     * @return T
     */
    public static <T> ApiResponse<T> success() {
        return success("操作成功");
    }

    public static <T> ApiResponse<T> success(T data) {
        return success("操作成功", data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return success(message, (T) null);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<T>(Type.SUCCESS, message, data);
    }

    public static <T> ApiResponse<T> warn(String message) {
        return warn(message, (T) null);
    }

    public static <T> ApiResponse<T> warn(String message, T data) {
        return new ApiResponse<T>(Type.WARN, message, data);
    }


    public static <T> ApiResponse<T> unAuth() {
        return new ApiResponse<T>(Type.UN_AUTH, "未登陆", (T) null);
    }

    public static <T> ApiResponse<T> forbidden() {
        return new ApiResponse<T>(Type.FORBIDDEN, "禁止访问", (T) null);
    }

    public static <T> ApiResponse<T> error() {
        return error("操作失败");
    }

    public static <T> ApiResponse<T> error(String message) {
        return error(message, (T) null);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<T>(Type.ERROR, message, data);
    }

    public static enum Type {
        SUCCESS(200),
        UN_AUTH(401),
        WARN(402),
        FORBIDDEN(403),
        ERROR(500);

        private final int value;

        private Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
