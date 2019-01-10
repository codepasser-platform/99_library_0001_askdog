/*
    ShengDao Android Client, AsyncTaskManager
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.askdog.android.network.async;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.askdog.android.utils.NLog;
import com.squareup.okhttp.Response;

import java.io.IOException;

import de.greenrobot.event.EventBus;


/**
 * [A brief description]
 **/
public class AsyncTaskManager {

    /**
     * 发生请求成功
     **/
    public static final int REQUEST_SUCCESS_CODE = 200;
    /**
     * 发生请求失败
     **/
    public static final int REQUEST_ERROR_CODE = -999;
    /**
     * 网络有问题
     **/
    public static final int HTTP_ERROR_CODE = -200;
    /**
     * 网络不可用
     **/
    public static final int HTTP_NULL_CODE = -400;
    private static AsyncTaskManager instance;
    private final String tag = AsyncTaskManager.class.getSimpleName();
    private Context mContext;

    /**
     * 构造方法
     */
    private AsyncTaskManager() {

        EventBus.getDefault().register(this);
    }

    /**
     * [获取AsyncTaskManager实例，单例模式实现]
     *
     * @return
     */
    public static AsyncTaskManager getInstance() {
        if (instance == null) {
            synchronized (AsyncTaskManager.class) {
                if (instance == null) {
                    instance = new AsyncTaskManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    /**
     * 发送请求（默认检查网络）
     *
     * @param requestCode 请求码
     * @param listener    回调监听
     */
    public void request(int requestCode, OnDataListener listener) {
        request(requestCode, true, listener);
    }

    /**
     * 发送请求
     *
     * @param requestCode    请求码
     * @param isCheckNetwork 是否检查网络，true检查，false不检查
     * @param listener       回调监听
     */
    public void request(final int requestCode, final boolean isCheckNetwork, final OnDataListener listener) {
        if (requestCode > 0) {
            EventBus.getDefault().post(new AsyncRequestEvent(requestCode, isCheckNetwork, listener));
        } else {
            NLog.e(tag, "the error is requestCode < 0");
        }
    }

    /**
     * 异步线程
     *
     * @param bean
     */
    public void onEventAsync(AsyncRequestEvent bean) {
        AsyncResultEvent result = new AsyncResultEvent(bean.getRequestCode(), bean.isCheckNetwork(), bean.getListener());
        try {
            if (!isNetworkConnected(mContext, bean.isCheckNetwork())) {
                result.setState(HTTP_NULL_CODE);
            } else {
                Object object = bean.getListener().doInBackground(bean.getRequestCode());
                final Response response = (Response) object;
//                int code = response.code();
                if (response.code() >= 400 && response.code() <= 599) {
                    result.setState(REQUEST_ERROR_CODE);
                    result.setResult(response.body().string());
                } else if(response.code() >= 200 && response.code() <= 300){
                    String message = response.body().string();
                    result.setResult(message);
                    result.setState(REQUEST_SUCCESS_CODE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(e.toString());
//			if(e instanceof HttpException){
//				result.setState(HTTP_ERROR_CODE);
//			}else{
            result.setState(REQUEST_ERROR_CODE);
//			}
        }
        EventBus.getDefault().post(result);
    }


    /**
     * 在数据返回到UI线程中处理
     *
     * @param bean
     */
    public void onEventMainThread(AsyncResultEvent bean) {
        switch (bean.getState()) {
            case REQUEST_SUCCESS_CODE:
                bean.getListener().onSuccess(bean.getRequestCode(), bean.getResult());
                break;

            case REQUEST_ERROR_CODE:
            case HTTP_ERROR_CODE:
            case HTTP_NULL_CODE:
                bean.getListener().onFailure(bean.getRequestCode(), bean.getState(), bean.getResult());
                break;
            default:
                break;
        }
    }

    /**
     * 取消所有请求
     */
    public void cancelRequest() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @param isCheckNetwork 是否检查网络，true表示检查，false表示不检查
     * @return
     */
    public boolean isNetworkConnected(Context context, boolean isCheckNetwork) {
        if (isCheckNetwork) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnectedOrConnecting();
        } else {
            return true;
        }
    }
}
