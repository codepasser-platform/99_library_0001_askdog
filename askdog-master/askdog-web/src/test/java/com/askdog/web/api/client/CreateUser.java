package com.askdog.web.api.client;

import com.askdog.web.api.client.extractor.ResponseEntityExtractor;
import com.askdog.web.api.client.extractor.ResponseExtractor;
import com.askdog.web.api.vo.RegisterUser;
import com.askdog.web.dsl.Chain;
import com.askdog.web.dsl.Injector.CookieInjector;

public interface CreateUser {

    interface Action extends Chain {
        ResponseEntityExtractor create(RegisterUser user);
        ResponseExtractor confirm(String token);
    }

    interface Api extends Action, CookieInjector<Action> {}

}
