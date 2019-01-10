package com.askdog.web.api.client;

import com.askdog.web.api.client.extractor.ResponseEntityExtractor;
import com.askdog.web.api.vo.PostedAnswer;
import com.askdog.web.dsl.Chain;
import com.askdog.web.dsl.Injector;

public interface CreateAnswer {

    interface Action extends Chain {
        ResponseEntityExtractor create(PostedAnswer answer);
    }

    interface Api extends CreateAnswer.Action, Injector.CookieInjector<CreateAnswer.Action> {}
}
