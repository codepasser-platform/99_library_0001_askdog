package com.askdog.web.api.vo.common;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResult<E> {

    private List<E> result;
    private long total;
    private boolean isLast;

    public PageResult(List<E> result, long total, boolean isLast) {
        this.result = result;
        this.total = total;
        this.isLast = isLast;
    }

    public List<E> getResult() {
        return result;
    }

    public void setResult(List<E> result) {
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
