package com.askdog.service.proxy;

import com.askdog.model.entity.User;
import com.askdog.service.SearchService;
import com.askdog.service.exception.SearchingException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.search.SearchRequest;
import com.askdog.service.search.SearchResult;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public SearchResult similar(@Valid SearchRequest searchRequest) throws SearchingException {
        return null;
    }

    @Override
    public SearchResult search(@Valid SearchRequest searchRequest) throws SearchingException {
        return null;
    }

    @Override
    public SearchResult questionNew(User user, @Valid SearchRequest searchRequest) throws ServiceException {
        return null;
    }

    @Override
    public SearchResult questionHot(User user, @Valid SearchRequest searchRequest) throws ServiceException {
        return null;
    }

    @Override
    public SearchResult answerNew(User user, @Valid SearchRequest searchRequest) throws ServiceException {
        return null;
    }

    @Override
    public SearchResult trends(User user, @Valid SearchRequest searchRequest) throws ServiceException {
        return null;
    }

    @Override
    public SearchResult waitingAnswer(User user, @Valid SearchRequest searchRequest) throws ServiceException {
        return null;
    }

    @Override
    public SearchResult related(String questionId, @Valid SearchRequest searchRequest) throws ServiceException {
        return null;
    }
}
