/*
    ShengDao Android Client, BaseAction
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.askdog.android.network.service;

import android.content.Context;


/**
 * [业务逻辑基础类，实现xml、json直接与JAVA对象互转，初始化网络请求类]
 *
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-29
 **/
public abstract class BaseAction {

    protected Context mContext;

    protected int pageSize = 20;

    /**
     * 缓存有效期5分钟
     **/
    protected final long INVALID_TIME = 5 * 60;
    /**
     * 缓存有效期30分钟
     **/
    protected final long INVALID_TIME_30MIN = 30 * 60;
    /**
     * 缓存有效期1小时
     **/
    protected final long INVALID_TIME_1HOURS = 60 * 60;
    /**
     * 缓存有效期1天
     **/
    protected final long INVALID_TIME_1DAY = 24 * 60 * 60;
    /**
     * 缓存有效期1周
     **/
    protected final long INVALID_TIME_1WEEK = 7 * 24 * 60 * 60;
    /**
     * 缓存有效期1个月
     **/
    protected final long INVALID_TIME_1MONTH = 30 * 24 * 60 * 60;


    /**
     * 构造方法
     *
     * @param context
     */
    public BaseAction(Context context) {
        this.mContext = context;
    }


    /**
     * 获取完整URL方法
     *
     * @param url
     * @return
     */
    protected String getURL(String url) {
        return getURL(url, new String[]{});
    }

    /**
     * 获取完整URL方法
     *
     * @param url
     * @param params
     * @return
     */
    protected String getURL(String url, String... params) {
        StringBuilder urlBilder = new StringBuilder(OkRequestConstants.DOMAIN).append(url);
        if (params != null) {
            for (String param : params) {
                if (!urlBilder.toString().endsWith("/")) {
                    urlBilder.append("&");
                }
                urlBilder.append(param);
            }
        }
        return urlBilder.toString();
    }
}
