package com.askdog.android.activity.cash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.model.RevenueBean;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NToast;
import com.askdog.android.view.dialog.CommonToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCashActivity extends BaseActivity {
    @Bind(R.id.navi_title)
    TextView title;
    @Bind(R.id.income_withdraw_account)
    TextView account;

    private int requestok = 0;
    private long balance = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cash);
        ButterKnife.bind(this);
        initView();
        request(OkRequestConstants.CODE_ME_REVENUE);
        request(OkRequestConstants.CODE_ME_BIND_ACCOUNTS);
    }

    private void initView() {
        title.setText("我的收入");
    }

    private void updateUI(String balance) {
        account.setText(balance);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.navi_back, R.id.income_detail_item_ll, R.id.withdraw_record_item_ll, R.id.income_withdraw_btn})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.navi_back:
                finish();
                break;
            case R.id.income_withdraw_btn:
                Intent withdrawIntent = new Intent();
                withdrawIntent.putExtra("ok",requestok);
                withdrawIntent.putExtra("balance",balance);
                withdrawIntent.setClass(MyCashActivity.this, CashWithdrawActivity.class);
                startActivityForResult(withdrawIntent, 100);
                break;
            case R.id.income_detail_item_ll:
                Intent i = new Intent();
                i.setClass(MyCashActivity.this, CashIncomeDetailActivity.class);
                startActivity(i);

                break;
            case R.id.withdraw_record_item_ll:
                Intent recordIntent = new Intent();
                recordIntent.setClass(MyCashActivity.this, CashWithdrawRecordActivity.class);
                startActivity(recordIntent);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && requestCode == RESULT_OK) {
            request(OkRequestConstants.CODE_ME_REVENUE);
            CommonToast toast = new CommonToast(MyCashActivity.this);
            toast.setMessage("提现成功");
            toast.show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(this);
        if (requestCode == OkRequestConstants.CODE_ME_REVENUE) {
            return action.getMeRevenue();
        }else if (requestCode == OkRequestConstants.CODE_ME_BIND_ACCOUNTS) {
            return action.getMeBindAccount();
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (requestCode == OkRequestConstants.CODE_ME_REVENUE) {
                final RevenueBean bean = JsonMananger.jsonToBean(result, RevenueBean.class);
                requestok = 1;
                balance = bean.balance;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(String.valueOf(bean.balance));
                    }
                });
            }else if(requestCode == OkRequestConstants.CODE_ME_BIND_ACCOUNTS){
                NToast.shortToast(this,result);
            }
        } catch (Exception e) {

        }
    }

}
