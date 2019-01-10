package com.askdog.common.utils;

import javax.annotation.Nullable;

public class Conditions {

    public static <T extends Throwable> void checkState(boolean expression, @Nullable T throwable) throws T {
        if (!expression) {
            throw throwable;
        }
    }

}
