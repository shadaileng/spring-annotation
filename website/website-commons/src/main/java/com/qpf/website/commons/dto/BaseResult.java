package com.qpf.website.commons.dto;

import java.io.Serializable;

public class BaseResult implements Serializable {
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_FAILED = 500;
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
    public BaseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static BaseResult success(int code, String msg) {
        return new BaseResult(code, msg);
    }
    public static BaseResult success(String msg) {
        return success(STATUS_SUCCESS, msg);
    }
    public static BaseResult success() {
        return success("操作成功");
    }
    public static BaseResult failed(int code, String msg) {
        return new BaseResult(code, msg);
    }
    public static BaseResult failed(String msg) {
        return failed(STATUS_FAILED, msg);
    }
    public static BaseResult failed() {
        return failed("操作失败");
    }
}
