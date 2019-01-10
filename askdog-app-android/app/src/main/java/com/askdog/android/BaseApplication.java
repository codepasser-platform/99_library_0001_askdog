/*
    ShengDao Android Client, BaseApplication
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.askdog.android;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;


import com.askdog.android.model.ProfileBean;
import com.askdog.android.network.async.AsyncTaskManager;
import com.askdog.android.utils.CommonUtils;
import com.askdog.android.utils.NLog;
import com.askdog.android.utils.common.APPOnCrashListener;
import com.askdog.android.utils.common.AppCrashHandler;
import com.askdog.android.utils.common.CacheManager;

import java.io.File;

/**
 * [系统Application类，设置全局变量以及初始化组件]
 *
 * @author wyong
 * @version 1.0
 * @date 2015-12-02
 **/
public class BaseApplication extends Application implements APPOnCrashListener {

    private final String tag = BaseApplication.class.getSimpleName();
    static Context _context;

    public static ProfileBean getProfileBean() {
        return profileBean;
    }

    public static void setProfileBean(ProfileBean bean) {
        profileBean = bean;
    }

    private static ProfileBean profileBean;
    private static int notificationNo = 0;

    public static int getNotificationNo() {
        return notificationNo;
    }

    public static void setNotificationNo(int notificationNo) {
        BaseApplication.notificationNo = notificationNo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        _context = getApplicationContext();
        AsyncTaskManager.getInstance().init(this);
//        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
//        ImageLoader.getInstance().init(configuration);
    }
    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }
    /**
     * 初始化
     */
    private void init() {
        //初始化debug模式
        String flag = CommonUtils.getProperty(getApplicationContext(), "debug");
        if (!TextUtils.isEmpty(flag)) {
            Boolean isDebug = Boolean.parseBoolean(flag);
            NLog.setDebug(isDebug);
            NLog.e(tag, "isDebug: " + isDebug);
        }

        //设置默认缓存路径
        CacheManager.setSysCachePath(getCacheDir().getPath());

        //初始化默认异常处理组件
        if (!NLog.isDebug()) {
            AppCrashHandler crashHandler = AppCrashHandler.getInstance(getApplicationContext());
            crashHandler.setOnCrashListener(this);
        }
    }

    @Override
    public void onCrashDialog(Context context) {

    }

    @Override
    public void onCrashPost(String crashReport, File file) {

    }

}
