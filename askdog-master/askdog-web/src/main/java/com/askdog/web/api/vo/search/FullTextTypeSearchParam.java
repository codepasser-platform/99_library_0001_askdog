package com.askdog.web.api.vo.search;

import java.util.Map;

public class FullTextTypeSearchParam extends TypeSearchParam {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> params = super.getParams();
        params.put("key", getKey());
        return params;
    }
}
