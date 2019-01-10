package com.askdog.service.search;

import com.askdog.service.exception.SearchingException;

public interface SearchAgent {
    SearchResult search(SearchRequest searchRequest) throws SearchingException;
}
