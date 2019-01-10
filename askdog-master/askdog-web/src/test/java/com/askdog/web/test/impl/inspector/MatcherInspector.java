package com.askdog.web.test.impl.inspector;

import com.askdog.web.test.Inspector;
import org.hamcrest.Matcher;

import static org.junit.Assert.assertThat;

public interface MatcherInspector<T> extends Inspector.MatcherInspector<T> {

    @Override
    default MatcherInspector<T> expect(Matcher<T> matcher) {
        autowire(matcher);
        assertThat(getBody(), matcher);
        return this;
    }

    T getBody();

    void autowire(Object bean);
}
