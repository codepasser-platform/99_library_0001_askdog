package com.askdog.web.api.vo.search;

import java.util.Map;

public class SimilarTypeSearchParam extends TypeSearchParam {

    private String key;

    private int size = 5;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> params = super.getParams();
        params.put("key", getKey());
        return params;
    }
}
