package com.askdog.web.test;

import com.askdog.web.api.vo.PostedAnswer;
import com.askdog.web.api.vo.PresentationAnswer;
import com.askdog.web.dsl.Chain;
import com.askdog.web.dsl.Injector;

public interface AnswerTest {

    interface Api extends Chain {
        AnswerInspector<PresentationAnswer> create(PostedAnswer answer);
    }

    interface Test extends Api, Injector.CookieInjector<Api> {}

    interface AnswerInspector<T> extends Inspector.StatusInspector<Inspector.ComposedInspector<T>>, Inspector.ComposedInspector<T> {}
}
