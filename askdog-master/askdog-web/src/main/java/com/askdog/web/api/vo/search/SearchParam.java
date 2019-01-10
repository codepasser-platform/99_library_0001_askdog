package com.askdog.web.api.vo.search;

import com.google.common.collect.Maps;

import java.util.Map;

public class SearchParam {

    private String searchId;
    private boolean tailored = true;
    private int from;
    private int size;

    public boolean isTailored() {
        return tailored;
    }

    public void setTailored(boolean tailored) {
        this.tailored = tailored;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public Map<String, Object> getParams() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("from", getFrom());
        params.put("size", getSize());
        params.put("tailored", isTailored());
        return params;
    }
}
