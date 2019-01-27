package com.qpf.bean.dto;

import java.util.List;

public class Page<T> {
    private int index;
    private int pageSize;
    private int total;
    private int pageCount;
    private List<T> data;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return "Page{" +
                "index=" + index +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", pageCount=" + pageCount +
                ", data=" + data +
                '}';
    }
}
