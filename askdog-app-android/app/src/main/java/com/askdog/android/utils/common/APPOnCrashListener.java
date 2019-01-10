/*
    ShengDao Android Client, APPOnCrashListener
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.askdog.android.utils.common;

import android.content.Context;

import java.io.File;

/**
 * [系统未捕获异常处理监听类]
 **/
public interface APPOnCrashListener {

    /**
     * 当系统Crash的时候处理方法，一般弹出友好提示框
     *
     * @param context
     */
    void onCrashDialog(Context context);

    /**
     * 处理收集错误信息，一般发送于服务器
     *
     * @param crashReport 收集错误信息
     */
    void onCrashPost(String crashReport, File file);
}
