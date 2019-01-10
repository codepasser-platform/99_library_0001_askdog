package com.askdog.web.api.client;

import com.askdog.web.api.vo.UserSelf;
import com.askdog.web.api.client.extractor.ResponseBodyExtractor;
import com.askdog.web.dsl.Chain;
import com.askdog.web.dsl.Injector.CookieInjector;

public interface Me {

    interface Action extends Chain {
        ResponseBodyExtractor<UserSelf> me();
    }

    interface Api extends Action, CookieInjector<Action> {}
}
