/*
    ShengDao Android Client, BaseActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.askdog.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.askdog.android.activity.login.LoginActivity;
import com.askdog.android.network.async.AsyncTaskManager;
import com.askdog.android.network.async.OnDataListener;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.MyProgressDialog;
import com.askdog.android.utils.NToast;
import com.askdog.android.utils.PreferencesUtils;
import com.askdog.android.utils.common.ActivityPageManager;

import org.json.JSONObject;


/**
 * [Activity基础类，实现异步框架，Activity堆栈的管理，destroy时候销毁所有资源]
 *
 * @author wyong
 * @version 1.0
 * @date 2015-12-02
 **/
public class BaseActivity extends AppCompatActivity implements OnDataListener, View.OnClickListener {

    protected Context mContext;
    protected MyProgressDialog mProgressDialog;
    protected int PAGESIZE = 6;
    private AsyncTaskManager mAsyncTaskManager;
    public OkRequestAction action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mProgressDialog = new MyProgressDialog(mContext);
        action = new OkRequestAction(mContext);
        //初始化异步框架
        mAsyncTaskManager = AsyncTaskManager.getInstance();
        mAsyncTaskManager.init(this);
        //Activity管理
        ActivityPageManager.getInstance().addActivity(this);
    }


//    @Override
//    protected void setContentView(int layoutResID) {
//        View view = LayoutInflater.from(this).inflate(layoutResID, null);
//        setContentView(view);
//    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        action.cancelRequestByTag();
        ActivityPageManager.getInstance().removeActivity(this);
        mContext = null;
    }

    /**
     * 发送请求（需要检查网络）
     *
     * @param requestCode 请求码
     */
    public void request(int requestCode) {
        boolean timeout = checkSessionTimeout();
        boolean flg = checkIsNeedLogin(requestCode);
        if (flg) {
            boolean isLogin = PreferencesUtils.getBoolean(this, ConstantUtils.IS_LOGIN, false);
            if (!isLogin) {
                Intent intent = new Intent();
                intent.setClass(mContext, LoginActivity.class);
                startActivity(intent);
                return;
            }
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show(true);
        }
        request(requestCode, true);

    }

    private boolean checkSessionTimeout() {
        //TODO 接口预留
        return false;
    }

    /**
     * 下述请求要求登录
     *
     * @param requestCode
     * @return
     */
    protected boolean checkIsNeedLogin(int requestCode) {
        boolean flg = false;
        if (requestCode == OkRequestConstants.CODE_ORDERS_EXPERIENCE
                || requestCode == OkRequestConstants.CODE_ORDERS_EXPERIENCE_PAY
                || requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION
                || requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION_CANCEL
                || requestCode == OkRequestConstants.CODE_VOTE_UP
                || requestCode == OkRequestConstants.CODE_VOTE_DOWN
                || requestCode == OkRequestConstants.CODE_VOTE_DOWN_UP_CANCEL
                || requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION
                || requestCode == OkRequestConstants.CODE_CHANNELS_SUBSCRIPTION_CANCEL) {
            flg = true;
        }

        return flg;
    }

    /**
     * 发送请求
     *
     * @param requestCode    请求码
     * @param isCheckNetwork 是否需检查网络，true检查，false不检查
     */
    public void request(int requestCode, boolean isCheckNetwork) {
        mAsyncTaskManager.request(requestCode, isCheckNetwork, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        action.cancelRequestByTag();
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
        mProgressDialog.show(false);
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        mProgressDialog.show(false);
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
                String errorMsg = null;
                try{
                    JSONObject jsonObject = new JSONObject((String) result);
                    errorMsg = jsonObject.getString("message");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                NToast.shortToast(mContext, errorMsg);
                break;
            default:
                NToast.shortToast(mContext, R.string.common_request_error);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 重新启动当前页面
     */
    protected void reLoad() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void onClick(View v) {

    }
}
