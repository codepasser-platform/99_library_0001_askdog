package com.askdog.web.test;

import com.askdog.web.test.cleanup.CleanupExecutor;

import javax.annotation.Nullable;

public interface DataSet {
    void prepare();
    @Nullable CleanupExecutor cleanupExecutor();
}
