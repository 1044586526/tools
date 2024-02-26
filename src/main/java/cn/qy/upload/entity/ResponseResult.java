package cn.qy.upload.entity;


import cn.qy.upload.entity.enums.AppHttpCodeEnum;

import java.io.Serializable;

public class ResponseResult implements Serializable {
    private Integer status;
    private Integer code;
    private String message;
    private Object data;

    public ResponseResult(Integer status, Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseResult ok() {
        AppHttpCodeEnum hc = AppHttpCodeEnum.SUCCESS;
        return new ResponseResult(hc.getStatus(), hc.getCode(), hc.getMessage(), null);
    }

    public static ResponseResult ok(Object data) {
        ResponseResult result = ok();
        result.setData(data);
        return result;
    }

    public static ResponseResult fail() {
        return fail(AppHttpCodeEnum.SERVER_ERROR);
    }

    public static ResponseResult fail(AppHttpCodeEnum hc) {
        return new ResponseResult(hc.getStatus(), hc.getCode(), hc.getMessage(), null);
    }

    public static ResponseResult fail(String msg) {
        return fail().msg(msg);
    }

    public ResponseResult msg(String message) {
        setMessage(message);
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}