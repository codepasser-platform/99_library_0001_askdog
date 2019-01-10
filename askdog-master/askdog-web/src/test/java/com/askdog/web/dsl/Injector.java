package com.askdog.web.dsl;

import java.net.CookieStore;

public interface Injector extends Chain {

    interface EmptyInjector extends Injector {}

    interface CookieInjector<N extends Chain> extends Injector {
        N withCookies(CookieStore store);
    }
}
