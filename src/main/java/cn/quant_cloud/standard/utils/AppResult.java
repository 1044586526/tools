package cn.quant_cloud.standard.utils;

import cn.quant_cloud.standard.base.AbstractResult;

import java.io.Serializable;

/**
 * @description: AppResult
 * @author ljh
 * @date 2024/1/23 15:10
 * @version 1.0
 */
public class AppResult<T> extends AbstractResult<T>   {
    private static final long serialVersionUID = 2372169689834212971L;
    private Integer code;
    private String message;
    private T data;

    public AppResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public AppResult() {
    }

    public static <T> AppResult<T> ok() {
        return new AppResult<>(200,"操作成功",null);
    }

    public static <T> AppResult<T> ok(T data) {
        return new AppResult<>(200,"操作成功",data);
    }

    public static <T> AppResult<T> fail(T data) {
        return new AppResult<>(400,"操作失败",data);
    }

    public static <T> AppResult<T> fail() {
        return new AppResult<>(400,"操作失败",null);
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
}