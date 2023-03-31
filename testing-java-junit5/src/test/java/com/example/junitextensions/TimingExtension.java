package com.example.junitextensions;

import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class TimingExtension implements org.junit.jupiter.api.extension.BeforeTestExecutionCallback, org.junit.jupiter.api.extension.AfterTestExecutionCallback {
    private static final java.util.logging.Logger log = Logger.getLogger(TimingExtension.class.getName());
    private static final String START_TIME = "start_time";

    public TimingExtension() {
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        getStore(context).put(START_TIME, System.currentTimeMillis());
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        return context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        long start_time = getStore(context).remove(START_TIME, long.class);
        long duration = System.currentTimeMillis() - start_time;
        log.info(() -> String.format("Method [%s] took %s ms.", testMethod.getName(), duration));

    }
}
