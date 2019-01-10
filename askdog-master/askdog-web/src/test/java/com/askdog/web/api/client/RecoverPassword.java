package com.askdog.web.api.client;

import com.askdog.web.api.client.extractor.ResponseEntityExtractor;
import com.askdog.web.dsl.Chain;
import com.askdog.web.dsl.Injector;

public interface RecoverPassword {

    interface Action extends Chain {
        ResponseEntityExtractor recover(String mail);
    }

    interface Api extends Action, Injector.CookieInjector<Action> {}
}
