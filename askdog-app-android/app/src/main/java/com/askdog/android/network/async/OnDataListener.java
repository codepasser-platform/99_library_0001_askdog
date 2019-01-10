/*
    ShengDao Android Client, OnDataListener
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.askdog.android.network.async;


/**
 * [异步数据处理回调接口类]
 **/
public interface OnDataListener {

    /**
     * 异步耗时方法
     *
     * @param requestCode 请求码
     * @return Object
     * @throws Exception
     */
    Object doInBackground(int requestCode) throws Exception;

    /**
     * 成功方法（可直接更新UI）
     *
     * @param requestCode 请求码
     * @param result      返回结果
     */
    void onSuccess(int requestCode, String result);

    /**
     * 失败方法（可直接更新UI）
     *
     * @param requestCode 请求码
     * @param state       返回状态
     * @param result      返回结果
     */
    void onFailure(int requestCode, int state, Object result);
}
