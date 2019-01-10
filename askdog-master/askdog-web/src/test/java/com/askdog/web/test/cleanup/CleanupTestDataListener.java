package com.askdog.web.test.cleanup;

import com.askdog.web.test.DataSet;
import com.askdog.web.test.UsingDataSet;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.lang.reflect.Method;
import java.util.Stack;

import static java.util.Arrays.stream;

public class CleanupTestDataListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        CleanupExecutorRegistry.clean();
        bindDataSet(testContext);
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        Stack<CleanupExecutor> cleanupExecutors = CleanupExecutorRegistry.getCleanupExecutors();
        while (!cleanupExecutors.empty()) {
            CleanupExecutor executor = cleanupExecutors.pop();
            executor.execute();
        }
    }

    private void bindDataSet(TestContext testContext) {
        Method method = testContext.getTestMethod();
        if (method.isAnnotationPresent(UsingDataSet.class)) {
            ApplicationContext applicationContext = testContext.getApplicationContext();
            AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
            UsingDataSet usingDataSet = method.getAnnotation(UsingDataSet.class);
            stream(usingDataSet.value()).forEachOrdered(dataSetClass -> {
                try {
                    DataSet instance = dataSetClass.newInstance();
                    autowireCapableBeanFactory.autowireBean(instance);
                    instance.prepare();
                    CleanupExecutor executor = instance.cleanupExecutor();
                    if (executor != null) {
                        CleanupExecutorRegistry.register(executor);
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
