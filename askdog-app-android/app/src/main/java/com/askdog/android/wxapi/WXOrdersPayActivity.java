package com.askdog.android.wxapi;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.model.OrdersPayBean;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.JsonMananger;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WXOrdersPayActivity extends BaseActivity {
    @Bind(R.id.navi_title)
    TextView title;
    @Bind(R.id.wx_pay_amount)
    TextView pay_amount;
    @Bind(R.id.wx_order_subject)
    TextView order_subject;
    private IWXAPI api;
    private String subject;
    private String experienceId;
    private String amount;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxorders_pay);
        ButterKnife.bind(this);
        api = WXAPIFactory.createWXAPI(this, "wx861de98443c15c52");
        initData();
        initView();
    }

    private void initData() {
        subject = getIntent().getStringExtra("subject");
        experienceId = getIntent().getStringExtra("experienceId");
        amount = getIntent().getStringExtra("amount");
        content = getIntent().getStringExtra("content");
    }

    private void initView() {
        title.setText("支付订单");
        pay_amount.setText(amount);
        order_subject.setText(subject);
    }

    @OnClick({R.id.navi_back, R.id.wx_order_pay_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navi_back:
                finish();
                break;
            case R.id.wx_order_pay_btn:
                request(OkRequestConstants.CODE_ORDERS_EXPERIENCE_PAY);
                break;
        }
    }

    private void processWXRequest(OrdersPayBean ordersPayBean) {

        PayReq req = new PayReq();
        req.appId = ordersPayBean.pay_request.appid;
        req.partnerId = ordersPayBean.pay_request.partnerid;
        req.prepayId = ordersPayBean.pay_request.prepayid;
        req.nonceStr = ordersPayBean.pay_request.noncestr;
        req.timeStamp = ordersPayBean.pay_request.timestamp;
        req.packageValue = "Sign=WXPay";
        req.sign = ordersPayBean.pay_request.sign;
        req.extData = "app data"; // optional
//        Toast.makeText(WXOrdersPayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

    /**
     *
     */

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (requestCode == OkRequestConstants.CODE_ORDERS_EXPERIENCE_PAY) {
            return action.postOrdersExperiencePay(experienceId, subject, content);
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        try {
            if (requestCode == OkRequestConstants.CODE_ORDERS_EXPERIENCE_PAY) {
                OrdersPayBean mOrdersPayBean = JsonMananger.jsonToBean(result, OrdersPayBean.class);
                processWXRequest(mOrdersPayBean);
            }
        } catch (Exception e) {

        }
        super.onSuccess(requestCode, result);
    }


}
