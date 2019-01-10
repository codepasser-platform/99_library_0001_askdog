package com.askdog.service.impl.search;

import com.askdog.model.redis.SerializableValueRedisTemplate;
import com.askdog.service.exception.SearchingException;
import com.askdog.service.impl.search.component.SearchRequestBuilderAdapter;
import com.askdog.service.search.ResponseResolver;
import com.askdog.service.search.SearchRequest;
import com.askdog.service.search.SearchResult;
import com.askdog.service.search.SearchAgent;
import com.google.common.collect.Maps;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Map;

import static com.askdog.common.utils.Json.writeValueAsString;
import static com.askdog.service.exception.SearchingException.Error.SEARCH_EXCEPTION;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Component
public class EsSearchAgent implements SearchAgent {

    private static Scroll scroll = new Scroll(TimeValue.parseTimeValue("1d", null, ""));
    private static ResponseResolver esResponseResolver = new EsResponseResolver();

    @Autowired private Client client;
    @Autowired private SerializableValueRedisTemplate<String> serializableRedisTemplate;

    @Override
    public SearchResult search(SearchRequest searchRequest) throws SearchingException {
        Assert.notNull(searchRequest);

        // disable scroll pagination
        searchRequest.setSearchId(null);

        try {
            SearchRequestBuilder searchRequestBuilder = createSearchRequestBuilder(searchRequest);
            SearchResult searchResult = esResponseResolver.resolve(searchRequestBuilder.get());

            initLastFlag(searchRequest, searchResult);

            if (!StringUtils.isEmpty(searchResult.getScrollId())) {
                serializableRedisTemplate.opsForValue().set(searchRequest.getSearchId(), searchResult.getScrollId());
                serializableRedisTemplate.expire(searchRequest.getSearchId(), scroll.keepAlive().getMillis(), MILLISECONDS);
            }
            return searchResult;

        } catch (Exception e) {
            throw new SearchingException(SEARCH_EXCEPTION, e);
        }
    }

    private void initLastFlag(SearchRequest searchRequest, SearchResult searchResult) {
        int from = Integer.valueOf(String.valueOf(searchRequest.getParams().get("from")));
        int size = Integer.valueOf(String.valueOf(searchRequest.getParams().get("size")));
        searchResult.setLast(from + size >= searchResult.getTotal());
    }

    private SearchRequestBuilder createSearchRequestBuilder(SearchRequest searchRequest) {
        SearchRequestBuilder searchRequestBuilder;

        String scrollId = searchRequest.getSearchId() == null ? null : serializableRedisTemplate.opsForValue().get(searchRequest.getSearchId());

        if (!StringUtils.isEmpty(scrollId)) {
            searchRequestBuilder = new SearchRequestBuilderAdapter(client, client.prepareSearchScroll(scrollId));

        } else {
            Map<String, Object> queryDslTemplate = Maps.newHashMap();
            queryDslTemplate.put("id", searchRequest.getType());
            queryDslTemplate.put("params", searchRequest.getParams());
            searchRequestBuilder = client.prepareSearch().setTemplateSource(writeValueAsString(queryDslTemplate));
        }

        if (!StringUtils.isEmpty(searchRequest.getSearchId())) {
            searchRequestBuilder.setScroll(scroll);
        }

        return searchRequestBuilder;
    }

}