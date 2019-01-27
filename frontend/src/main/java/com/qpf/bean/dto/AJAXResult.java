package com.qpf.bean.dto;

public class AJAXResult {
    private boolean success;
    private Object data;

    @Override
    public String toString() {
        return "AJAXResult{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
