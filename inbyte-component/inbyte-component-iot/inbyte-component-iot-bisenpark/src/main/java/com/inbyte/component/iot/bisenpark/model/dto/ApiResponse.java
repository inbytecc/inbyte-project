package com.inbyte.component.iot.bisenpark.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * API通用响应包装类
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
public class ApiResponse<T> {

    /**
     * 响应状态码(0成功 1失败)
     */
    @JsonProperty("code")
    private Integer code;

    /**
     * 响应数据
     */
    @JsonProperty("data")
    private T data;

    /**
     * 错误信息
     */
    @JsonProperty("msg")
    private String msg;

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return Integer.valueOf(0).equals(code);
    }

    /**
     * 创建成功响应
     */
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(0);
        response.setData(data);
        return response;
    }

    /**
     * 创建失败响应
     */
    public static <T> ApiResponse<T> fail(String msg) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(1);
        response.setMsg(msg);
        return response;
    }
} 