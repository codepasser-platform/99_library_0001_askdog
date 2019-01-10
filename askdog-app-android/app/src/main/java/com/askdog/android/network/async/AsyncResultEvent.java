/*
    ShengDao Android Client, DownLoad
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.askdog.android.network.async;

/**
 * [A brief description]
 **/
public class AsyncResultEvent {

    /**
     * 请求id
     */
    private int requestCode;
    /**
     * 是否检查网络，true表示检查，false表示不检查
     */
    private boolean isCheckNetwork;
    /**
     * 下载状态
     */
    private int state;
    /**
     * 返回结果
     */
    private String  result;
    /**
     * 处理监听
     */
    private OnDataListener listener;

    public AsyncResultEvent() {
        super();
    }

    /**
     * @param requestCode
     * @param isCheckNetwork
     * @param listener
     */
    public AsyncResultEvent(int requestCode, boolean isCheckNetwork, OnDataListener listener) {
        this.requestCode = requestCode;
        this.isCheckNetwork = isCheckNetwork;
        this.listener = listener;
    }

    public int getRequestCode() {
        return requestCode;
    }

    /**
     * @param requestCode
     */
    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public boolean isCheckNetwork() {
        return isCheckNetwork;
    }

    public void setCheckNetwork(boolean isCheckNetwork) {
        this.isCheckNetwork = isCheckNetwork;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public OnDataListener getListener() {
        return listener;
    }

    public void setListener(OnDataListener listener) {
        this.listener = listener;
    }

}
