package com.askdog.service.search;

import org.elasticsearch.action.search.SearchResponse;

public interface ResponseResolver {
    SearchResult resolve(SearchResponse searchResponse);
}
