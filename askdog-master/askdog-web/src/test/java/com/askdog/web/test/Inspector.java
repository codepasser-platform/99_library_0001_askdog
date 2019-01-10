package com.askdog.web.test;

import com.askdog.web.dsl.Chain;
import org.hamcrest.Matcher;
import org.springframework.http.HttpStatus;

public interface Inspector extends Chain {

    interface EmptyInspector extends Inspector {
        EmptyInspector INSTANCE = new EmptyInspector() {};
    }

    interface StatusInspector<N extends Inspector> extends Inspector {
        N expectStatus(HttpStatus status);
    }

    interface ErrorInspector<N extends Inspector> extends Inspector {
        N expectErrorCode(String code);
    }

    interface MatcherInspector<T> extends Inspector {
        MatcherInspector<T> expect(Matcher<T> matcher);
    }

    interface ComposedInspector<T> extends ErrorInspector<EmptyInspector>, MatcherInspector<T> {}

}
