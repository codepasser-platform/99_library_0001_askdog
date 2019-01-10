package com.askdog.android.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MailSendSuccessActivity extends BaseActivity {
    private String TAG = "ForGetPasswordActivity";

    @Bind(R.id.navi_back_ll)
    LinearLayout backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail_success);
        ButterKnife.bind(this);
        // 中间按键图片触发
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Log.v("lss", "ResetPasswordActivity  ");
            Intent intent = new Intent(MailSendSuccessActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        }
    };

    @OnClick({R.id.navi_back_ll, R.id.return_login})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.navi_back_ll:
                finish();
                break;
            case R.id.return_login:
                Intent intent = new Intent(MailSendSuccessActivity.this, LoginActivity.class);
                startActivity(intent);
        }
    }
}
