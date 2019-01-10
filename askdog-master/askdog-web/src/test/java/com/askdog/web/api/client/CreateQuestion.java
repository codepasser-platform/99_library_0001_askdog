package com.askdog.web.api.client;

import com.askdog.service.bo.PureQuestion;
import com.askdog.web.api.client.extractor.ResponseBodyExtractor;
import com.askdog.web.api.vo.PresentationQuestion;
import com.askdog.web.dsl.Chain;
import com.askdog.web.dsl.Injector;

public interface CreateQuestion {

    interface Action extends Chain {
        ResponseBodyExtractor<PresentationQuestion> create(PureQuestion pureQuestion);
    }

    interface Api extends Action, Injector.CookieInjector<Action> {}

}
