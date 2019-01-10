package com.askdog.web.test.cleanup;

import javax.annotation.Nonnull;
import java.util.Stack;

public final class CleanupExecutorRegistry {

    private Stack<CleanupExecutor> executors = new Stack<>();

    private void internalRegister(CleanupExecutor executor) {
        executors.push(executor);
    }

    private static ThreadLocal<CleanupExecutorRegistry> executorRegistryThreadLocal = new ThreadLocal<>();

    public static void register(@Nonnull  CleanupExecutor executor) {
        CleanupExecutorRegistry registry = executorRegistryThreadLocal.get();
        if (registry == null) {
            registry = new CleanupExecutorRegistry();
            executorRegistryThreadLocal.set(registry);
        }

        registry.internalRegister(executor);
    }

    static void clean() {
        CleanupExecutorRegistry registry = executorRegistryThreadLocal.get();
        if (registry != null) {
            registry.executors.clear();
        }
    }

    static Stack<CleanupExecutor> getCleanupExecutors() {
        CleanupExecutorRegistry registry = executorRegistryThreadLocal.get();
        if (registry != null) {
            return registry.executors;
        }

        return new Stack<>();
    }

}
