package com.askdog.web.test;

import com.askdog.service.bo.PureQuestion;
import com.askdog.web.api.vo.PresentationQuestion;
import com.askdog.web.dsl.Chain;
import com.askdog.web.dsl.Injector;

public interface QuestionTest {

    interface Api extends Chain {
        QuestionTest.QuestionInspector<PresentationQuestion> create(PureQuestion question);
    }

    interface Test extends Api, Injector.CookieInjector<Api> {}

    interface QuestionInspector<T> extends Inspector.StatusInspector<Inspector.ComposedInspector<T>>, Inspector.ComposedInspector<T> {}
}
