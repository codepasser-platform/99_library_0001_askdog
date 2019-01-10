package com.askdog.web.test;

import com.askdog.web.dsl.Chain;
import com.askdog.web.dsl.Injector;

public interface PasswordTest {

    interface Api extends Chain {
        PasswordInspector<Void> recover(String mail);
    }

    interface Test extends Api, Injector.CookieInjector<Api> {}

    interface PasswordInspector<T> extends Inspector.StatusInspector<Inspector.ComposedInspector<T>>, Inspector.ComposedInspector<T> {}

}
