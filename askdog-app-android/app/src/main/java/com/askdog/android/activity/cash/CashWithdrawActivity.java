package com.askdog.android.activity.cash;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.model.RevenueBean;
import com.askdog.android.network.service.BaseAction;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.JsonMananger;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现
 */
public class CashWithdrawActivity extends BaseActivity {
    @Bind(R.id.navi_title)
    TextView title;
    @Bind(R.id.cash_withdraw_amount)
    EditText withdraw_amount;
    @Bind(R.id.cash_withdraw_balance)
    TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_withdraw);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        int isok = getIntent().getIntExtra("ok",0);
        long balanceCash = getIntent().getLongExtra("balance",0);
        if(isok == 1){
            updateUI(String.valueOf(balanceCash));
        }else{//为了节省一次网络请求，如果上一页面请求成功，由上一页面传入，否则自己请求
            request(OkRequestConstants.CODE_ME_REVENUE);
        }
    }

    private void initView() {
        title.setText("提现");
    }


    @OnClick({R.id.navi_back,R.id.cash_withdraw_submit_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navi_back:
                finish();
                break;
            case R.id.cash_withdraw_submit_btn:
                String amount = withdraw_amount.getText().toString().trim();
                if (TextUtils.isEmpty(amount)) {
                    withdraw_amount.requestFocus();
                } else {
                    request(OkRequestConstants.CODE_USER_WITHDRAW);
                }
                break;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(this);
        if (requestCode == OkRequestConstants.CODE_USER_WITHDRAW) {
            return action.postUserWithdraw("WXPAY", withdraw_amount.getText().toString().trim());
        } else if (requestCode == OkRequestConstants.CODE_ME_REVENUE) {
            return action.getMeRevenue();
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (requestCode == OkRequestConstants.CODE_USER_WITHDRAW) {
                setResult(RESULT_OK);
                finish();
            } else if (requestCode == OkRequestConstants.CODE_ME_REVENUE) {
                final RevenueBean bean = JsonMananger.jsonToBean(result, RevenueBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(String.valueOf(bean.balance));
                    }
                });
            }
        } catch (Exception e) {

        }
    }

    private void updateUI(String s) {
        balance.setText(s);
    }
}
