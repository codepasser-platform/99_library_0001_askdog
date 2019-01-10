package com.askdog.web.api.vo.search;

import com.askdog.common.In;
import com.askdog.service.search.SearchRequest;

import javax.validation.constraints.NotNull;

public class TypeSearchParam extends SearchParam implements In<SearchRequest> {

    @NotNull
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public SearchRequest convert() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setType(getType());
        searchRequest.setSearchId(getSearchId());
        searchRequest.setParams(getParams());
        return searchRequest;
    }
}
