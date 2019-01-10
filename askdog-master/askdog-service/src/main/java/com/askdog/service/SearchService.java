package com.askdog.service;

import com.askdog.model.entity.User;
import com.askdog.service.exception.SearchingException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.search.SearchRequest;
import com.askdog.service.search.SearchResult;

import javax.validation.Valid;

public interface SearchService {

    SearchResult similar(@Valid SearchRequest searchRequest) throws SearchingException;

    SearchResult search(@Valid SearchRequest searchRequest) throws SearchingException;

    SearchResult questionNew(User user, @Valid SearchRequest searchRequest) throws ServiceException;

    SearchResult questionHot(User user, @Valid SearchRequest searchRequest) throws ServiceException;

    SearchResult answerNew(User user, @Valid SearchRequest searchRequest) throws ServiceException;

    SearchResult trends(User user, @Valid SearchRequest searchRequest) throws ServiceException;

    SearchResult waitingAnswer(User user, @Valid SearchRequest searchRequest) throws ServiceException;

    SearchResult related(String questionId, @Valid SearchRequest searchRequest) throws ServiceException;
}
