package com.askdog.service.utils.ProcessPipline;

import com.askdog.service.exception.ServiceException;

public class ProcessPipline<T> {

    private static final NoOpProcessPipline NO_OP_PROCESSPIPLINE = new NoOpProcessPipline<>();

    private T intermediateValues;

    public static <T> ProcessPipline<T> init(T t) {
        return new ProcessPipline<>(t);
    }

    protected ProcessPipline() {
    }

    private ProcessPipline(T t) {
        this.intermediateValues = t;
    }

    public <R> ProcessPipline<R> process(ProcessFunction<T, R> processFunction) throws ServiceException {
        if (this instanceof NoOpProcessPipline) {
            return (NoOpProcessPipline<R>) this;
        }

        R r = processFunction.process(intermediateValues);

        return new ProcessPipline<>(r);
    }

    @SuppressWarnings("unchecked")
    public ProcessPipline<T> filter(ProcessFunction<T, Boolean> processFunction) throws ServiceException {
        if (this instanceof NoOpProcessPipline) {
            return this;
        }

        Boolean isSuccess = processFunction.process(intermediateValues);

        if (!isSuccess) {
            return NO_OP_PROCESSPIPLINE;
        }

        return this;
    }
}
