/*
    ShengDao Android Client, BaseFragment
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.askdog.android;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.askdog.android.interf.BackHandlerInterface;
import com.askdog.android.interf.BaseFragmentInterface;
import com.askdog.android.network.async.AsyncTaskManager;
import com.askdog.android.network.async.OnDataListener;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.utils.NToast;
import com.askdog.android.utils.common.ActivityPageManager;

import butterknife.ButterKnife;


/**
 * [Fragment基础类，实现异步框架，Activity堆栈的管理，destroy时候销毁所有资源]
 *
 * @author wyong
 * @version 1.0
 * @date 2015-12-02
 **/
public abstract class BaseFragment extends Fragment implements OnDataListener, BaseFragmentInterface, BackHandlerInterface {

    protected Context mContext;

    private View mContentView = null;
    private AsyncTaskManager mAsyncTaskManager;
    public OkRequestAction action;

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {
        action.cancelRequestByTag();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        //初始化异步框架
        action = new OkRequestAction(mContext);
        mAsyncTaskManager = AsyncTaskManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = onCreateFragmentView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, mContentView);
        initData();
        initView(mContentView);
        return mContentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityPageManager.unbindReferences(mContentView);
        ButterKnife.unbind(this);
        mContentView = null;
        mContext = null;
    }

    @Override
    public void onStop() {
        super.onStop();
//        action.cancelRequestByTag();
    }

    /**
     * 创建view方法，子类必须重写
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 发送请求（需要检查网络）
     *
     * @param requsetCode 请求码
     */
    public void request(int requsetCode) {
        mAsyncTaskManager.request(requsetCode, this);
    }

    /**
     * 发送请求
     *
     * @param requsetCode    请求码
     * @param isCheckNetwork 是否需检查网络，true检查，false不检查
     */
    public void request(int requsetCode, boolean isCheckNetwork) {
        mAsyncTaskManager.request(requsetCode, isCheckNetwork, this);
    }

    /**
     * 取消所有请求
     */
    public void cancelRequest() {
        mAsyncTaskManager.cancelRequest();
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        return null;
    }

    @Override
    public void onSuccess(int requestCode, String result) {

    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        switch (state) {
            //网络不可用给出提示
            case AsyncTaskManager.HTTP_NULL_CODE:
                NToast.shortToast(mContext, R.string.common_network_unavailable);
                break;

            //网络有问题给出提示
            case AsyncTaskManager.HTTP_ERROR_CODE:
                NToast.shortToast(mContext, R.string.common_network_error);
                break;

            //请求有问题给出提示
            case AsyncTaskManager.REQUEST_ERROR_CODE:
                NToast.shortToast(mContext, R.string.common_request_error);
                break;
            default:
                NToast.shortToast(mContext, R.string.common_request_error);
                break;
        }
    }
//    public abstract void notifyIconStatus(int count);
}
