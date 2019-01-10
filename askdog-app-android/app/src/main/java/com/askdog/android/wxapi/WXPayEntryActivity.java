package com.askdog.android.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.askdog.android.R;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.NLog;
import com.askdog.android.utils.NToast;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        api = WXAPIFactory.createWXAPI(this, ConstantUtils.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        NLog.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {//微信支付回调
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {//微信支付成功
                //成功
                NToast.shortToast(this, R.string.wxpay_success);
            } else {
                NToast.shortToast(this, R.string.wxpay_failed);
            }
        }
        finish();
    }
}