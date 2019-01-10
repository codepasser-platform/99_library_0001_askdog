package com.askdog.web.test.impl.inspector;

import com.askdog.web.test.Inspector;
import com.askdog.web.test.UserTest;

public interface ComposedInspector<T> extends Inspector.ComposedInspector<T>,
        StatusInspector<Inspector.ComposedInspector<T>>,
        ErrorInspector<Inspector.EmptyInspector>,
        MatcherInspector<T> {

    @Override
    default ComposedInspector<T> afterStatusInspector() {
        return this;
    }

    @Override
    default EmptyInspector afterErrorInspector() {
        return EmptyInspector.INSTANCE;
    }

}
