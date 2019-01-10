package com.askdog.web.test.mockito;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ResultCaptor<T> implements Answer<T> {

    private Object result;

    @Override
    public T answer(InvocationOnMock invocation) throws Throwable {
        result = invocation.callRealMethod();
        //noinspection unchecked
        return (T) result;
    }

    public Object getResult() {
        return result;
    }
}
