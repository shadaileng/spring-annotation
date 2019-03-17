package com.qpf.website.commons.dto;

import java.util.List;

public class PageInfo<T> {
    private Integer total;
    private List<T> data;
    private String error;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "total=" + total +
                ", data=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
